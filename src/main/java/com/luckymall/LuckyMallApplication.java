package com.luckymall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: springboot启动类
 * @Author: wangming.chen
 * @Date: 2019/8/2 15:09
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(value = {"com.luckymall.mapper"})
public class LuckyMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyMallApplication.class, args);
    }

}
