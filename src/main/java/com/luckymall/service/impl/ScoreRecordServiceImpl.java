package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.*;
import com.luckymall.mapper.*;
import com.luckymall.service.ScoreRecordService;
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
import java.util.List;

/**
 * @Description: 积分记录服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/13 11:10
 */
@Service
public class ScoreRecordServiceImpl implements ScoreRecordService {

    /**
     * 积分记录mapper
     */
    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    /**
     * 商品mapper
     */
    @Autowired
    private ProductMapper productMapper;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreRecordServiceImpl.class);

    /**
     * 方法说明：用户查看积分明细
     *
     * @return org.springframework.web.servlet.ModelAndView 积分视图
     */
    @Override
    public ModelAndView userScore() {
        LOGGER.info("===============用户查看积分==============");
        ModelAndView modelAndView = new ModelAndView("user/score");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<ScoreRecord> scoreRecordList = scoreRecordMapper.findScoreRecordByUserId(user.getId());
        LOGGER.info("积分记录：" + JSON.toJSONString(scoreRecordList));
        int score = user.getScore();
        modelAndView.addObject("list", scoreRecordList);
        modelAndView.addObject("score", score);
        return modelAndView;
    }

    /**
     * 方法说明：用户积分抽奖
     *
     * @return org.springframework.web.servlet.ModelAndView 积分抽奖视图
     */
    @Override
    public ModelAndView userLottery() {
        LOGGER.info("===============积分抽奖==============");
        ModelAndView modelAndView = new ModelAndView("user/lottery");
        List<Product> productList = productMapper.findLotteryProduct();
        if (productList.size() > 8) {
            productList = productList.subList(0, 8);
        }
        modelAndView.addObject("list",productList);
        return modelAndView;
    }

    /**
     * 方法说明：抽奖结果处理
     * @param product 抽中的商品
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result doLottery(Product product){
        LOGGER.info("===============积分抽奖结果处理==============");
        LOGGER.info("抽中的商品为："+JSON.toJSONString(product));
        Result result =new Result();
        HttpSession session =request.getSession();
        // 用户积分减100
        User user = (User)session.getAttribute("user");
        user.setScore(user.getScore()-Constant.SCORE_PER_TIME);
        userMapper.updateUserById(user);
        session.setAttribute("user",user);
        // 总金额
        double totalPrice = product.getPrice();
        // 订单号
        String orderCode = String.valueOf(OrderIdGenerateUtil.get());
        // 下单时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());
        // 生成订单
        Order order = new Order();
        order.setStatus(1);
        order.setOrderCode(orderCode);
        order.setCreateTime(createTime);
        order.setTotalPrice(totalPrice);
        order.setUserId(user.getId());
        int orderFlag = orderMapper.insertOrder(order);
        // 生成订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getId());
        orderItem.setOrderId(order.getId());
        orderItem.setNumber(1);
        int orderItemFlag = orderItemMapper.addOrderItem(orderItem);
        // 添加积分记录
        ScoreRecord scoreRecord = new ScoreRecord();
        scoreRecord.setAddTime(createTime);
        scoreRecord.setPoint(-Constant.SCORE_PER_TIME);
        scoreRecord.setOrderId(order.getId());
        scoreRecord.setUserId(user.getId());
        scoreRecordMapper.addScoreRecord(scoreRecord);
        // 更新商品库存
        product.setNumber(product.getNumber() - 1);
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
     * 方法说明：获取积分明细列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分明细列表视图
     */
    @Override
    public ModelAndView scoreRecordList(int start, int size) {
        ModelAndView modelAndView = new ModelAndView("admin/scoreRecord-list");
        LOGGER.info("===============获取积分明细列表==============");
        PageHelper.startPage(start, size);
        List<ScoreRecord> scoreRecordList = scoreRecordMapper.findAllScoreRecord();
        PageInfo<ScoreRecord> page = new PageInfo<>(scoreRecordList);
        LOGGER.info("积分明细列表：" + JSON.toJSONString(scoreRecordList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
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
    @Override
    public ModelAndView searchScoreRecord(int id, int start, int size) {
        LOGGER.info("===============管理员搜索积分明细==============");
        ModelAndView modelAndView = new ModelAndView("admin/scoreRecord-search-result");
        // 分页
        PageHelper.startPage(start, size);
        List<ScoreRecord> scoreRecordList = scoreRecordMapper.findScoreRecordByUserId(id);
        PageInfo<ScoreRecord> page = new PageInfo<>(scoreRecordList);
        LOGGER.info("搜索结果：" + JSON.toJSONString(scoreRecordList));
        modelAndView.addObject("page", page);
        // 搜索词
        modelAndView.addObject("key", id);
        return modelAndView;
    }
}
