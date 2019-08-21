package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.User;
import com.luckymall.mapper.UserMapper;
import com.luckymall.service.UserService;
import com.luckymall.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 用户实现层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/2 9:56
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户mapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 方法说明：用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return com.luckymall.common.Result 返回验证信息 success/登陆成功 error/验证失败
     */
    @Override
    public Result loginUser(String username, String password) {
        LOGGER.info("===============用户登录==============");
        HttpSession session = request.getSession();
        Result result = new Result();
        // 根据用户名和密码查找用户
        User user = userMapper.findUserByNameAndPassword(username, password);
        LOGGER.info("查询的用户信息：" + JSON.toJSONString(user));
        if (user == null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        } else if (user.getStatus() == 0) {
            result.setMsg(Constant.DISABLED_MSG);
            return result;
        }
        session.setAttribute("user", user);
        result.setMsg(Constant.SUCCESS_MSG);
        return result;
    }

    /**
     * 方法说明：用户退出登录
     *
     * @return java.lang.String 返回首页地址
     */
    @Override
    public String logout() {
        LOGGER.info("===============用户退出==============");
        request.getSession().invalidate();
        return "redirect:/index";
    }

    /**
     * 方法说明：查看用户名是否存在
     *
     * @param username 用户名
     * @return com.luckymall.common.Result 返回验证信息 success/用户名不存在 error/用户名存在
     */
    @Override
    public Result findUserByName(String username) {
        LOGGER.info("===============用户名是否存在==============");
        HttpSession session = request.getSession();
        Result result = new Result();
        // 根据用户名查找用户
        User user = userMapper.findUserByUsername(username);
        LOGGER.info("根据用户名查询的用户信息：" + JSON.toJSONString(user));
        if (user == null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        session.setAttribute("user", user);
        result.setMsg(Constant.SUCCESS_MSG);
        return result;
    }

    /**
     * 方法说明：用户注册验证
     *
     * @param user 用户
     * @return com.luckymall.common.Result  success/注册成功 error/注册失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result register(User user) {
        LOGGER.info("===============用户注册==============");
        LOGGER.info("注册用户信息：" + JSON.toJSONString(user));
        Result result = new Result();
        HttpSession session = request.getSession();
        user.setScore(0);
        user.setStatus(1);
        user.setImage(Constant.DEFAULT_IMAGE);
        user.setNickname(user.getUsername());
        // flag=1/数据插入成功 flag=0/数据插入失败
        int flag = userMapper.insertByUser(user);
        // 若注册失败
        if (flag == 0) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        result.setMsg(Constant.SUCCESS_MSG);
        session.setAttribute("user", user);
        return result;
    }
    
    /**
     * 方法说明：用户信息修改
     *
     * @param file 上传的图片
     * @param user 用户
     * @return com.luckymall.common.Result 返回验证信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editUser(MultipartFile file, User user) {
        LOGGER.info("===============用户信息修改==============");
        Result result = new Result();
        HttpSession session = request.getSession();
        User oldUser = (User) session.getAttribute("user");
        LOGGER.info("修改前用户信息：" + JSON.toJSONString(oldUser));
        oldUser.setNickname(user.getNickname());
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        // 若上传了图片则对图片进行转存，并更新用户的头像
        if (!file.isEmpty()) {
            String image = FileUploadUtil.savaFile(file, Constant.USER_IMAGE_PATH);
            oldUser.setImage(image);
        }
        int flag = userMapper.updateUserById(oldUser);
        if (flag == 1) {
            session.setAttribute("user", oldUser);
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        LOGGER.info("修改后用户信息：" + JSON.toJSONString(oldUser));
        return result;
    }

    /**
     * 方法说明：修改用户密码
     *
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return com.luckymall.common.Result  返回验证信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editPassword(String oldPassword, String password) {
        LOGGER.info("===============用户密码修改==============");
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info("旧密码：" + JSON.toJSONString(oldPassword));
        // 若输入的密码错误，返回错误信息
        if (!oldPassword.equals(user.getPassword())) {
            result.setMsg(Constant.ERROR_MSG);
        } else {
            user.setPassword(password);
            userMapper.updateUserById(user);
            session.setAttribute("user", user);
            result.setMsg(Constant.SUCCESS_MSG);
        }
        LOGGER.info("新密码：" + JSON.toJSONString(password));
        return result;
    }

    /**
     * 方法说明：获取会员列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 会员列表视图
     */
    @Override
    public ModelAndView userList(int start, int size) {
        ModelAndView modelAndView = new ModelAndView("admin/user-list");
        LOGGER.info("===============管理员获取会员列表==============");
        PageHelper.startPage(start, size);
        List<User> userList = userMapper.findAllUser();
        PageInfo<User> page = new PageInfo<>(userList);
        LOGGER.info("会员列表：" + JSON.toJSONString(userList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 方法说明：按会员名搜索会员
     *
     * @param name  搜索词
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 会员展示视图
     */
    @Override
    public ModelAndView searchUser(String name, int start, int size) {
        LOGGER.info("===============管理员搜索会员==============");
        ModelAndView modelAndView = new ModelAndView("admin/user-search-result");
        // 分页
        PageHelper.startPage(start, size);
        List<User> userList = userMapper.searchUserByUsername(name);
        PageInfo<User> page = new PageInfo<>(userList);
        LOGGER.info("搜索结果：" + JSON.toJSONString(userList));
        modelAndView.addObject("page", page);
        // 搜索词
        modelAndView.addObject("key",name);
        return modelAndView;
    }

    /**
     * 方法说明：管理员添加会员
     *
     * @param user 会员
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addUser(User user) {
        LOGGER.info("===============管理员添加会员==============");
        LOGGER.info("会员信息：" + JSON.toJSONString(user));
        Result result = new Result();
        // 确保会员名唯一
        User oldUser = userMapper.findUserByUsername(user.getUsername());
        if (oldUser != null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        // 状态设为1，表示启用
        user.setStatus(1);
        // 初始积分为0
        user.setScore(0);
        user.setNickname(user.getUsername());
        user.setImage(Constant.DEFAULT_IMAGE);
        int flag = userMapper.insertByUser(user);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：管理员修改会员状态
     *
     * @param id     会员id
     * @param status 会员状态 0/已禁用 1/已启用
     * @return com.luckymall.common.Result  结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editUserStatus(int id, int status) {
        LOGGER.info("===============管理员修改会员状态==============");
        Result result = new Result();
        // 更新状态
        int newStatus = status == 0 ? 1 : 0;
        User user = userMapper.findUserById(id);
        LOGGER.info("用户信息：" + JSON.toJSONString(user));
        user.setStatus(newStatus);
        int flag = userMapper.updateUserById(user);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.SUCCESS_MSG);
        }
        return result;
    }

    /**
     * 方法说明：修改会员信息
     * @param file  会员头像信息
     * @param user 会员信息
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editUserByAdmin(MultipartFile file, User user){
        LOGGER.info("===============修改会员信息==============");
        User oldUser = userMapper.findUserByUsername(user.getUsername());
        LOGGER.info("修改前会员信息：" + JSON.toJSONString(oldUser));
        Result result = new Result();
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        oldUser.setNickname(user.getNickname());
        // 若上传了图片则对图片进行转存，并更新用户的头像
        if (!file.isEmpty()) {
            String image = FileUploadUtil.savaFile(file, Constant.USER_IMAGE_PATH);
            oldUser.setImage(image);
        }
        LOGGER.info("修改后会员信息：" + JSON.toJSONString(oldUser));
        int flag = userMapper.updateUserById(oldUser);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：管理员修改会员密码
     * @param username  会员名
     * @param password  密码
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editPasswordByAdmin(String username,String password){
        LOGGER.info("===============管理员修改会员密码==============");
        Result result = new Result();
        User user = userMapper.findUserByUsername(username);
        LOGGER.info("旧密码：" + JSON.toJSONString(user.getPassword()));
        user.setPassword(password);
        int flag = userMapper.updateUserById(user);
        LOGGER.info("新密码：" + JSON.toJSONString(password));
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }
}
