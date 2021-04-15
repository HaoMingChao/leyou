package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/2/26 09:13
 */

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void insertCategoryBrand(Brand brand, List<Long> cids) {
        for (Long cid : cids) {
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }
    @Override
    @Transactional
    public IPage<Brand> findByPage(Integer current, Integer size, String sortBy, Boolean desc, String key) {
        IPage<Brand> page = new Page<>(current,size);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(key)){
            queryWrapper.like("name",key).or().like("letter",key.toUpperCase());
        }
        if (StringUtils.isNoneBlank(sortBy)){
            if (desc == true){
                queryWrapper.orderByDesc("id");
            }else {
                queryWrapper.orderByAsc("id");
            }
        }
        IPage<Brand> byPage = brandMapper.selectPage(page, queryWrapper);
        return byPage;
    }

    public Brand findById(Long id){
        Brand brand = brandMapper.findById(id);
        if (brand == null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }

    @Override
    public List<Brand> findBrandByCid(Long cid) {
        return brandMapper.findBrandByCid(cid);
    }
}
