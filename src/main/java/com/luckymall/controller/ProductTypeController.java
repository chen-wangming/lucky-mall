package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.entity.ProductType;
import com.luckymall.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 商品种类控制层
 * @Author: wangming.chen
 * @Date: 2019/8/9 11:06
 */
@Controller
@RequestMapping("/productType")
public class ProductTypeController {
    /**
     * 商品种类服务层
     */
    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 方法说明：获取商品种类列表
     *
     * @return com.luckymall.common.Result<java.util.List < com.luckymall.entity.ProductType>> 包含商品种类列表信息
     */
    @RequestMapping("/listType")
    @ResponseBody
    public Result<List<ProductType>> listProductType() {
        Result<List<ProductType>> result = productTypeService.listProductType();
        return result;
    }

    /**
     * 方法说明：添加商品种类
     *
     * @param productTypeName 商品类别名
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result addProductType(String productTypeName) {
        Result result = productTypeService.addProductType(productTypeName);
        return result;
    }


    /**
     * 方法说明：修改商品类别信息
     *
     * @param productTypeId   商品类别id
     * @param productTypeName 修改后的商品类别名
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Result editProductType(int productTypeId, String productTypeName) {
        Result result = productTypeService.editProductType(productTypeId, productTypeName);
        return result;
    }

    /**
     * 方法说明：删除商品类别
     *
     * @param id 商品类别id
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result deleteProductType(int id) {
        Result result = productTypeService.deleteProductType(id);
        return result;
    }
}
