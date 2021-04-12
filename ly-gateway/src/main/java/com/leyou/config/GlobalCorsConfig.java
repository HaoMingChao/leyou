package com.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @package: com.leyou.config
 * @project-name: leyou
 * @description: 跨域请求配置cors
 * @author: Created by MingChao Hao
 * @create-datetime: 2021/2/15 19:27
 */
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
//        cors配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        允许的域，不可以写*,否则cookie就无法使用
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        corsConfiguration.addAllowedOrigin("http://www.leyou.com");
//        是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
//        允许的方式
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
//        允许的头信息
        corsConfiguration.addAllowedHeader("*");
//        有效时长
        corsConfiguration.setMaxAge(3600L);
//        添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
//        返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
