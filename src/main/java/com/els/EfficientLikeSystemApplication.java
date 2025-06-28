package com.els;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengYuJun
 */
@MapperScan("com.els.mapper")
@SpringBootApplication
public class EfficientLikeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfficientLikeSystemApplication.class, args);
    }

}
