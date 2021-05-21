package com.leyou.mq;

import com.leyou.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname PageListener
 * @Description 静态页mq
 * @Date 2021/5/21 9:20
 * @Created by MingChao Hao
 */

@Component
public class PageListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insert.queue",declare = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenerSaveOrUpdate(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息 对静态页创建
        pageService.createItemHtml(spuId);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue",declare = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.Delate"}
    ))
    public void ListenerDelete(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息对静态页删除
        pageService.deleteItemHtml(spuId);
    }
}
