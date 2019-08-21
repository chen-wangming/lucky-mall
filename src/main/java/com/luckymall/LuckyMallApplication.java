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

    /**
     * 方法说明：springboot启动类main方法
     * @param args 参数
     */
    public static void main(String[] args) {
        // springboot启动
        SpringApplication.run(LuckyMallApplication.class, args);
    }

}
