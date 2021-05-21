package com.leyou.controller;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/1/30 15:13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> findCategoryByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok(categoryService.findCategoryListByPid(pid));
    }

    @GetMapping("/bid/{id}")
    public ResponseEntity<Brand> uploadFindByBid(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(brandService.getById(id));
    }

    /**
     * 根据id查询商品分类
     * @param ids
     * @return
     */
    @GetMapping("/list/ids")
    public ResponseEntity<List<Category>> findCategoryByPid(@RequestParam("ids")List<Long> ids){
        return ResponseEntity.ok(categoryService.findByIds(ids));
    }
}
