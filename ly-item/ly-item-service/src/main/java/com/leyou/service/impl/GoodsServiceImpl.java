package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.mapper.SpuVoMapper;
import com.leyou.mapper.StockMapper;
import com.leyou.pojo.Category;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import com.leyou.service.GoodsService;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname GoodsServiceImpl
 * @Description TODO
 * @Date 2021/3/4 15:58
 * @Created by MingChao Hao
 */

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private SpuVoMapper spuVoMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    @Transactional
    @Override
    public void saveGoods(SpuVo spu) {
//        添加spu
        System.out.println(spu);
        spu.setId(null);
        spu.setCreateTime(new Date(System.currentTimeMillis()));
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int count = spuVoMapper.insert(spu);
        if (count != 1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

//        新增detail
        SpuDetail spuDetail = spu.getSpuDetail();
        System.out.println(spu.getId());
        spuDetail.setSpuId(spu.getId());
        System.out.println(spuDetail);
        spuDetailMapper.addSpuDetail(spuDetail);
//        spuDetailMapper.insert(spuDetail);

//        定义一个库存集合
//        List<Stock> stockList = new ArrayList<>();

        List<SkuVo> skus = spu.getSkus();
        for (SkuVo sku : skus) {
            sku.setCreateTime(new Date(System.currentTimeMillis()));
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if (count != 1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

//            新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
//            stockList.add(stock);
            stockMapper.addStock(stock);
        }

    }

    @Override
    public IPage<SpuVo> findSpuByPage(int current, int size, Boolean saleable, String key) {
        IPage<SpuVo> spuPage = new Page<>(current,size);
        QueryWrapper<SpuVo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(key)){
            queryWrapper.like("title",key);
        }
        if (saleable != null){
            if (saleable == true){
                queryWrapper.eq("saleable",1);
            }else {
                queryWrapper.eq("saleable",0);
            }

        }
        queryWrapper.orderByDesc("last_update_time");

        IPage<SpuVo> voIPage = spuVoMapper.selectPage(spuPage, queryWrapper);
        if (CollectionUtils.isEmpty(voIPage.getRecords())){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }
        loadCategoryAndBrandName(voIPage);
        return voIPage;
    }

    private void loadCategoryAndBrandName(IPage<SpuVo> voIPage) {
        List<SpuVo> records = voIPage.getRecords();
        for (SpuVo record : records) {
            List<String> nameList = categoryService.findByIds(Arrays.asList(record.getCid1(), record.getCid2(), record.getCid3())).stream().map(Category::getName).collect(Collectors.toList());
            record.setCname(StringUtils.join(nameList,"/"));
            record.setBname(brandService.getById(record.getBrandId()).getName());
        }
    }
}
