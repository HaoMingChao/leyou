package com.leyou.controller;

import com.leyou.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.Map;

/**
 * @Classname PageController
 * @Description TODO
 * @Date 2021/5/16 15:28
 * @Created by MingChao Hao
 */

@Controller
@RequestMapping("/item")
public class  PageController {

    @Autowired
    private PageService pageService;

    /**
     * 跳转到商品详情页
     * @param spuId
     * @param model
     * @return
     */
    @GetMapping("/{id}.html")
    public String toItemPage(@PathVariable("id")Long spuId, Model model){
        Map<String,Object> attributes = pageService.loadModel(spuId);
        //准备模型数据
        model.addAllAttributes(attributes);
        //返回数据
        return "item";
    }
}
