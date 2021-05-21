package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.Sku;
import com.leyou.vo.SkuVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Classname SkuMapper
 * @Description TODO
 * @Date 2021/3/11 9:04
 * @Created by MingChao Hao
 */

@Mapper
public interface SkuMapper extends BaseMapper<SkuVo> {
    @Select("SELECT * FROM tb_sku WHERE spu_id = #{skuId}")
    List<SkuVo> findSkuById(Long spuId);

    @Delete("DELETE FROM tb_sku WHERE spu_id = #{skuId};")
    void deleteBySpuId(Long spuId);
}
