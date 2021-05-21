package com.leyou.client;

import com.leyou.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Classname GoodsClient
 * @Description TODO
 * @Date 2021/4/29 10:23
 * @Created by MingChao Hao
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
