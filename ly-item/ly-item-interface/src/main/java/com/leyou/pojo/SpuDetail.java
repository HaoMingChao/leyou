package com.leyou.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SpuDetail
 * @Description TODO
 * @Date 2021/3/4 15:35
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_spu_detail")
public class SpuDetail{

    @TableId(type = IdType.AUTO)
    @TableField(value = "spu_id")
    private Long spuId;

    private String description;

    private String specialSpec;

    private String genericSpec;

    private String packingList;

    private String afterService;
}
