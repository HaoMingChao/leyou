package com.leyou.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpuDetail;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @Classname SpuVo
 * @Description TODO
 * @Date 2021/3/4 16:17
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_spu")
public class SpuVo{

//    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String title;
    private String subTitle;
    private Boolean saleable;
    private Date createTime;
    @JsonIgnore
    private Boolean valid;
    @JsonIgnore
    private Date lastUpdateTime;
    @TableField(exist = false)
    private String cname;
    @TableField(exist = false)
    private String bname;
    @TableField(exist = false)
    private List<SkuVo> skus;
    @TableField(exist = false)
    private SpuDetail spuDetail;
}
