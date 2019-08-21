package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.entity.Admin;
import com.luckymall.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description: 管理员控制层
 * @Author: wangming.chen
 * @Date: 2019/8/14 9:19
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 管理员服务层
     */
    @Autowired
    private AdminService adminService;

    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 订单服务层
     */
    @Autowired
    private OrderService orderService;

    /**
     * 订单服务层
     */
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 积分规则服务层
     */
    @Autowired
    private ScoreService scoreService;

    /**
     * 积分记录服务层
     */
    @Autowired
    private ScoreRecordService scoreRecordService;

    /**
     * 商品服务层
     */
    @Autowired
    private ProductService productService;

    /**
     * 商品类别服务层
     */
    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    /**
     * 方法说明：跳转后台首页
     *
     * @param
     * @return java.lang.String 后台首页地址
     */
    @RequestMapping("/index")
    public String index() {
        LOGGER.info("==================跳转后台首页=================");
        return "admin/index";
    }


    /**
     * 方法说明：跳转后台欢迎页
     *
     * @return org.springframework.web.servlet.ModelAndView 欢迎页视图
     */
    @RequestMapping("/welcome")
    public ModelAndView welcome(){
        ModelAndView modelAndView = adminService.welcome();
        return modelAndView;
    }

    /**
     * 方法说明：管理员登录验证
     *
     * @param name     登录名
     * @param password 密码
     * @return com.luckymall.common.Result  返回验证信息 success/验证成功 error/验证失败
     */
    @RequestMapping("/loginAdmin")
    @ResponseBody
    public Result loginAdmin(String name, String password) {
        Result result = new Result();
        result = adminService.loginAdmin(name, password);
        return result;
    }

    /**
     * 方法说明：管理员退出登录
     *
     * @return java.lang.String 返回登录页面
     */
    @RequestMapping("/logout")
    public String logout() {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/admin/index";
    }

    /**
     * 方法说明：获取商品类别列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品类别管理视图
     */
    @RequestMapping("/productTypeList")
    public ModelAndView productTypeList(@RequestParam(value = "start", defaultValue = "1") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = productTypeService.productTypeList(start, size);
        return modelAndView;
    }


    /**
     * 方法说明：跳转添加商品种类页面
     *
     * @return java.lang.String 添加商品种类网页地址
     */
    @RequestMapping("/addProductType")
    public String addProductType() {
        LOGGER.info("==================跳转添加商品种类页面=================");
        return "admin/product-type-add";
    }

    /**
     * 方法说明：修改商品类别信息
     *
     * @param id 要修改的商品类别id
     * @return org.springframework.web.servlet.ModelAndView 修改商品类别视图
     */
    @RequestMapping("/editProductType")
    public ModelAndView editProductType(int id) {
        ModelAndView modelAndView = adminService.editProductType(id);
        return modelAndView;
    }

    /**
     * 方法说明：获取商品列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品列表视图
     */
    @RequestMapping("/productList")
    public ModelAndView productList(@RequestParam(value = "start", defaultValue = "1") int start,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = productService.productList(start, size);
        return modelAndView;
    }

    /**
     * 方法说明：按商品名搜索商品
     *
     * @param name  搜索词
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 商品展示视图
     */
    @RequestMapping("/searchProduct")
    public ModelAndView searchProduct(@RequestParam(value = "searchProductName") String name,
                                      @RequestParam(value = "start", defaultValue = "1") int start,
                                      @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = productService.searchProduct(name, start, size);
        return modelAndView;
    }

    /**
     * 方法说明：增加商品
     *
     * @return org.springframework.web.servlet.ModelAndView 增加商品视图
     */
    @RequestMapping("/addProduct")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = adminService.addProduct();
        return modelAndView;
    }

    /**
     * 方法说明：修改商品信息
     *
     * @param id 要修改的商品id
     * @return org.springframework.web.servlet.ModelAndView 修改商品视图
     */
    @RequestMapping("/editProduct")
    public ModelAndView editProduct(int id) {
        ModelAndView modelAndView = adminService.editProduct(id);
        return modelAndView;
    }

    /**
     * 方法说明：获取会员列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 会员列表视图
     */
    @RequestMapping("/userList")
    public ModelAndView userList(@RequestParam(value = "start", defaultValue = "1") int start,
                                 @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = userService.userList(start, size);
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
    @RequestMapping("/searchUser")
    public ModelAndView searchUser(@RequestParam(value = "searchUserName") String name,
                                   @RequestParam(value = "start", defaultValue = "1") int start,
                                   @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = userService.searchUser(name, start, size);
        return modelAndView;
    }

    /**
     * 方法说明：跳转到添加会员界面
     *
     * @return java.lang.String 添加会员界面地址
     */
    @RequestMapping("/addUser")
    public String addUser() {
        LOGGER.info("==================跳转添加会员页面=================");
        return "admin/user-add";
    }

    /**
     * 方法说明：打开添加会员界面
     *
     * @return java.lang.String 添加会员界面地址
     */
    @RequestMapping("/addUserIndex")
    public String addUserIndex() {
        LOGGER.info("==================打开添加会员界面=================");
        return "admin/user-add-index";
    }

    /**
     * 方法说明：修改会员信息
     *
     * @param id 要修改的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员视图
     */
    @RequestMapping("/editUser")
    public ModelAndView editUser(int id) {
        ModelAndView modelAndView = adminService.editUser(id);
        return modelAndView;
    }

    /**
     * 方法说明：修改会员密码
     *
     * @param id 要修改密码的会员id
     * @return org.springframework.web.servlet.ModelAndView 修改会员密码视图
     */
    @RequestMapping("/editUserPassword")
    public ModelAndView editUserPassword(int id) {
        ModelAndView modelAndView = adminService.editUserPassword(id);
        return modelAndView;
    }

    /**
     * 方法说明：获取订单列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 订单列表视图
     */
    @RequestMapping("/orderList")
    public ModelAndView orderList(@RequestParam(value = "start", defaultValue = "1") int start,
                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = orderService.orderList(start, size);
        return modelAndView;
    }

    /**
     * 方法说明：按会员id搜索订单
     *
     * @param id    会员id
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 订单展示视图
     */
    @RequestMapping("/searchOrder")
    public ModelAndView searchOrder(@RequestParam(value = "searchUserId") int id,
                                    @RequestParam(value = "start", defaultValue = "1") int start,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        LOGGER.info("搜索order id：" + id);
        ModelAndView modelAndView = orderService.searchOrder(id, start, size);
        return modelAndView;
    }

    /**
     * 方法说明：管理员查看订单详情
     *
     * @param id 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情视图
     */
    @RequestMapping("/orderDetail")
    public ModelAndView orderDetail(int id) {
        ModelAndView modelAndView = orderService.userOrderDetail(id);
        return modelAndView;
    }


    /**
     * 方法说明：获取管理员列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 管理员列表视图
     */
    @RequestMapping("/adminList")
    public ModelAndView adminList(@RequestParam(value = "start", defaultValue = "1") int start,
                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = adminService.adminList(start, size);
        return modelAndView;
    }

    /**
     * 方法说明：跳转到添加管理员界面
     *
     * @return java.lang.String 添加管理员界面地址
     */
    @RequestMapping("/addAdmin")
    public String addAdmin() {
        return "admin/admin-add";
    }

    /**
     * 方法说明：添加管理员
     *
     * @param admin 管理员
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(Admin admin) {
        Result result = adminService.add(admin);
        return result;
    }

    /**
     * 方法说明：跳转到管理员信息修改界面
     *
     * @param id 管理员id
     * @return org.springframework.web.servlet.ModelAndView 管理员信息修改视图
     */
    @RequestMapping("/editAdmin")
    public ModelAndView editAdmin(int id) {
        ModelAndView modelAndView = adminService.editAdmin(id);
        return modelAndView;
    }


    /**
     * 方法说明：修改管理员信息
     *
     * @param name     管理员名
     * @param password 管理员密码
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(String name, String password) {
        Result result = adminService.edit(name, password);
        return result;
    }

    /**
     * 方法说明：删除管理员
     *
     * @param id 管理员id
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result deleteAdmin(int id) {
        Result result = adminService.deleteAdmin(id);
        return result;
    }

    /**
     * 方法说明：获取积分规则列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分规则列表视图
     */
    @RequestMapping("/scoreList")
    public ModelAndView scoreList(@RequestParam(value = "start", defaultValue = "1") int start,
                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = scoreService.scoreList(start, size);
        return modelAndView;
    }

    /**
     * 方法说明：跳转到添加积分规则界面
     *
     * @return java.lang.String 添加积分规则界面地址
     */
    @RequestMapping("/addScore")
    public String addScore() {
        return "admin/score-add";
    }

    /**
     * 方法说明：跳转到积分规则修改界面
     *
     * @param id 积分规则id
     * @return org.springframework.web.servlet.ModelAndView 积分规则修改视图
     */
    @RequestMapping("/editScore")
    public ModelAndView editScore(int id) {
        ModelAndView modelAndView = scoreService.editScore(id);
        return modelAndView;
    }


    /**
     * 方法说明：获取积分明细列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分明细列表视图
     */
    @RequestMapping("/scoreRecordList")
    public ModelAndView scoreRecordList(@RequestParam(value = "start", defaultValue = "1") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        ModelAndView modelAndView = scoreRecordService.scoreRecordList(start, size);
        return modelAndView;
    }

    /**
     * 方法说明：按会员id搜索积分明细
     *
     * @param id    会员id
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 积分明细展示视图
     */
    @RequestMapping("/searchScoreRecord")
    public ModelAndView searchScoreRecord(@RequestParam(value = "searchUserId") int id,
                                          @RequestParam(value = "start", defaultValue = "1") int start,
                                          @RequestParam(value = "size", defaultValue = "5") int size) {
        LOGGER.info("会员id：" + id);
        ModelAndView modelAndView = scoreRecordService.searchScoreRecord(id, start, size);
        return modelAndView;
    }
}
