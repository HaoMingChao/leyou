package com.leyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.pojo.Stock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends EasyBaseMapper<Stock>{
    @Insert("INSERT INTO tb_stock (skuid,seckill_stock,seckill_total,stock) VALUES (#{skuId},#{seckillStock},#{seckillTotal},#{stock})")
    void addStock(Stock stock);
}
