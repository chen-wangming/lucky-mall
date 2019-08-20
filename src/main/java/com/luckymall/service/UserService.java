package com.luckymall.service;

import com.luckymall.common.Result;
import com.luckymall.entity.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 用户服务层
 * @Author: wangming.chen
 * @Date: 2019/8/2 9:55
 */
public interface UserService {

    /**
     * 方法说明：用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return com.luckymall.common.Result 返回验证信息 返回验证信息 success/登陆成功 error/验证失败
     */
    public Result loginUser(String username, String password);

    /**
     * 方法说明：用户退出登录接口
     *
     * @return java.lang.String 返回首页地址
     */
    public String logout();

    /**
     * 方法说明：查看用户名是否存在
     *
     * @param username 用户名
     * @return com.luckymall.common.Result 返回验证信息 success/用户名不存在 error/用户名存在
     */
    public Result findUserByName(String username);

    /**
     * 方法说明：用户注册验证接口
     *
     * @param user 用户
     * @return com.luckymall.common.Result  success/注册成功 error/注册失败
     */
    public Result register(User user);

    /**
     * 方法说明：用户信息修改
     *
     * @param file 上传的图片
     * @param user 用户
     * @return com.luckymall.common.Result 返回验证信息
     */
    public Result editUser(MultipartFile file, User user);

    /**
     * 方法说明：修改用户密码
     *
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return com.luckymall.common.Result  返回验证信息
     */
    public Result editPassword(String oldPassword, String password);

    /**
     * 方法说明：管理员获取会员列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 会员列表视图
     */
    public ModelAndView userList(int start, int size);

    /**
     * 方法说明：按会员名搜索会员
     *
     * @param name  搜索词
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 会员展示视图
     */
    public ModelAndView searchUser(String name, int start, int size);

    /**
     * 方法说明：管理员添加会员
     *
     * @param user 会员
     * @return com.luckymall.common.Result 结果
     */
    public Result addUser(User user);

    /**
     * 方法说明：修改会员状态
     *
     * @param id     会员id
     * @param status 会员状态 0/已禁用 1/已启用
     * @return com.luckymall.common.Result  结果
     */
    public Result editUserStatus(int id, int status);

    /**
     * 方法说明：修改会员信息
     * @param file  会员头像信息
     * @param user 会员信息
     * @return com.luckymall.common.Result 结果
     */
    public Result editUserByAdmin(MultipartFile file, User user);

    /**
     * 方法说明：管理员修改会员密码
     * @param username  会员名
     * @param password  密码
     * @return com.luckymall.common.Result 结果
     */
    public Result editPasswordByAdmin(String username,String password);
}
