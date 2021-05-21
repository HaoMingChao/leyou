package com.leyou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;

import java.util.List;


/**
 * @Classname GoodsService
 * @Description TODO
 * @Date 2021/3/4 15:58
 * @Created by MingChao Hao
 */

public interface GoodsService{
    IPage<SpuVo> findSpuByPage(int current, int size, Boolean saleable, String key);

    void saveGoods(SpuVo spu);

    SpuDetail findDetailById(Long supId);

    List<SkuVo> findSkuById(Long spuId);

    void uploadGoods(SpuVo spu);

    SpuVo findSpuById(Long id);
}
