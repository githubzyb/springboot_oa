package com.zyb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyb.dao")
public class SpringbootOaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOaApplication.class, args);
    }

}
