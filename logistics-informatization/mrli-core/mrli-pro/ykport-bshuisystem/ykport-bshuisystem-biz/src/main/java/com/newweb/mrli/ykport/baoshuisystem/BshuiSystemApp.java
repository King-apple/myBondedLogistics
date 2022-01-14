package com.newweb.mrli.ykport.baoshuisystem;

import com.newweb.mrli.common.feign.annotation.EnableMrliFeignClients;
import com.newweb.mrli.common.security.annotation.EnableMrliResourceServer;
import com.newweb.mrli.common.swagger.annotation.EnableMrliSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mrli archetype
 * <p>
 * 项目启动类
 */
@EnableMrliSwagger2
@EnableMrliFeignClients
@EnableDiscoveryClient
@EnableMrliResourceServer
@SpringBootApplication
public class BshuiSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(BshuiSystemApp.class, args);
    }
}
