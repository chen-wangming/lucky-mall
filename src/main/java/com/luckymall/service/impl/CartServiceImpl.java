package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.Cart;
import com.luckymall.entity.Product;
import com.luckymall.entity.User;
import com.luckymall.mapper.CartMapper;
import com.luckymall.mapper.ProductMapper;
import com.luckymall.service.CartService;
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
 * @Description: 购物车服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/11 15:40
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     * 购物车mapper
     */
    @Autowired
    private CartMapper cartMapper;
    /**
     * 商品mapper
     */
    @Autowired
    private ProductMapper productMapper;
    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    /**
     * 方法说明：加入购物车
     * @param id 商品id
     * @param number 商品数量
     * @return com.luckymall.common.Result 返回验证信息
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result addToCart(int id, int number){
        LOGGER.info("===============添加商品到购物车==============");
        LOGGER.info("商品id："+id+" 数量："+number);
        Result result=new Result();
        result.setMsg(Constant.ERROR_MSG);
        HttpSession session=request.getSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        User user = (User)session.getAttribute("user");
        if(user==null){
            result.setMsg(Constant.NOUSER_MSG);
            return result;
        }
        // 先查看用户该商品的购物车是否已存在
        Cart cart = cartMapper.findCartByUserIdAndProductId(user.getId(),id);

        // 若购物车不存在，则添加一个新的购物车;否则，修改已存在的购物车
        if(cart==null){
            Cart newCart =new Cart();
            newCart.setAddTime(addTime);
            newCart.setNumber(number);
            newCart.setProductId(id);
            newCart.setUserId(user.getId());
            cartMapper.addToCart(newCart);
            result.setMsg(Constant.SUCCESS_MSG);
        }else{
            cart.setAddTime(addTime);
            cart.setNumber(cart.getNumber()+number);
            cartMapper.updateCart(cart);
            result.setMsg(Constant.SUCCESS_MSG);
        }
        return result;
    }

    /**
     * 方法说明：用户查看购物车
     *
     * @return org.springframework.web.servlet.ModelAndView 购物车视图
     */
    @Override
    public ModelAndView userCart(){
        LOGGER.info("===============查看购物车==============");
        ModelAndView modelAndView = new ModelAndView("user/cart");
        HttpSession session=request.getSession();
        User user = (User)session.getAttribute("user");
        // Map<商品，该商品的购物车>
        Map<Product,Cart> productCartMap = new HashMap<Product,Cart>(50);
        // List<用户的购物车>
        List<Cart> cartList = cartMapper.findCartByUserId(user.getId());
        for(Cart cart:cartList){
            Product product = productMapper.findProductById(cart.getProductId());
            productCartMap.put(product,cart);
        }
        LOGGER.info("购物车map：" + JSON.toJSONString(productCartMap));
        modelAndView.addObject("cartMap",productCartMap);
        return modelAndView;
    }

    /**
     * 方法说明：删除购物车
     * @param cartId 购物车id
     * @return java.lang.String 返回跳转地址
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public String deleteCart(int cartId){
        int i = cartMapper.deleteCartByCartId(cartId);
        return "redirect:/user/cart";
    }

}
