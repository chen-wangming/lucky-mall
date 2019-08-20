package com.luckymall.service;

import com.luckymall.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 订单服务层
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:53
 */
public interface OrderService {

    /**
     * 方法说明：添加订单
     *
     * @param numArr   商品数量数组
     * @param idArr    商品id数组
     * @param priceArr 商品单价数组
     * @return com.luckymall.common.Result 结果
     */
    public Result addOrder(String[] numArr, String[] idArr, String[] priceArr);

    /**
     * 方法说明：用户查看订单
     *
     * @return org.springframework.web.servlet.ModelAndView 用户订单视图
     */
    public ModelAndView userOrder();

    /**
     * 方法说明：用户支付订单
     *
     * @param id 订单id
     * @return com.luckymall.common.Result 结果
     */
    public Result payOrder(int id);

    /**
     * 方法说明：用户查看订单详情页
     *
     * @param orderId 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情页视图
     */
    public ModelAndView userOrderDetail(int orderId);

    /**
     * 方法说明：商品详情页点击立即购买
     *
     * @param id     商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 结果
     */
    public Result buy(int id, int number);

    /**
     * 方法说明：管理员获取订单列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 订单列表视图
     */
    @RequestMapping("/orderList")
    public ModelAndView orderList(int start, int size);

    /**
     * 方法说明：管理员修改订单状态
     * @param id 订单id
     * @param status 订单状态 0/未支付 1/已支付
     * @return com.luckymall.common.Result  结果
     */
    public Result editOrderStatus(int id,int status);

    /**
     * 方法说明：按会员id搜索订单
     *
     * @param id  会员id
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 订单展示视图
     */
    public ModelAndView searchOrder(int id,int start,int size);

    /**
     * 方法说明：管理员删除订单
     * @param id 订单id
     * @return com.luckymall.common.Result  结果
     */
    public Result deleteOrder(int id);
}