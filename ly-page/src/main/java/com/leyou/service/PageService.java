package com.leyou.service;

import java.util.Map;

/**
 * @Classname PageService
 * @Description TODO
 * @Date 2021/5/17 9:13
 * @Created by MingChao Hao
 */

public interface PageService {

    Map<String, Object> loadModel(Long spuId);

    void createItemHtml(Long spuId);

    void deleteItemHtml(Long spuId);
}
