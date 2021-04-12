package com.leyou.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @Classname FastClientImporter
 * @Description TODO
 * @Date 2021/3/3 19:18
 * @Created by MingChao Hao
 */

@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastClientImporter {
}
