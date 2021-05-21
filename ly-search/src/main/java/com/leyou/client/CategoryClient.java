package com.leyou.client;

import com.leyou.api.CategoryApi;
import com.leyou.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Classname Category
 * @Description TODO
 * @Date 2021/4/28 20:15
 * @Created by MingChao Hao
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
