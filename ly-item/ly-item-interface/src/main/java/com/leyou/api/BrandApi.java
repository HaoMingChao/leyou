package com.leyou.api;

import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BrandApi {
    /**
     * 根据id查询一个品牌
     *
     * @param id
     * @return
     */
    @GetMapping("/brand/{id}")
    Brand findBrandById(@PathVariable("id") Long id);

    /**
     *根据品牌id集合查询
     * @param ids
     * @return
     */
    @GetMapping("/brand/brands")
    List<Brand> findBrandByIds(@RequestParam("ids") List<Long> ids);
}
