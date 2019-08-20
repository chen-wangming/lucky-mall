package com.luckymall;

import com.luckymall.common.Constant;
import com.luckymall.entity.ProductType;
import com.luckymall.entity.User;
import com.luckymall.mapper.ProductTypeMapper;
import com.luckymall.mapper.UserMapper;
import com.luckymall.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 方法说明：用户插入测试
     */
    @Test
    public void insertUser() {
        User user = new User();
        user.setUsername("chengcc");
        user.setPassword("123456qwer");
        user.setEmail("2784359@qq.com");
        System.out.println("user:" + user);
        System.out.println(userMapper.insertByUser(user));
    }

    /**
     * 方法说明：用户删除测试
     */
    @Test
    public void deleteUser() {
        System.out.println(userMapper.deleteUserById(5));
    }

    /**
     * 方法说明：更新用户信息测试
     */
    @Test
    public void updateUser() {
        User user = userMapper.findUserById(6);
        System.out.println("before user: " + user);
        user.setNickname("chenwangming");
        System.out.println("after user: " + user);
        System.out.println("update: " + userMapper.updateUserById(user));
    }

    /**
     * 方法说明：添加商品种类测试
     */
    @Test
    public void insertType() {
        ProductType productType = new ProductType();
        productType.setName("服装");
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        productType.setUpdateTime(addTime);
        System.out.println("插入type：" + typeMapper.addType(productType));
    }

    /**
     * 方法说明：修改商品种类测试
     */
    @Test
    public void updateType() {
        ProductType productType = typeMapper.findTypeById(2);
        productType.setName("食品");
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        productType.setUpdateTime(addTime);
        System.out.println("更新商品种类：" + typeMapper.updateTypeById(productType));
    }
}