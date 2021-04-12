package com.leyou.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SpecGroup
 * @Description TODO
 * @Date 2021/3/4 12:55
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_spec_group")
public class SpecGroup  implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    @TableField(value = "cid")
    private Long cid;

    @TableField(value = "name")
    private String name;
}
