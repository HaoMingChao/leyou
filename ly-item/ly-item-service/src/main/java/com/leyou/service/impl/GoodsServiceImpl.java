package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.mapper.SpuVoMapper;
import com.leyou.mapper.StockMapper;
import com.leyou.pojo.*;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import com.leyou.service.GoodsService;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname GoodsServiceImpl
 * @Description TODO
 * @Date 2021/3/4 15:58
 * @Created by MingChao Hao
 */

@Slf4j
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public SpuVo findSpuById(Long id) {
        //查询spu
        SpuVo spu = spuVoMapper.selectById(id);
        if (spu == null){
            throw  new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }

        //查询sku
        spu.setSkus(findSkuById(id));

        //查询detail
        spu.setSpuDetail(findDetailById(id));
        return spu;
    }

    /**
     * 修改商品列表
     * @param spu
     */
    @Transactional
    @Override
    public void uploadGoods(SpuVo spu) {
        if(spu.getId() == null){
            throw new LyException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }

        SkuVo sku = new SkuVo();
        sku.setSpuId(spu.getId());
        System.out.println("sku = " + sku);
        List<SkuVo> skuVoList = skuMapper.findSkuById(sku.getSpuId());
        System.out.println("skuVoList = " + skuVoList);
        if (!CollectionUtils.isEmpty(skuVoList)){
            skuMapper.deleteBySpuId(sku.getSpuId());
            List<Long> ids = skuVoList.stream().map(SkuVo::getId).collect(Collectors.toList());
            stockMapper.deleteBatchIds(ids);
        }

        //修改spu
        spu.setValid(null);
        spu.setCreateTime(new Date(System.currentTimeMillis()));
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        int updateSpu = spuVoMapper.updateById(spu);
        if (updateSpu != 1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        int updateDetail = spuDetailMapper.updateById(spu.getSpuDetail());
        if (updateDetail != 1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        saveSkuAndStock(spu);

        //发送mq消息
        try {
            amqpTemplate.convertAndSend("item.insert",spu.getId());
            System.out.println("消息发送成功");
        } catch (AmqpException e) {
            log.error("【mq消息】:修改消息发送异常！",e);
        }
    }

    /**
     * 根据supId查询sku
     * @param spuId
     * @return
     */
    @Override
    public List<SkuVo> findSkuById(Long spuId) {
        //查询sku
        List<SkuVo> listSku = skuMapper.findSkuById(spuId);
        if (CollectionUtils.isEmpty(listSku)){
            throw new LyException(ExceptionEnum.CATEGORY_SKU_NOT_FOND);
        }
//        查询库存
        List<Long> list = listSku.stream().map(SkuVo::getId).collect(Collectors.toList());
        List<Stock> stocksList = stockMapper.selectIds(list);
        if (CollectionUtils.isEmpty(stocksList)){
            throw new LyException(ExceptionEnum.CATEGORY_SKU_NOT_FOND);
        }
        Map<Long, Integer> stockMap = stocksList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        listSku.forEach(sku -> sku.setStock(stockMap.get(sku.getId())));
        return listSku;
    }

    /**
     * 根据id查询详情
     * @param supId
     * @return
     */
    @Override
    public SpuDetail findDetailById(Long supId) {
        SpuDetail spuDetail = spuDetailMapper.selectById(supId);
        if (spuDetail == null){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOND);
        }
        return spuDetail;
    }

    /**
     * 添加商品
     * @param spu
     */
    @Transactional
    @Override
    public void saveGoods(SpuVo spu) {
        //        添加spu
        spu.setId(null);
        spu.setCreateTime(new Date(System.currentTimeMillis()));
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int insertSpuCount = spuVoMapper.insert(spu);
        if (insertSpuCount != 1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

//        新增detail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.addSpuDetail(spuDetail);

        //新增sku和库存
        saveSkuAndStock(spu);

        //发送mq消息
        try {
            amqpTemplate.convertAndSend("item.insert",spu.getId());
        } catch (AmqpException e) {
            log.error("【mq消息】:添加消息发送异常！",e);
        }
    }

    private void saveSkuAndStock(SpuVo spu) {
        //        新增sku
        List<Stock> stockList = new ArrayList<>();
        List<SkuVo> skus = spu.getSkus();
        for (SkuVo sku : skus) {
            sku.setCreateTime(new Date(System.currentTimeMillis()));
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            int skuCount = skuMapper.insert(sku);
            if (skuCount != 1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            //        新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //批量新增
        Integer count = stockMapper.insertBatch(stockList);
        if (count != stockList.size()){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
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
