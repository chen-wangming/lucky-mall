package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 订单控制层
 * @Author: wangming.chen
 * @Date: 2019/8/12 11:16
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    /**
     * 订单服务层
     */
    @Autowired
    private OrderService orderService;

    /**
     * 方法说明：添加订单
     * @param numArr 商品数量数组
     * @param idArr 商品id数组
     * @param priceArr 商品单价数组
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result addOrder(String[] numArr,String[] idArr,String[] priceArr){
        Result result =orderService.addOrder(numArr,idArr,priceArr);
        return result;
    }

    /**
     * 方法说明：用户支付订单
     * @param id 订单id
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/pay")
    @ResponseBody
    public Result payOrder(int id){
        Result result = orderService.payOrder(id);
        return result;
    }

    /**
     * 方法说明：商品详情页点击立即购买
     * @param id    商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/buy")
    @ResponseBody
    public Result buy(int id,int number){
        Result result = orderService.buy(id,number);
        return result;
    }

    /**
     * 方法说明：管理员修改订单状态
     * @param id 订单id
     * @param status 订单状态 0/未支付 1/已支付
     * @return com.luckymall.common.Result  结果
     */
    @RequestMapping("/admin/editOrderStatus")
    @ResponseBody
    public Result editOrderStatus(int id,int status){
        Result result = orderService.editOrderStatus(id,status);
        return result;
    }

    /**
     * 方法说明：管理员删除订单
     * @param id 订单id
     * @return com.luckymall.common.Result  结果
     */
    @RequestMapping("/admin/deleteOrder")
    @ResponseBody
    public Result deleteOrder(int id){
        Result result = orderService.deleteOrder(id);
        return result;
    }
}
