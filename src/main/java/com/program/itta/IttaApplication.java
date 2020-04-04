package com.program.itta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.program.itta.mapper")
public class IttaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IttaApplication.class, args);
    }

}
