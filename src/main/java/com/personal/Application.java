package com.personal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: Application
 * @Description: main
 * @author: like
 * @date 2024/6/5 14:42
 */
@SpringBootApplication
@MapperScan("com.personal.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
