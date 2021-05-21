package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @package: com.leyou.mapper
 * @project-name: leyou
 * @description: 商品mapper
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/1/28 21:01
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category>{
    @Select(" <script>" +
            "SELECT id,name,parent_id,is_parent,sort FROM tb_category WHERE parent_id IN"+
            " <foreach collection='ids' item='parentId' separator=',' open='(' close=')'>#{parentId}</foreach> "+
            "</script>")
    List<Category> findByIds(@Param("ids") List<Long> ids);
}
