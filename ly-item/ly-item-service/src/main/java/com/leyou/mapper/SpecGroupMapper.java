package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.SpecGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SpecGroupMapper extends BaseMapper<SpecGroup> {
    @Insert("INSERT INTO tb_spec_group (cid,name) VALUES (#{cid},#{name})")
    int addGroup(@Param(value = "cid") Long cid,@Param(value = "name") String name);
}
