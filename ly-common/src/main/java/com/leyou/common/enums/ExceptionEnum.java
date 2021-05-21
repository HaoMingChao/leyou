package com.leyou.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    CATEGORY_NOT_FOND(404,"商品分类没有查到"),
    CATEGORY_SKU_NOT_FOND(404,"商品SKU不存在"),
    CATEGORY_STOCk_NOT_FOND(404,"商品库存不存在"),
    BRAND_NOT_FOUND(404,"品牌不存在" ),
    BRAND_SAVE_ERROR(500, "新增品牌失败"),
    GOODS_UPDATE_ERROR(500, "修改商品失败"),
    GOODS_ID_CANNOT_BE_NULL(500, "商品id不能为空"),
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    UPLOAD_FILE_ERROR(500,"文件上传失败" ),
    SPEC_GROUP_NOT_FOND(404,"商品规格组没查到"),
    SPEC_PARAM_NOT_FOND(404,"商品参数没查到"),
    GOODS_NOT_FOND(404,"商品不存在"),
    GOODS_DETAIL_NOT_FOND(404,"商品详情不存在"),
    GOODS_SAVE_ERROR(500,"新增商品失败")
    ;
    private int code;
    private String msg;
}
