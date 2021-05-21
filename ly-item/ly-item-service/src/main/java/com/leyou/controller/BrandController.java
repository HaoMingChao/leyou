package com.leyou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.common.vo.PageInfo;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import com.mysql.cj.log.Log;
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

    /**
     *根据品牌id集合查询
     * @param ids
     * @return
     */
    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> findBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.findBrandByIds(ids));
    }

    /**
     * 根据id查询一个品牌
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brand> findBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.findById(id));
    }

    /**
     * 分页查询品牌
     * @param current
     * @param size
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<IPage<Brand>> findByPage(@RequestParam(value = "current",defaultValue = "1")Integer current, @RequestParam(value = "size",defaultValue = "5")Integer size, @RequestParam(value = "sortBy",required = false)String sortBy, @RequestParam(value = "desc",defaultValue = "false")Boolean desc, @RequestParam(value = "key",required = false)String key){
        return ResponseEntity.ok(brandService.findByPage(current,size,sortBy,desc,key));
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping("/addBrand")
    public ResponseEntity<Boolean> addBrand(Brand brand,@RequestParam("cids")List<Long> cids){
        boolean b = brandService.saveOrUpdate(brand);
        brandService.insertCategoryBrand(brand,cids);
        return ResponseEntity.ok(b);
    }

    /**
     * 根据cid查询品牌
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> findBrandByCid(@PathVariable(value = "cid")Long cid){
        return ResponseEntity.ok(brandService.findBrandByCid(cid));
    }

    /**
     * 根据id删除一个品牌
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<Boolean> deleteBrandById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(brandService.removeById(id));
    }
}
