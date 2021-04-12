package com.leyou.common.vo;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * @Classname PageInfo
 * @Description TODO
 * @Date 2021/3/2 13:36
 * @Created by MingChao Haoj0
 */

@Data
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer current;
    private Integer size;
    private String sortBy;
    private Boolean desc;
    private String key;
}
