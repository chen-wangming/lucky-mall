package com.luckymall.service;

import com.luckymall.common.Result;
import com.luckymall.entity.Admin;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 管理员服务层
 * @Author: wangming.chen
 * @Date: 2019/8/14 9:21
 */
public interface AdminService {

    /**
     * 方法说明：跳转后台欢迎页
     *
     * @return org.springframework.web.servlet.ModelAndView 欢迎页视图
     */
    public ModelAndView welcome();

    /**
     * 方法说明：管理员登录验证
     *
     * @param name     登录名
     * @param password 密码
     * @return com.luckymall.common.Result  返回验证信息 success/验证成功 error/验证失败
     */
    public Result loginAdmin(String name, String password);

    /**
     * 方法说明：修改商品类别信息
     *
     * @param id 要修改的商品类别id
     * @return org.springframework.web.servlet.ModelAndView 修改商品类别视图
     */
    public ModelAndView editProductType(int id);

    /**
     * 方法说明：增加商品
     *
     * @return org.springframework.web.servlet.ModelAndView 增加商品视图
     */
    public ModelAndView addProduct();

    /**
     * 方法说明：修改商品信息
     *
     * @param id 要修改的商品id
     * @return org.springframework.web.servlet.ModelAndView 修改商品视图
     */
    public ModelAndView editProduct(int id);

    /**
     * 方法说明：修改会员信息
     *
     * @param id 要修改的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员视图
     */
    public ModelAndView editUser(int id);

    /**
     * 方法说明：修改会员密码
     *
     * @param id 要修改密码的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员密码视图
     */
    public ModelAndView editUserPassword(int id);


    /**
     * 方法说明：获取管理员列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 管理员列表视图
     */
    public ModelAndView adminList(int start, int size);

    /**
     * 方法说明：添加管理员
     *
     * @param admin 管理员
     * @return com.luckymall.common.Result 结果
     */
    public Result add(Admin admin);

    /**
     * 方法说明：跳转到修改管理员信息界面
     *
     * @param id 管理员id
     * @return org.springframework.web.servlet.ModelAndView 管理员信息修改视图
     */
    public ModelAndView editAdmin(int id);

    /**
     * 方法说明：修改管理员信息
     *
     * @param name     管理员名
     * @param password 管理员密码
     * @return com.luckymall.common.Result 结果
     */
    public Result edit(String name, String password);

    /**
     * 方法说明：删除管理员
     *
     * @param id 管理员id
     * @return com.luckymall.common.Result 结果
     */
    public Result deleteAdmin(int id);

}
