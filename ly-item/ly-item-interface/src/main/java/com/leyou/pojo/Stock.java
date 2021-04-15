package com.leyou.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname Stock
 * @Description TODO
 * @Date 2021/3/10 20:12
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "sku_id")
    private Long skuId;
    private Integer seckillStock;
    private Integer seckillTotal;
    private Integer stock;
}
