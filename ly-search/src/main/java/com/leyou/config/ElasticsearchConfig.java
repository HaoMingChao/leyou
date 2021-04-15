package com.leyou.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @Classname ElasticsearchConfig
 * @Description TODO
 * @Date 2021/4/14 14:56
 * @Created by MingChao Hao
 */

@Configuration
public class ElasticsearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        //有几个集群写几个！！
//                        new HttpHost("127.0.0.1",9200,"http"),
                        new HttpHost("192.168.110.100",9200,"http")
                )
        );
    }
}
