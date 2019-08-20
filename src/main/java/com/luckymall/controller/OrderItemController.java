package com.luckymall.controller;

import com.luckymall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 订单项控制层
 * @Author: wangming.chen
 * @Date: 2019/8/12 11:17
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    /**
     * 订单项服务层
     */
    @Autowired
    private OrderItemService orderItemService;


}
