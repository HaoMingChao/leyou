package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.vo.SpecGroupVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecGroupVoMapper extends BaseMapper<SpecGroupVo> {
    @Insert("INSERT INTO tb_spec_group (cid,name) VALUES (#{cid},#{name})")
    int addGroup(@Param(value = "cid") Long cid,@Param(value = "name") String name);

    @Select("SELECT * FROM tb_spec_group WHERE cid = #{cid}")
    List<SpecGroupVo> selectByCid(Long cid);
}
