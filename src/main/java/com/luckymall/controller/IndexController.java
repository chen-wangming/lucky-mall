package com.luckymall.controller;

import com.luckymall.service.ProductService;
import com.luckymall.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 页面跳转控制
 * @Author: wangming.chen
 * @Date: 2019/8/2 10:43
 */
@Controller
public class IndexController {

    /**
     * 商品种类服务层
     */
    @Autowired
    private ProductTypeService productTypeService;
    /**
     * 商品服务层
     */
    @Autowired
    private ProductService productService;

    /**
     * 方法说明：跳转登录界面
     *
     * @return 登录界面地址
     */
    @RequestMapping("/notLogin")
    public String notLogin() {
        return "error/notlogin";
    }

    /**
     * 方法说明：跳转登录界面
     *
     * @return 登录界面地址
     */
    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * 方法说明：访问根目录转发到首页
     *
     * @return java.lang.String 返回首页地址
     */
    @RequestMapping("/")
    public String index(){
        return "forward:/index";
    }

    /**
     * 方法说明：跳转商城首页并带上商品信息
     *
     * @return 商城首页视图
     */
    @RequestMapping("/index")
    public ModelAndView goIndex() {
        ModelAndView modelAndView = productService.goIndex();
        return modelAndView;
    }


    /**
     * 方法说明：跳转注册界面
     *
     * @return 注册界面地址
     */
    @RequestMapping("/register")
    public String register() {
        return "user/register";
    }

    /**
     * 方法说明：跳转用户个人中心页面
     *
     * @return 个人中心地址
     */
    @RequestMapping("/user_index")
    public String userIndex() {
        return "user/user_index";
    }

    /**
     * 方法说明：跳转用户信息修改页面
     *
     * @return 用户信息修改页面
     */
    @RequestMapping("/editUser")
    public String userEdit() {
        return "user/editUser";
    }

    /**
     * 方法说明：跳转用户密码修改页面
     *
     * @return 用户密码修改页面
     */
    @RequestMapping("/editPassword")
    public String passwordEdit() {
        return "user/editPassword";
    }

    /**
     * 方法说明：管理员登录
     *
     * @return java.lang.String 管理员登陆界面
     */
    @RequestMapping("/admin/login")
    public String adminLogin(){
        return "admin/login";
    }
}
