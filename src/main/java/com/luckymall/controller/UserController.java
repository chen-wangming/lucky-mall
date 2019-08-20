package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.entity.Product;
import com.luckymall.entity.User;
import com.luckymall.service.CartService;
import com.luckymall.service.OrderService;
import com.luckymall.service.ScoreRecordService;
import com.luckymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 用户控制层
 * @Author: wangming.chen
 * @Date: 2019/8/2 9:46
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;
    /**
     * 购物车服务层
     */
    @Autowired
    private CartService cartService;

    /**
     * 订单服务层
     */
    @Autowired
    private OrderService orderService;

    /**
     * 积分记录服务层
     */
    @Autowired
    private ScoreRecordService scoreRecordService;

    /**
     * 方法说明：用户登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return com.luckymall.common.Result  返回验证信息 success/验证成功 error/验证失败
     */
    @RequestMapping("/loginUser")
    @ResponseBody
    public Result loginUser(String username, String password) {
        Result result = new Result();
        // 根据用户名和密码查找用户
        result = userService.loginUser(username, password);
        return result;
    }

    /**
     * 方法说明：用户退出登录
     *
     * @return 返回商城首页
     */
    @RequestMapping("/logout")
    public String logout() {
        return userService.logout();
    }

    /**
     * 方法说明：查看用户名是否存在
     *
     * @param username 用户名
     * @return com.luckymall.common.Result 返回信息 success/用户名存在 error/用户名不存在
     */
    @RequestMapping("/hasUsername")
    @ResponseBody
    public Result findUserByName(String username) {
        Result result = new Result();
        // 根据用户名查找用户
        result = userService.findUserByName(username);
        return result;
    }


    /**
     * 方法说明：用户注册验证
     *
     * @param user 用户
     * @return com.luckymall.common.Result  success/注册成功 error/注册失败
     */
    @RequestMapping("/registerUser")
    @ResponseBody
    public Result register(User user) {
        Result result = userService.register(user);
        return result;
    }

    /**
     * 方法说明：用户信息修改
     *
     * @param file 上传的图片
     * @param user 用户
     * @return com.luckymall.common.Result 返回验证信息
     */
    @RequestMapping("/editUser")
    @ResponseBody
    public Result editUser(MultipartFile file, User user) {
        Result result = new Result();
        result = userService.editUser(file, user);
        return result;
    }

    /**
     * 方法说明：修改用户密码
     *
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return com.luckymall.common.Result  返回验证信息
     */
    @RequestMapping("/editPassword")
    @ResponseBody
    public Result editPassword(String oldPassword, String password) {
        Result result = new Result();
        result = userService.editPassword(oldPassword, password);
        return result;
    }

    /**
     * 方法说明：用户查看购物车
     *
     * @return org.springframework.web.servlet.ModelAndView 购物车视图
     */
    @RequestMapping("/cart")
    public ModelAndView userCart() {
        ModelAndView modelAndView = cartService.userCart();
        return modelAndView;
    }

    /**
     * 方法说明：用户查看订单
     *
     * @return org.springframework.web.servlet.ModelAndView 用户订单视图
     */
    @RequestMapping("/order")
    public ModelAndView userOrder() {
        ModelAndView modelAndView = orderService.userOrder();
        return modelAndView;
    }

    /**
     * 方法说明：用户查看订单详情页
     *
     * @param orderId 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情页视图
     */
    @RequestMapping("/orderDetail")
    public ModelAndView userOrderDetail(int orderId) {
        ModelAndView modelAndView = orderService.userOrderDetail(orderId);
        return modelAndView;
    }

    /**
     * 方法说明：用户查看积分明细
     *
     * @return org.springframework.web.servlet.ModelAndView 积分视图
     */
    @RequestMapping("/score")
    public ModelAndView userScore() {
        ModelAndView modelAndView = scoreRecordService.userScore();
        return modelAndView;
    }

    /**
     * 方法说明：用户积分抽奖
     *
     * @return org.springframework.web.servlet.ModelAndView 积分抽奖视图
     */
    @RequestMapping("/lottery")
    public ModelAndView userLottery(){
        ModelAndView modelAndView =scoreRecordService.userLottery();
        return modelAndView;
    }

    /**
     * 方法说明：抽奖结果处理
     * @param product 抽中的商品
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/doLottery")
    @ResponseBody
    public Result doLottery(Product product){
        Result result =scoreRecordService.doLottery(product);
        return result;
    }

    /**
     * 方法说明：管理员添加会员
     *
     * @param user 会员
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/admin/add")
    @ResponseBody
    public Result addUser(User user) {
        Result result = userService.addUser(user);
        return result;
    }

    /**
     * 方法说明：管理员修改会员状态
     *
     * @param id     会员id
     * @param status 会员状态 0/已禁用 1/已启用
     * @return com.luckymall.common.Result  结果
     */
    @RequestMapping("/admin/editUserStatus")
    @ResponseBody
    public Result editUserStatus(int id, int status) {
        Result result = userService.editUserStatus(id,status);
        return result;
    }

    /**
     * 方法说明：管理员修改会员信息
     * @param file  会员头像信息
     * @param user 会员信息
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/admin/edit")
    @ResponseBody
    public Result editUserByAdmin(MultipartFile file, User user){
        Result result=userService.editUserByAdmin(file,user);
        return result;
    }

    /**
     * 方法说明：管理员修改会员密码
     * @param username  会员名
     * @param password  密码
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/admin/editPassword")
    @ResponseBody
    public Result editPasswordByAdmin(String username,String password){
        Result result =userService.editPasswordByAdmin(username,password);
        return result;
    }
}
