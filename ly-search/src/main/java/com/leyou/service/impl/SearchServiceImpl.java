package com.leyou.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SearchService;
import com.leyou.vo.SearchResult;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname SearchServiceImpl
 * @Description TODO
 * @Date 2021/4/29 11:32
 * @Created by MingChao Hao
 */

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void DeleteIndex(Long spuId) {
        goodsRepository.deleteById(spuId);
    }

    @Override
    public void createOrUpdateIndex(Long spuId) {
        //查询spu
        SpuVo spu = goodsClient.findSpuById(spuId);
        //构建goods
        Goods buildGoods = buildGoods(spu);
        goodsRepository.save(buildGoods);
    }

    /**
     * 搜索功能
     * @param searchRequest
     * @return
     */
    @Override
    public PageResult<Goods> search(SearchRequest searchRequest) {
        Integer page = searchRequest.getPage() - 1;
        Integer size = searchRequest.getSize();
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page,size));
        //过滤
//        QueryBuilder basicQuery = QueryBuilders.matchQuery("all", searchRequest.getKey());
        QueryBuilder basicQuery = buildBasicQuery(searchRequest);
        queryBuilder.withQuery(basicQuery);

        //聚合分类和品牌
        //聚合分类
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        //聚合品牌
        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        //查询
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(queryBuilder.build(), Goods.class);
        SearchPage<Goods> search = SearchHitSupport.searchPageFor(searchHits, queryBuilder.build().getPageable());
        List<Goods> goodsList = search.getContent().stream().map(s -> s.getContent()).collect(Collectors.toList());
        //解析结果
        long total = search.getTotalElements();
        int totalPages = search.getTotalPages();

        //解析聚合结果
        Aggregations aggregations = searchHits.getAggregations();
        List<Category> categories = parseCategoryAgg(aggregations.get(categoryAggName));
        List<Brand> brands = parseBrandAgg(aggregations.get(brandAggName));

        //规格参数集聚和
        List<Map<String,Object>> specs = null;
        if (categories != null && categories.size() == 1){
//            categories不为空并且规格参数为1
            specs = buildSpecificationAgg(categories.get(0).getId(),basicQuery);
        }

        return new SearchResult(total,totalPages,goodsList,categories,brands,specs);
    }

    private QueryBuilder buildBasicQuery(SearchRequest searchRequest) {
        //创建布尔查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //条件查询
        queryBuilder.must(QueryBuilders.matchQuery("all",searchRequest.getKey()));
        //过滤条件
        Map<String, Object> filterMap = searchRequest.getFilter();
        for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
            String key = entry.getKey();
            if (!"cid3".equals(key) && !"barndId".equals(key)){
                //es存进去的是specs所以我们要对key进行转换
                key = "specs."+key+".keyword";
            }
            Object value = entry.getValue();
            queryBuilder.filter(QueryBuilders.termQuery(key,value));
        }
        return queryBuilder;
    }

    private List<Map<String, Object>> buildSpecificationAgg(Long cid, QueryBuilder basicQuery) {
        List<Map<String,Object>> specs = new ArrayList<>();
        //查询需要聚合的规格参数
        List<SpecParam> specParams = specificationClient.findParamByGid(null, cid, true);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //带上查询条件
        queryBuilder.withQuery(basicQuery);
        //完成聚合
        for (SpecParam specParam : specParams) {
            //规格参数名称
            String name = specParam.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs."+name+".keyword"));
        }
        //获取结果
        SearchHits<Goods> search = elasticsearchRestTemplate.search(queryBuilder.build(), Goods.class);
        //解析聚合结果
        Aggregations aggregations = search.getAggregations();
        for (SpecParam specParam : specParams) {
            //规格参数名称
            String name = specParam.getName();
            Terms terms = aggregations.get(name);
            //准备map key name options terms.options
            Map<String,Object> map = new HashMap<>();
            map.put("k",name);
            map.put("options",terms.getBuckets().stream().map(b -> b.getKey()).collect(Collectors.toList()));
            specs.add(map);
        }
        return specs;
    }

    private List<Brand> parseBrandAgg(Terms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brands = brandClient.findBrandByIds(ids);
            return brands;
        } catch (Exception e) {
            log.error("[搜索服务]查询品牌异常：",e);
            return null;
        }
    }

    private List<Category> parseCategoryAgg(Terms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Category> categories = categoryClient.findCategoryByPid(ids);
            return categories;
        } catch (Exception e) {
            log.error("[搜索服务]查询分类异常：",e);
            return null;
        }
    }

    /**
     * es导入数据
     * @param spu
     * @return
     */
    @Override
    public Goods buildGoods(SpuVo spu) {

        Long spuId = spu.getId();

        //查询分类
        List<Category> categories = categoryClient.findCategoryByPid(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());

        //品牌查询
        Brand brand = brandClient.findBrandById(spu.getBrandId());
        if (brand == null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //搜索字段
        String all = spu.getTitle()+ StringUtils.join(names," ")+brand.getName();

        //查询sku
        List<SkuVo> skuList = goodsClient.findSkuById(spuId);
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        //对sku进行处理
        List<Map<String,Object>> skus = new ArrayList<>();
        //价格集合
        Set<Long> priceSet = new TreeSet<>();
        for (SkuVo sku : skuList) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("images",StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);

            //处理价格
            priceSet.add(sku.getPrice());
        }

        //查询规格参数
        List<SpecParam> params = specificationClient.findParamByGid(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        SpuDetail spuDetail = goodsClient.findDetailById(spuId);
        Map<String, Object> genericSpec = JsonUtils.jsonToMap(spuDetail.getGenericSpec());
        //获取公共
        Map<String, List<Object>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<String, List<Object>>>() {});
        //获取特有
        //规格参数
        HashMap<String, Object> specs = new HashMap<>();
        for (SpecParam param : params) {
            //规格名称
            String key = param.getName();
            Object value = "";
            if (param.getGeneric()){
                value = genericSpec.get(param.getId().toString());
                //判断是否是数值类型
                if (param.getNumeric()){
                    //处理为段
                    value =  chooseSegment(value.toString(),param);
                }
            }else {
                //因为得到的是个集合，我们要取出每个值所以便利一下这个集合
                List<Object> objects = specialSpec.get(param.getId().toString());
                for (Object object : objects) {
                    //往value填充值
                    value = object;
                }
            }
            //存入map
            specs.put(key,value);
        }



        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spuId);
        goods.setAll(all);//搜索字段 标题 分类 品牌 规格参数
        goods.setPrice(priceSet);//所有sku价格集合
        goods.setSkus(JsonUtils.objectTOJSONString(skus));//所有sku的集合josn格式
        goods.setSpecs(specs);//所有可搜索的规格参数
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }

    private String chooseSegment(String value,SpecParam specParam){
        double val = NumberUtils.toDouble(value);
        String result = "其他";
        //保存数值段
        for (String segment : specParam.getSegments().split(",")) {
            String[] split = segment.split(",");
            //获取数值范围
            double begin = NumberUtils.toDouble(split[0]);
            double end = Double.MAX_VALUE;
            if (split.length == 2){
                end = NumberUtils.toDouble(split[1]);
            }
            //判断是否在范围内
            if (val >= begin && val < end){
                if (split.length == 1){
                    result = split[0] + specParam.getUnit() + "以上";
                }else if (begin == 0){
                    result = split[1] + specParam.getUnit() + "以下";
                }else {
                    result = segment + specParam.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
