package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @package: com.leyou.mapper
 * @project-name: leyou
 * @description: 商品mapper
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/1/28 21:01
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category>{
}
