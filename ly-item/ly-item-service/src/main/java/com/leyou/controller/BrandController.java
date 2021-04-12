package com.leyou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.vo.PageInfo;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/2/26 09:11
 */

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<IPage<Brand>> findByPage(@RequestParam(value = "current",defaultValue = "1")Integer current, @RequestParam(value = "size",defaultValue = "5")Integer size, @RequestParam(value = "sortBy",required = false)String sortBy, @RequestParam(value = "desc",defaultValue = "false")Boolean desc, @RequestParam(value = "key",required = false)String key){
        return ResponseEntity.ok(brandService.findByPage(current,size,sortBy,desc,key));
    }

    @PostMapping("/addBrand")
    public ResponseEntity<Boolean> addBrand(Brand brand,@RequestParam("cids")List<Long> cids){
        boolean b = brandService.saveOrUpdate(brand);
        brandService.insertCategoryBrand(brand,cids);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> findBrandByCid(@PathVariable(value = "cid")Long cid){
        return ResponseEntity.ok(brandService.findBrandByCid(cid));
    }
    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<Boolean> deleteBrandById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(brandService.removeById(id));
    }
}
