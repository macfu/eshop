package com.macfu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description 主程序入口
 * @author: macfu
 * @create: 2020-05-02
 **/
@SpringBootApplication
@MapperScan("com.macfu.mapper")
public class StartSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartSpringBootApplication.class, args);
    }

}
