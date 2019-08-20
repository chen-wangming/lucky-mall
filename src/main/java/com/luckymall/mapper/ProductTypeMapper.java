package com.luckymall.mapper;

import com.luckymall.entity.ProductType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 商品种类mapper
 * @Author: wangming.chen
 * @Date: 2019/8/9 9:44
 */
@Mapper
@Repository
public interface ProductTypeMapper {

    /**
     * 方法说明：统计商品种类数量
     *
     * @return int 商品种类数量
     */
    @Select("select count(*) from t_product_type")
    int countProductType();

    /**
     * 方法说明：根据id查询商品种类
     * @param id 商品种类id
     * @return com.luckymall.entity.ProductType 商品种类
     */
    @Select("select * from t_product_type where id = #{id}")
    ProductType findTypeById(@Param("id") int id);

    /**
     * 方法说明：根据类别名查询商品类别
     * @param name 商品类别名
     * @return com.luckymall.entity.ProductType 商品类别
     */
    @Select("select * from t_product_type where name = #{name}")
    ProductType findTypeByName(String name);

    /**
     * 方法说明：查询所有种类
     *
     * @return java.util.List<com.luckymall.entity.ProductType> 商品种类列表
     */
    @Select("select * from t_product_type")
    List<ProductType> findAllType();

    /**
     * 方法说明：根据id删除种类
     * @param id 商品id
     * @return int 1/删除成功 2/删除失败
     */
    @Delete("delete from t_product_type where id = #{id}")
    int deleteTypeById(int id);

    /**
     * 方法说明：修改商品种类
     * @param productType 商品种类
     * @return int 1/修改成功 0/修改失败
     */
    @Update("update t_product_type set name = #{name},update_time=#{updateTime} where id = #{id}")
    int updateTypeById(ProductType productType);

    /**
     * 方法说明：添加商品种类
     * @param productType 商品种类
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_product_type(name,update_time) values(#{productType.name},#{productType.updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "productType.id",keyColumn = "id")
    int addType(@Param("productType") ProductType productType);
}
