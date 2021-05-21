package com.leyou.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.leyou.pojo.SpecParam;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @Classname SpecGroupVoVo
 * @Description 规格参数组vo
 * @Date 2021/5/16 15:52
 * @Created by MingChao Hao
 */

@Data
@TableName("tb_spec_group")
public class SpecGroupVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String name;

    @TableField(value = "cid")
    private Long cid;

    @TableField(exist = false)
    private List<SpecParam> specParams;
}
