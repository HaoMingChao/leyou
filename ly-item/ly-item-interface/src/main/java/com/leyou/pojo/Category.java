package com.leyou.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @package: com.leyou.pojo
 * @project-name: leyou
 * @description: goods category
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/1/28 20:54
 */

@Data
@TableName("tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;
    private String name;

    @TableField(value = "parent_id")
    private Long parentId;

    private Boolean isParent;

    private Integer sort;
}
