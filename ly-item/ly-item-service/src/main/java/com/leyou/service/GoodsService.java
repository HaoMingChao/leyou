package com.leyou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.pojo.Spu;
import com.leyou.vo.SpuVo;


/**
 * @Classname GoodsService
 * @Description TODO
 * @Date 2021/3/4 15:58
 * @Created by MingChao Hao
 */

public interface GoodsService{
    IPage<SpuVo> findSpuByPage(int current, int size, Boolean saleable, String key);

    void saveGoods(SpuVo spu);
}
