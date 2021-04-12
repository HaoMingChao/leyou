package com.leyou.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @Classname SpecParam
 * @Description TODO
 * @Date 2021/3/4 14:23
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_spec_param")
public class SpecParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long cid;

    @TableField(value = "group_id")
    private Long groupId;

    private String name;

    @TableField(value = "`numeric`")
    private Boolean numeric;

    private String unit;

    private Boolean generic;

    private Boolean searching;

    private String segments;
}
