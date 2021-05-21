package com.leyou.mq;

import com.leyou.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname ItemListener
 * @Description item mq
 * @Date 2021/5/20 15:23
 * @Created by MingChao Hao
 */

@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue",declare = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenerSaveOrUpdate(Long spuId){
        if (spuId == null){
            return;
        }
        searchService.createOrUpdateIndex(spuId);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue",declare = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.Delate"}
    ))
    public void ListenerDelete(Long spuId){
        if (spuId == null){
            return;
        }
        try {
            searchService.DeleteIndex(spuId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
