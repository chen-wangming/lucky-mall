package com.luckymall.service;

import com.luckymall.common.Result;
import com.luckymall.entity.ProductType;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description: 商品种类服务层
 * @Author: wangming.chen
 * @Date: 2019/8/9 10:39
 */
public interface ProductTypeService {

    /**
     * 方法说明：获取商品种类列表
     *
     * @return com.luckymall.common.Result<java.util.List < com.luckymall.entity.ProductType>> 包含商品种类列表信息
     */
    public Result<List<ProductType>> listProductType();

    /**
     * 方法说明：获取商品类别列表
     * @param start 当前页
     * @param size 每页数据个数
     * @return org.springframework.web.servlet.ModelAndView 商品类别管理视图
     */
    public ModelAndView productTypeList(int start,int size);

    /**
     * 方法说明：添加商品种类
     * @param productTypeName 商品类别名
     * @return com.luckymall.common.Result 结果
     */
    public Result addProductType(String productTypeName);

    /**
     * 方法说明：修改商品类别信息
     * @param productTypeId 商品类别id
     * @param productTypeName 修改后的商品类别名
     * @return com.luckymall.common.Result 结果
     */
    public Result editProductType(int productTypeId,String productTypeName);

    /**
     * 方法说明：删除商品类别
     *
     * @param id   商品类别id
     * @return com.luckymall.common.Result 结果
     */
    public Result deleteProductType(int id);
}
