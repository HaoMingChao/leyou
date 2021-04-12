package com.leyou.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Classname PageResult
 * @Description TODO
 * @Date 2021/3/1 17:49
 * @Created by MingChao
 */

@Data
public class PageResult<T> {

    private static final long serialVersionUID = 1L;

    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageResult(){

    }
    public PageResult(Long total,List<T> items){
        this.total = total;
        this.items = items;
    }
    public PageResult(Long total,Integer totalPage,List<T> items){
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
