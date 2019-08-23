package com.luckymall;

import com.luckymall.mapper.ProductTypeMapper;
import com.luckymall.mapper.UserMapper;
import com.luckymall.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuckyMallApplicationTests {

    /**
     * 用户mapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 商品种类mapper
     */
    @Autowired
    private ProductTypeMapper typeMapper;


    /**
     * 方法说明：用户数量统计测试
     */
    @Test
    public void countUser(){
        int num = userMapper.countUser();
        System.out.println("用户数量："+num);
    }
}