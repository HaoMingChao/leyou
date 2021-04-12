package com.leyou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.pojo.Category;

import java.util.List;


public interface CategoryService extends IService<Category> {
    List<Category> findCategoryListByPid(Long pid);
    List<Category> findByIds(List<Long> ids);
}
