package com.leyou.mapper;

import com.leyou.pojo.Stock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper extends EasyBaseMapper<Stock>{
    @Insert("INSERT INTO tb_stock  ( sku_id,seckill_stock,seckill_total,stock )  VALUES  (#{skuId},#{seckillStock},#{seckillTotal},#{stock})")
    int addStock(Stock stock);

    @Select(" <script>" +
            "SELECT sku_id,seckill_stock,seckill_total,stock FROM tb_stock WHERE sku_id IN"+
            " <foreach collection='list' item='skuId' separator=',' open='(' close=')'>#{skuId}</foreach> "+
            "</script>"
    )
    List<Stock> selectIds(@Param("list") List<Long> list);
}
