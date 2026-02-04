package com.xjy.shujia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ShujiaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShujiaApplication.class, args);
    }

}
