package com.leyou.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    /**
     * 根据spuId查询spu
     * @param spuId
     * @return
     */
    @GetMapping("/spu/{id}")
    SpuVo findSpuById(@PathVariable("id")Long spuId);

    /**
     * 根据spuid查询详情
     * @param supId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail findDetailById(@PathVariable("id")Long supId);
    /**
     * 根据spuid查询下面sku
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    List<SkuVo> findSkuById(@RequestParam("id")Long spuId);

    /**
     * 分页查询spu
     * @param current
     * @param size
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    Page<SpuVo> findSpuByPage(@RequestParam(value = "current",defaultValue = "1")int current, @RequestParam(value = "size",defaultValue = "5")int size, @RequestParam(value = "saleable",required = false)Boolean saleable, @RequestParam(value = "key",required = false)String key);
}
