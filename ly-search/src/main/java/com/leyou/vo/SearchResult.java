package com.leyou.vo;

import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.Goods;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Classname SearchResult
 * @Description 搜索vo
 * @Date 2021/5/13 16:13
 * @Created by MingChao Hao
 */

@Data
public class SearchResult extends PageResult<Goods> {

    private List<Category> categories; // 分类待选项

    private List<Brand> brands; //品牌待选项

    private List<Map<String,Object>> specs; //规格参数 key和待选项

    public SearchResult(){

    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
