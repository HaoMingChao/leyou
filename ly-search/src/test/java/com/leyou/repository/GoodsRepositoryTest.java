package com.leyou.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyou.client.GoodsClient;
import com.leyou.pojo.Goods;
import com.leyou.service.SearchService;
import com.leyou.vo.SpuVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class GoodsRepositoryTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    void testCreateIndex(){
        elasticsearchRestTemplate.createIndex(Goods.class);
        elasticsearchRestTemplate.putMapping(Goods.class);
    }
    @Test
    void loadData(){
        //查询sup信息
        int page = 1;
        int rows = 100;
        int size = 0;
        do {
            IPage<SpuVo> spuByPage = goodsClient.findSpuByPage(page, rows, true, null);
            List<SpuVo> spuList = spuByPage.getRecords();
            if (CollectionUtils.isEmpty(spuList)){
                break;
            }
            //构建成goods
            List<Goods> goodsList = spuList.stream().map(searchService::buildGoods).collect(Collectors.toList());
            //存进索引库
            elasticsearchRestTemplate.save(goodsList);
            page++;
            size = spuList.size();
        }while (size == 100);
    }

}