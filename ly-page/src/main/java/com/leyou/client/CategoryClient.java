package com.leyou.client;

import com.leyou.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Classname Category
 * @Description TODO
 * @Date 2021/4/28 20:15
 * @Created by MingChao Hao
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
