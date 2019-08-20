package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.*;
import com.luckymall.mapper.*;
import com.luckymall.service.OrderService;
import com.luckymall.util.OrderIdGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:57
 */
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * 订单mapper
     */
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 商品mapper
     */
    @Autowired
    private ProductMapper productMapper;

    /**
     * 订单项mapper
     */
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 用户mapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 购物车mapper
     */
    @Autowired
    private CartMapper cartMapper;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 方法说明：添加订单
     *
     * @param numArr   商品数量数组
     * @param idArr    商品id数组
     * @param priceArr 商品单价数组
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addOrder(String[] numArr, String[] idArr, String[] priceArr) {
        LOGGER.info("===============生成订单==============");
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 下单时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());
        // 订单号
        String orderCode = String.valueOf(OrderIdGenerateUtil.get());
        LOGGER.info("下单时间：" + createTime);
        LOGGER.info("订单号：" + orderCode);
        // 总金额
        double totalPrice = 0.0;
        for (int i = 0; i < numArr.length && i < priceArr.length; i++) {
            totalPrice += Integer.parseInt(numArr[i]) * Double.parseDouble(priceArr[i]);
        }
        // 生成订单
        Order order = new Order();
        order.setCreateTime(createTime);
        order.setOrderCode(orderCode);
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setUserId(user.getId());
        // 订单添加
        int orderFlag = orderMapper.insertOrder(order);
        int[] orderItemFlags = new int[idArr.length];
        int orderId = order.getId();
        for (int j = 0; j < idArr.length; j++) {
            int productId = Integer.parseInt(idArr[j]);
            int number = Integer.parseInt(numArr[j]);
            // 添加订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(number);
            orderItem.setProductId(productId);
            orderItem.setOrderId(orderId);
            orderItemFlags[j] = orderItemMapper.addOrderItem(orderItem);
            // 更新商品库存
            Product product = productMapper.findProductById(productId);
            product.setNumber(product.getNumber() - number);
            productMapper.updateProductById(product);
        }
        // 清空用户购物车
        cartMapper.deleteCartByUserId(user.getId());
        // 判断所有操作是否成功(标志是否都为1)
        int orderItemFlag = 1;
        for (int flag : orderItemFlags) {
            orderItemFlag *= flag;
        }
        // 插入结果
        if (orderFlag == 1 && orderItemFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：用户查看订单
     *
     * @return org.springframework.web.servlet.ModelAndView 用户订单视图
     */
    @Override
    public ModelAndView userOrder() {
        LOGGER.info("===============查看订单==============");
        ModelAndView modelAndView = new ModelAndView("user/order");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 用户所有订单
        List<Order> allOrderList = orderMapper.findOrderByUserId(user.getId());
        // 用户已支付订单
        List<Order> paidOrderList = orderMapper.findOrderByUserIdAndStatus(user.getId(), 1);
        // 用户未支付订单
        List<Order> unpaidOrderList = orderMapper.findOrderByUserIdAndStatus(user.getId(), 0);

        modelAndView.addObject("allOrderList", allOrderList);
        modelAndView.addObject("paidOrderList", paidOrderList);
        modelAndView.addObject("unpaidOrderList", unpaidOrderList);
        return modelAndView;
    }

    /**
     * 方法说明：用户支付订单
     * 支付订单时更新订单状态为已支付，
     * 并根据积分规则计算本次订单用户所能获得的积分，
     * 然后更新用户的积分
     *
     * @param id 订单id
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result payOrder(int id) {
        LOGGER.info("===============用户支付订单==============");
        LOGGER.info("订单号：" + id);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Result result = new Result();
        // 更新订单状态为已支付
        Order order = orderMapper.findOrderByOrderId(id);
        order.setStatus(1);
        int orderFlag = orderMapper.updateOrder(order);
        double totalPrice = order.getTotalPrice();
        // 用户获得积分
        List<Score> scoreList = scoreMapper.findAllScoreOrderByPriceDesc();
        LOGGER.info("积分规则：", JSON.toJSONString(scoreList));
        double percent = 0.0;
        // 判断本次订单属于哪种积分计算等级
        for (Score score : scoreList) {
            int price = score.getPrice();
            if (totalPrice >= price) {
                percent = score.getPercent();
                break;
            }
        }
        LOGGER.info("总金额：" + totalPrice + " 积分百分比：" + percent);
        int point = (int) (totalPrice * (percent / 100));
        user.setScore(user.getScore() + point);
        int userFlag = userMapper.updateUserById(user);
        // 添加积分记录
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        ScoreRecord scoreRecord = new ScoreRecord();
        scoreRecord.setAddTime(addTime);
        scoreRecord.setPoint(point);
        scoreRecord.setOrderId(order.getId());
        scoreRecord.setUserId(user.getId());
        int scoreRecordFlag = scoreRecordMapper.addScoreRecord(scoreRecord);
        // 操作是否成功
        if (orderFlag == 1 && userFlag == 1 && scoreRecordFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
            result.setData(point);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：用户查看订单详情页
     *
     * @param orderId 订单id
     * @return org.springframework.web.servlet.ModelAndView 订单详情页视图
     */
    @Override
    public ModelAndView userOrderDetail(int orderId) {
        LOGGER.info("===============用户查看订单详情==============");
        ModelAndView modelAndView = new ModelAndView("user/orderDetail");
        Order order = orderMapper.findOrderByOrderId(orderId);
        LOGGER.info("订单：" + JSON.toJSONString(order));
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(orderId);
        Map<Product, OrderItem> map = new HashMap<>(50);
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.findProductById(orderItem.getProductId());
            map.put(product, orderItem);
        }
        modelAndView.addObject("order", order);
        modelAndView.addObject("map", map);
        return modelAndView;
    }

    /**
     * 方法说明：商品详情页点击立即购买
     *
     * @param id     商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 结果
     */
    @Override
    public Result buy(int id, int number) {
        LOGGER.info("===============立即购买==============");
        LOGGER.info("商品id：" + id + " 购买数量：" + number);
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.setMsg(Constant.NOUSER_MSG);
            return result;
        }
        // 商品单价
        Product product = productMapper.findProductById(id);
        double price = product.getPrice();
        // 总金额
        double totalPrice = price * number;
        // 下单时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());
        // 订单号
        String orderCode = String.valueOf(OrderIdGenerateUtil.get());

        // 生成订单
        Order order = new Order();
        order.setOrderCode(orderCode);
        order.setCreateTime(createTime);
        order.setStatus(0);
        order.setUserId(user.getId());
        order.setTotalPrice(totalPrice);
        int orderFlag = orderMapper.insertOrder(order);

        // 生成订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(id);
        orderItem.setNumber(number);
        int orderItemFlag = orderItemMapper.addOrderItem(orderItem);

        // 更新商品库存
        product.setNumber(product.getNumber() - number);
        int productFlag = productMapper.updateProductById(product);
        // 判断所有操作是否成功(标志是否都为1)
        if (orderFlag == 1 && orderItemFlag == 1 && productFlag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：管理员获取订单列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 订单列表视图
     */
    @Override
    public ModelAndView orderList(int start, int size) {
        LOGGER.info("===============管理员获取订单列表==============");
        ModelAndView modelAndView = new ModelAndView("admin/order-list");
        PageHelper.startPage(start, size);
        List<Order> orderList = orderMapper.findAllOrder();
        PageInfo<Order> page = new PageInfo<>(orderList);
        LOGGER.info("订单列表：" + JSON.toJSONString(orderList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 方法说明：管理员修改订单状态
     *
     * @param id     订单id
     * @param status 订单状态 0/未支付 1/已支付
     * @return com.luckymall.common.Result  结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editOrderStatus(int id, int status) {
        LOGGER.info("===============管理员修改订单状态==============");
        Result result = new Result();
        int newStatus = status == 0 ? 1 : 0;
        Order order = orderMapper.findOrderByOrderId(id);
        LOGGER.info("订单信息：" + JSON.toJSONString(order));
        order.setStatus(newStatus);
        int flag = orderMapper.updateOrder(order);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.SUCCESS_MSG);
        }
        return result;
    }

    /**
     * 方法说明：按会员id搜索订单
     *
     * @param id    会员id
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 订单搜索结果展示视图
     */
    @Override
    public ModelAndView searchOrder(int id, int start, int size) {
        ModelAndView modelAndView = new ModelAndView("admin/order-search-result");
        LOGGER.info("===============按会员id搜索订单==============");
        PageHelper.startPage(start, size);
        List<Order> orderList = orderMapper.findOrderByUserId(id);
        PageInfo<Order> page = new PageInfo<>(orderList);
        LOGGER.info("订单列表：" + JSON.toJSONString(orderList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        // 搜索关键词
        modelAndView.addObject("key",id);
        return modelAndView;
    }

    /**
     * 方法说明：管理员删除订单
     *
     * @param id 订单id
     * @return com.luckymall.common.Result  结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteOrder(int id) {
        LOGGER.info("===============管理员删除订单==============");
        Result result = new Result();
        // 先删除订单项
        int orderItemFlag = orderItemMapper.deleteOrderItemByOrderId(id);
        // 再删除订单
        int orderFlag = orderMapper.deleteOrderById(id);
        if (orderItemFlag > 0 && orderFlag > 0) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }
}
