package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.ProductType;
import com.luckymall.mapper.ProductTypeMapper;
import com.luckymall.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 商品种类服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/9 10:40
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    /**
     * 方法说明：获取商品种类列表
     *
     * @return com.luckymall.common.Result<java.util.List < com.luckymall.entity.ProductType>> 包含商品种类列表信息
     */
    @Override
    public Result<List<ProductType>> listProductType() {
        LOGGER.info("===============获取商品种类==============");
        Result<List<ProductType>> result = new Result<>();
        List<ProductType> list = productTypeMapper.findAllType();
        if (list == null) {
            result.setMsg(Constant.ERROR_MSG);
        } else {
            result.setMsg(Constant.SUCCESS_MSG);
            result.setData(list);
        }
        LOGGER.info("商品种类列表：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 方法说明：获取商品类别列表
     *
     * @param start 当前页
     * @param size  每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品类别管理视图
     */
    @Override
    public ModelAndView productTypeList(int start, int size) {
        LOGGER.info("===============管理员获取商品种类==============");
        ModelAndView modelAndView = new ModelAndView("admin/product-type-list");
        // 分页
        PageHelper.startPage(start, size);
        List<ProductType> productTypeList = productTypeMapper.findAllType();
        PageInfo<ProductType> page = new PageInfo<>(productTypeList);
        LOGGER.info("商品种类列表：" + JSON.toJSONString(productTypeList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 方法说明：添加商品种类
     *
     * @param productTypeName 商品类别名
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addProductType(String productTypeName) {
        LOGGER.info("===============添加商品种类==============");
        LOGGER.info("添加类别：" + productTypeName);
        Result result = new Result();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String createTime = dateFormat.format(new Date());
        // 查看商品类别是否已存在
        ProductType oldProductType = productTypeMapper.findTypeByName(productTypeName);
        if (oldProductType != null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        ProductType productType = new ProductType();
        productType.setName(productTypeName);
        productType.setUpdateTime(createTime);
        int flag = productTypeMapper.addType(productType);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：修改商品类别信息
     *
     * @param productTypeId   商品类别id
     * @param productTypeName 修改后的商品类别名
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editProductType(int productTypeId, String productTypeName) {
        LOGGER.info("===============修改商品种类==============");
        Result result = new Result();
        // 查看商品类别是否已存在
        ProductType oldProductType = productTypeMapper.findTypeByName(productTypeName);
        if (oldProductType != null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String updateTime = dateFormat.format(new Date());
        ProductType productType = productTypeMapper.findTypeById(productTypeId);
        productType.setName(productTypeName);
        productType.setUpdateTime(updateTime);
        int flag = productTypeMapper.updateTypeById(productType);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：删除商品类别
     *
     * @param id 商品类别id
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteProductType(int id) {
        LOGGER.info("===============删除商品种类==============");
        LOGGER.info("商品id: " + id);
        Result result = new Result();
        int flag = productTypeMapper.deleteTypeById(id);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }
}
