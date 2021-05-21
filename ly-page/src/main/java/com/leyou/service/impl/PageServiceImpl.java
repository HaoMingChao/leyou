package com.leyou.service.impl;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.PageService;
import com.leyou.vo.SkuVo;
import com.leyou.vo.SpecGroupVo;
import com.leyou.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Classname PageServiceImpl
 * @Description TODO
 * @Date 2021/5/17 9:13
 * @Created by MingChao Hao
 */

@Slf4j
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 根据supId向Model添加数据
     * @param spuId
     * @return
     */
    @Override
    public Map<String, Object> loadModel(Long spuId) {
        Map<String,Object> model = new HashMap<>();
        SpuVo spu = goodsClient.findSpuById(spuId);
        List<SkuVo> skus = goodsClient.findSkuById(spuId);
        SpuDetail detail = goodsClient.findDetailById(spuId);
        Brand brand = brandClient.findBrandById(spu.getBrandId());
        List<Category> categories = categoryClient.findCategoryByPid(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<SpecGroupVo> specs = specificationClient.findGroupByCid(spu.getCid3());
        System.out.println("specs = " + specs);
        model.put("sou",spu.getTitle());
        model.put("subTitle",spu.getSubTitle());
        model.put("skus",skus);
        model.put("detail",detail);
        model.put("brand",brand);
        model.put("categories",categories);
        model.put("specs",specs);
        return model;
    }

    /**
     * 根据supId删除静态页
     * @param spuId
     */
    @Override
    public void deleteItemHtml(Long spuId) {
        File dest = new File("/opt/nginx/html");
        deleteHtml(dest);
    }

    private void deleteHtml() {

    }

    /**
     * 根据spuId创建静态页
     * @param spuId
     */
    public void createItemHtml(Long spuId){
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        File dest = new File("/opt/nginx/html");
        deleteHtml(dest);
        try {
            PrintWriter writer = new PrintWriter(dest,"UTF-8");
            templateEngine.process("item",context,writer);
        } catch (Exception e) {
            log.error("[静态页面服务] 生成静态页面异常!", e);
        }
    }

    /**
     *判断静态页是否存在存在删除
     * @param dest
     */
    private void deleteHtml(File dest) {
        if (dest.exists()) {
            dest.delete();
        }
    }
}
