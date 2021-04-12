package com.leyou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.pojo.Brand;

import java.util.List;

/**
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/2/26 09:13
 */
public interface BrandService extends IService<Brand> {

    IPage<Brand> findByPage(Integer current, Integer size, String sortBy, Boolean desc, String key);

    Brand findById(Long id);

    List<Brand> findBrandByCid(Long cid);

    void insertCategoryBrand(Brand brand, List<Long> cids);
}
