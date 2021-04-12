package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.Sku;
import com.leyou.vo.SkuVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname SkuMapper
 * @Description TODO
 * @Date 2021/3/11 9:04
 * @Created by MingChao Hao
 */

@Mapper
public interface SkuMapper extends BaseMapper<SkuVo> {
}
