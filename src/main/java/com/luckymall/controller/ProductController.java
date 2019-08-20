package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.entity.Product;
import com.luckymall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 商品控制层
 * @Author: wangming.chen
 * @Date: 2019/8/10 9:03
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    /**
     * 商品服务层
     */
    @Autowired
    private ProductService productService;

    /**
     * 方法说明：方法说明：根据商品种类查找相关商品
     *
     * @param id   商品种类id
     * @param name 商品种类名
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @RequestMapping("/findProductByType")
    public ModelAndView findProductByType(int id, String name) {
        ModelAndView modelAndView = productService.findProductByType(id, name);
        return modelAndView;
    }

    /**
     * 方法说明：根据搜索关键字查找相关商品
     *
     * @param key 搜素关键字
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @RequestMapping("/findProductByKey")
    public ModelAndView findProductByKey(String key) {
        ModelAndView modelAndView = productService.findProductByKey(key);
        return modelAndView;
    }

    /**
     * 方法说明：跳转到商品详情页
     *
     * @param id 商品id
     * @return org.springframework.web.servlet.ModelAndView 返回视图
     */
    @RequestMapping("/detail")
    public ModelAndView productDetail(int id) {
        ModelAndView modelAndView = productService.productDetail(id);
        return modelAndView;
    }

    /**
     * 方法说明：修改商品状态
     *
     * @param id     商品id
     * @param status 商品状态 0/已下架 1/已上架
     * @return com.luckymall.common.Result  结果
     */
    @RequestMapping("/editProductStatus")
    @ResponseBody
    public Result editProductStatus(int id, int status) {
        Result result = productService.editProductStatus(id, status);
        return result;
    }


    /**
     * 方法说明：添加商品
     *
     * @param file    商品图片文件
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result addProduct(MultipartFile file, Product product) {
        Result result = productService.addProduct(file,product);
        return result;
    }

    /**
     * 方法说明：修改商品信息
     * @param file  商品图片信息
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Result editProduct(MultipartFile file, Product product){
        Result result=productService.editProduct(file,product);
        return result;
    }
}
