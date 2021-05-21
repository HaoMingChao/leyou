package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname LyPage
 * @Description TODO
 * @Date 2021/5/16 14:50
 * @Created by MingChao Hao
 */

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class LyPage {
    public static void main(String[] args) {
        SpringApplication.run(LyPage.class,args);
    }
}
