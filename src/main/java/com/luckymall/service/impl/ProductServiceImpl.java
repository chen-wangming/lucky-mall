package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.Product;
import com.luckymall.entity.ProductType;
import com.luckymall.mapper.ProductMapper;
import com.luckymall.mapper.ProductTypeMapper;
import com.luckymall.service.ProductService;
import com.luckymall.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商品服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/10 1:54
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * 商品mapper
     */
    @Autowired
    private ProductMapper productMapper;

    /**
     * 商品种类mapper
     */
    @Autowired
    private ProductTypeMapper productTypeMapper;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * 方法说明：带着商品信息跳转到首页
     *
     * @return org.springframework.web.servlet.ModelAndView 跳转到首页视图
     */
    @Override
    public ModelAndView goIndex() {
        LOGGER.info("===============跳转到首页==============");
        ModelAndView modelAndView = new ModelAndView("index");
        // Map<商品种类,商品列表>
        Map<ProductType, List<Product>> productMap = new HashMap<>(6);
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        for (ProductType productType : productTypeList) {
            List<Product> productList = productMapper.findProductByType(productType.getId());
            // 每种商品最多存4个
            if (productList.size() > 4) {
                productMap.put(productType, productList.subList(0, 4));
            } else {
                productMap.put(productType, productList);
            }
        }
        LOGGER.info("商品信息map：" + JSON.toJSONString(productMap));
        modelAndView.addObject("map", productMap);
        return modelAndView;
    }

    /**
     * 方法说明：方法说明：根据商品种类查找相关商品
     *
     * @param id   商品种类id
     * @param name 商品种类名
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @Override
    public ModelAndView findProductByType(int id, String name) {
        LOGGER.info("===============按种类查找商品==============");

        ModelAndView modelAndView = new ModelAndView("mall/category");
        List<Product> productList = productMapper.findProductByType(id);
        modelAndView.addObject("productTypeName", name);
        modelAndView.addObject("list", productList);
        LOGGER.info("查找的商品种类：" + JSON.toJSONString(name));
        LOGGER.info("查询结果列表：" + JSON.toJSONString(productList));
        return modelAndView;
    }

    /**
     * 方法说明：根据搜索关键词查找相关商品
     *
     * @param key 搜索关键词
     * @return org.springframework.web.servlet.ModelAndView 返回商品视图
     */
    @Override
    public ModelAndView findProductByKey(String key) {
        LOGGER.info("===============按种类查找商品==============");
        ModelAndView modelAndView = new ModelAndView("mall/category");
        List<Product> productList = productMapper.findProductByName(key);
        modelAndView.addObject("productTypeName", key);
        modelAndView.addObject("list", productList);
        LOGGER.info("查询条件及结果：" + JSON.toJSONString(key) + " " + JSON.toJSONString(productList));
        return modelAndView;
    }


    /**
     * 方法说明：跳转到商品详情页
     *
     * @param id 商品id
     * @return org.springframework.web.servlet.ModelAndView 返回视图
     */
    @Override
    public ModelAndView productDetail(int id) {
        LOGGER.info("===============商品详情页跳转==============");
        ModelAndView modelAndView = new ModelAndView("mall/detail");
        Product product = productMapper.findProductById(id);
        LOGGER.info("商品：" + JSON.toJSONString(product));
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    /**
     * 方法说明：获取商品列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品列表视图
     */
    @Override
    public ModelAndView productList(int start, int size) {
        LOGGER.info("===============管理员获取商品列表==============");
        ModelAndView modelAndView = new ModelAndView("admin/product-list");
        // 分页
        PageHelper.startPage(start, size);
        List<Product> productList = productMapper.findAllProduct();
        PageInfo<Product> page = new PageInfo<>(productList);
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        LOGGER.info("商品列表：" + JSON.toJSONString(productList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        modelAndView.addObject("typeList", productTypeList);
        return modelAndView;
    }

    /**
     * 方法说明：修改商品状态
     *
     * @param id     商品id
     * @param status 商品状态 0/已下架 1/已上架
     * @return com.luckymall.common.Result  结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editProductStatus(int id, int status) {
        LOGGER.info("===============商品上下架==============");
        Result result = new Result();
        // 更新状态
        int newStatus = status == 0 ? 1 : 0;
        Product product = productMapper.findProductById(id);
        LOGGER.info("商品：" + JSON.toJSONString(product));
        product.setStatus(newStatus);
        int flag = productMapper.updateProductById(product);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.SUCCESS_MSG);
        }
        return result;
    }

    /**
     * 方法说明：添加商品
     *
     * @param file    商品图片文件
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addProduct(MultipartFile file, Product product) {
        LOGGER.info("===============添加商品==============");
        LOGGER.info("商品信息：" + JSON.toJSONString(product));
        Result result = new Result();
        // 图片上传
        String image = FileUploadUtil.savaFile(file, Constant.PRODUCT_IMAGE_PATH);
        product.setImage(image);
        product.setStatus(1);
        int flag = productMapper.insertByProduct(product);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：修改商品信息
     *
     * @param file    商品图片信息
     * @param product 商品信息
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editProduct(MultipartFile file, Product product) {
        LOGGER.info("===============修改商品信息==============");
        Product oldProduct = productMapper.findProductById(product.getId());
        LOGGER.info("修改前商品信息：" + JSON.toJSONString(oldProduct));
        Result result = new Result();
        oldProduct.setNumber(product.getNumber());
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setTypeId(product.getTypeId());
        if (!file.isEmpty()) {
            String image = FileUploadUtil.savaFile(file, Constant.PRODUCT_IMAGE_PATH);
            oldProduct.setImage(image);
        }
        LOGGER.info("修改后商品信息：" + JSON.toJSONString(oldProduct));
        int flag = productMapper.updateProductById(oldProduct);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：按商品名搜索商品
     *
     * @param name  搜索词
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 商品展示视图
     */
    @Override
    public ModelAndView searchProduct(String name, int start, int size) {
        ModelAndView modelAndView = new ModelAndView("admin/product-search-result");
        LOGGER.info("================搜索商品===============");
        // 分页
        PageHelper.startPage(start, size);
        List<Product> productList = productMapper.findProductByName(name);
        PageInfo<Product> page = new PageInfo<>(productList);
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        LOGGER.info("搜索结果：" + JSON.toJSONString(productList));
        modelAndView.addObject("page", page);
        modelAndView.addObject("typeList", productTypeList);
        // 搜索关键词
        modelAndView.addObject("key",name);
        return modelAndView;
    }
}
