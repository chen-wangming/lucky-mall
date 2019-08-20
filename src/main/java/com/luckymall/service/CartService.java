package com.luckymall.service;

import com.luckymall.common.Result;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 购物车服务层
 * @Author: wangming.chen
 * @Date: 2019/8/11 15:39
 */
public interface CartService {

    /**
     * 方法说明：加入购物车
     * @param id 商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 返回验证信息
     */
    public Result addToCart(int id, int number);

    /**
     * 方法说明：用户查看购物车
     *
     * @return org.springframework.web.servlet.ModelAndView 购物车视图
     */
    public ModelAndView userCart();

    /**
     * 方法说明：删除购物车
     * @param cartId 购物车id
     * @return java.lang.String 返回跳转地址
     */
    public String deleteCart(int cartId);
}
