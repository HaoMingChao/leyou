package com.leyou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyou.pojo.Spu;
import com.leyou.service.GoodsService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname GoodsController
 * @Description TODO
 * @Date 2021/3/4 16:07
 * @Created by MingChao Hao
 */

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<IPage<SpuVo>> findSpuByPage(@RequestParam(value = "current",defaultValue = "1")int current, @RequestParam(value = "size",defaultValue = "5")int size, @RequestParam(value = "saleable",required = false)Boolean saleable, @RequestParam(value = "key",required = false)String key){
        return ResponseEntity.ok(goodsService.findSpuByPage(current,size,saleable,key));
    }

//    @PostMapping("/goods")
//    public ResponseEntity<Boolean> saveGoods(@RequestBody SpuVo spuVo){
//        System.out.println(spuVo);
//        System.out.println("方法进入");
////        goodsService.saveGoods(spuVo);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuVo spu){
        System.out.println(spu);
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
