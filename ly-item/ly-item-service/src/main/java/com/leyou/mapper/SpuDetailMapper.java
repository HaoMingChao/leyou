package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.SpuDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpuDetailMapper extends BaseMapper<SpuDetail> {
    @Insert("INSERT INTO tb_spu_detail  ( spu_id,description, special_spec, generic_spec, packing_list, after_service )  VALUES  ( #{spuId}, #{description}, #{specialSpec}, #{genericSpec}, #{packingList}, #{afterService} )")
    void addSpuDetail(SpuDetail spuDetail);
}
