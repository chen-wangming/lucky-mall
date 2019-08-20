package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.Admin;
import com.luckymall.entity.Product;
import com.luckymall.entity.ProductType;
import com.luckymall.entity.User;
import com.luckymall.mapper.*;
import com.luckymall.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 管理员服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/14 9:22
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * 管理员mapper
     */
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 商品类别mapper
     */
    @Autowired
    private ProductTypeMapper productTypeMapper;

    /**
     * 商品mapper
     */
    @Autowired
    private ProductMapper productMapper;

    /**
     * 用户mapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 订单mapper
     */
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 订单项mapper
     */
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 积分规则mapper
     */
    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 积分记录mapper
     */
    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);


    /**
     * 方法说明：跳转后台欢迎页
     *
     * @return org.springframework.web.servlet.ModelAndView 欢迎页视图
     */
    @Override
    public ModelAndView welcome() {
        LOGGER.info("===============后台欢迎页==============");
        ModelAndView modelAndView = new ModelAndView("admin/welcome");
        int userNum = userMapper.countUser();
        int adminNum = adminMapper.countAdmin();
        int productNum = productMapper.countProduct();
        int productTypeNum = productTypeMapper.countProductType();
        int orderNum = orderMapper.countOrder();
        int scoreRecordNum = scoreRecordMapper.countScoreRecord();
        List<Integer> list = new ArrayList<>(6);
        LOGGER.info("user: " + userNum + ",order: " + orderNum + ",product: " + productNum + ",productType: " + productTypeNum + ",scoreRecord: " + scoreRecordNum + ",admin: " + adminNum);
        list.add(userNum);
        list.add(orderNum);
        list.add(productNum);
        list.add(productTypeNum);
        list.add(scoreRecordNum);
        list.add(adminNum);
        modelAndView.addObject("list",list);
        return modelAndView;
    }

    /**
     * 方法说明：管理员登录验证
     *
     * @param name     登录名
     * @param password 密码
     * @return com.luckymall.common.Result  返回验证信息 success/验证成功 error/验证失败
     */
    @Override
    public Result loginAdmin(String name, String password) {
        LOGGER.info("===============管理员登录==============");
        LOGGER.info("账号：" + name + " 密码：" + password);
        Result result = new Result();
        HttpSession session = request.getSession();
        Admin admin = adminMapper.findAdminByNameAndPassword(name, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }


    /**
     * 方法说明：修改商品类别信息
     *
     * @param id 要修改的商品类别id
     * @return org.springframework.web.servlet.ModelAndView 修改商品类别视图
     */
    @Override
    public ModelAndView editProductType(int id) {
        LOGGER.info("===============跳转修改商品类别页面==============");
        LOGGER.info("所要修改的商品类别id: " + id);
        ModelAndView modelAndView = new ModelAndView("admin/product-type-edit");
        ProductType productType = productTypeMapper.findTypeById(id);
        LOGGER.info("所要修改的商品类别: " + JSON.toJSONString(productType));
        modelAndView.addObject("productType", productType);
        return modelAndView;
    }

    /**
     * 方法说明：增加商品
     *
     * @return org.springframework.web.servlet.ModelAndView 增加商品视图
     */
    @Override
    public ModelAndView addProduct() {
        LOGGER.info("===============跳转添加商品页面==============");
        ModelAndView modelAndView = new ModelAndView("admin/product-add");
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        modelAndView.addObject("list", productTypeList);
        LOGGER.info("商品类别列表：" + JSON.toJSONString(productTypeList));
        return modelAndView;
    }

    /**
     * 方法说明：修改商品信息
     *
     * @param id 要修改的商品id
     * @return org.springframework.web.servlet.ModelAndView 修改商品视图
     */
    @Override
    public ModelAndView editProduct(int id) {
        LOGGER.info("===============跳转修改商品页面==============");
        ModelAndView modelAndView = new ModelAndView("admin/product-edit");
        Product product = productMapper.findProductById(id);
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        LOGGER.info("所要修改的商品：" + JSON.toJSONString(product));
        LOGGER.info("商品类别列表：" + JSON.toJSONString(productTypeList));
        modelAndView.addObject("product", product);
        modelAndView.addObject("list", productTypeList);
        return modelAndView;
    }

    /**
     * 方法说明：修改会员信息
     *
     * @param id 要修改的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员视图
     */
    @Override
    public ModelAndView editUser(int id) {
        LOGGER.info("===============跳转修改会员页面==============");
        ModelAndView modelAndView = new ModelAndView("admin/user-edit");
        User user = userMapper.findUserById(id);
        LOGGER.info("所要修改的会员：" + JSON.toJSONString(user));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /**
     * 方法说明：修改会员密码
     *
     * @param id 要修改密码的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员密码视图
     */
    @Override
    public ModelAndView editUserPassword(int id) {
        LOGGER.info("===============跳转修改会员密码页面==============");
        ModelAndView modelAndView = new ModelAndView("admin/user-password-edit");
        User user = userMapper.findUserById(id);
        LOGGER.info("要修改密码的会员：" + JSON.toJSONString(user));
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    /**
     * 方法说明：获取管理员列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 管理员列表视图
     */
    @Override
    public ModelAndView adminList(int start, int size) {
        LOGGER.info("===============获取管理员列表==============");
        ModelAndView modelAndView = new ModelAndView("admin/admin-list");
        PageHelper.startPage(start, size);
        List<Admin> adminList = adminMapper.findAllAdmin();
        PageInfo<Admin> page = new PageInfo<>(adminList);
        LOGGER.info("管理员列表：" + JSON.toJSONString(adminList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 方法说明：添加管理员
     *
     * @param admin 管理员
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(Admin admin) {
        LOGGER.info("===============添加管理员==============");
        LOGGER.info("管理员信息：" + JSON.toJSONString(admin));
        Result result = new Result();
        // 确保管理员名唯一
        Admin oldAdmin = adminMapper.findAdminByName(admin.getName());
        if (oldAdmin != null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        int flag = adminMapper.addAdmin(admin);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：跳转到管理员信息修改界面
     *
     * @param id 管理员id
     * @return org.springframework.web.servlet.ModelAndView 管理员信息修改视图
     */
    @Override
    public ModelAndView editAdmin(int id) {
        LOGGER.info("===============跳转到管理员信息修改界面==============");
        Admin admin = adminMapper.findAdminById(id);
        LOGGER.info("管理员信息：" + JSON.toJSONString(admin));
        ModelAndView modelAndView = new ModelAndView("admin/admin-edit");
        modelAndView.addObject("admin", admin);
        return modelAndView;
    }

    /**
     * 方法说明：修改管理员信息
     *
     * @param name     管理员名
     * @param password 管理员密码
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result edit(String name, String password) {
        LOGGER.info("===============修改管理员信息==============");
        Result result = new Result();
        Admin admin = adminMapper.findAdminByName(name);
        LOGGER.info("管理员信息：" + JSON.toJSONString(admin));
        LOGGER.info("新密码：" + password);
        admin.setPassword(password);
        int flag = adminMapper.updateAdmin(admin);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：删除管理员
     *
     * @param id 管理员id
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteAdmin(int id) {
        LOGGER.info("===============删除管理员==============");
        LOGGER.info("管理员id: " + id);
        Result result = new Result();
        int flag = adminMapper.deleteAdminById(id);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }
}
