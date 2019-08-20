package com.luckymall.service;

import com.luckymall.common.Result;
import com.luckymall.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 商品服务层
 * @Author: wangming.chen
 * @Date: 2019/8/10 1:53
 */
public interface ProductService {

    /**
     * 方法说明：带着商品信息跳转到首页
     *
     * @param
     * @return org.springframework.web.servlet.ModelAndView
     */
    public ModelAndView goIndex();

    /**
     * 方法说明：方法说明：根据商品种类查找相关商品
     *
     * @param id   商品种类id
     * @param name 商品种类名
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    public ModelAndView findProductByType(int id, String name);

    /**
     * 方法说明：根据搜索关键词查找相关商品
     *
     * @param key 搜索关键词
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    public ModelAndView findProductByKey(String key);

    /**
     * 方法说明：跳转到商品详情页
     *
     * @param id 商品id
     * @return org.springframework.web.servlet.ModelAndView 返回视图
     */
    public ModelAndView productDetail(int id);

    /**
     * 方法说明：获取商品列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品列表视图
     */
    public ModelAndView productList(int start, int size);

    /**
     * 方法说明：修改商品状态
     * @param id 商品id
     * @param status 商品状态 0/已下架 1/已上架
     * @return com.luckymall.common.Result  结果
     */
    public Result editProductStatus(int id, int status);

    /**
     * 方法说明：添加商品
     * @param file  商品图片文件
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    public Result addProduct(MultipartFile file, Product product);

    /**
     * 方法说明：修改商品信息
     * @param file  商品图片信息
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    public Result editProduct(MultipartFile file, Product product);

    /**
     * 方法说明：按商品名搜索商品
     * @param name 搜索词
     * @param start 分页 当前页
     * @param size 分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 商品展示视图
     */
    public ModelAndView searchProduct(String name,int start,int size);
}
