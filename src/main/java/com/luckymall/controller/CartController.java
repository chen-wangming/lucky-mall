package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 购物车控制层
 * @Author: wangming.chen
 * @Date: 2019/8/11 15:43
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    /**
     * 购物车服务层
     */
    @Autowired
    private CartService cartService;

    /**
     * 方法说明：加入购物车
     *
     * @param id     商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 返回验证信息
     */
    @RequestMapping("/addToCart")
    @ResponseBody
    public Result addToCart(int id, int number) {
        Result result = cartService.addToCart(id, number);
        return result;
    }

    /**
     * 方法说明：删除购物车
     * @param cartId 购物车id
     * @return java.lang.String 返回跳转地址
     */
    @RequestMapping("/deleteCart")
    public String deleteCart(int cartId){
        return cartService.deleteCart(cartId);
    }
}
