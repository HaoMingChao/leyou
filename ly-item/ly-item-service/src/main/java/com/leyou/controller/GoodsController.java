package com.leyou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.GoodsService;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname GoodsController
 * @Description TODO
 * @Date 2021/3/4 16:07
 * @Created by MingChao Hao
Untitled */

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 根据spuId查询spu
     * @param spuId
     * @return
     */
    @GetMapping("/spu/{id}")
    public ResponseEntity<SpuVo> findSpuById(@PathVariable("id")Long id){
        return ResponseEntity.ok(goodsService.findSpuById(id));
    }

    /**
     * 分页查询spu
     * @param current
     * @param size
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<IPage<SpuVo>> findSpuByPage(@RequestParam(value = "current",defaultValue = "1")int current, @RequestParam(value = "size",defaultValue = "5")int size, @RequestParam(value = "saleable",required = false)Boolean saleable, @RequestParam(value = "key",required = false)String key){
        return ResponseEntity.ok(goodsService.findSpuByPage(current,size,saleable,key));
    }

    /**
     * 添加商品
     * @param spu
     * @return
     */
    @RequestMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuVo spu){
        if (spu.getId() == null){
            goodsService.saveGoods(spu);
        }else {
            goodsService.uploadGoods(spu);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品
     * @param spu
     * @return
     */
//    @PutMapping("/goods")
//    public ResponseEntity<Void> uploadGoods(@RequestBody SpuVo spu){
//        goodsService.uploadGoods(spu);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }



    /**
     * 根据spuid查询详情
     * @param supId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> findDetailById(@PathVariable("id")Long supId){
        return ResponseEntity.ok(goodsService.findDetailById(supId));
    }

    /**
     * 根据spuid查询下面sku
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<SkuVo>> findSkuById(@RequestParam("id")Long spuId){
        return ResponseEntity.ok(goodsService.findSkuById(spuId));
    }
}
