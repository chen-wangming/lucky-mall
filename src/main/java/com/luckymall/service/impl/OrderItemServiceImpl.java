package com.luckymall.service.impl;

import com.luckymall.mapper.OrderItemMapper;
import com.luckymall.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 订单项服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:53
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    /**
     * 订单项mapper
     */
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemServiceImpl.class);


}
