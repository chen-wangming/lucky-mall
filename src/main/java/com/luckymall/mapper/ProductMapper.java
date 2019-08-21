package com.luckymall.mapper;

import com.luckymall.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 商品mapper
 * @Author: wangming.chen
 * @Date: 2019/8/9 15:10
 */
@Mapper
@Repository
public interface ProductMapper {

    /**
     * 方法说明：统计商品数量
     *
     * @return int 商品数量
     */
    @Select("select count(*) from t_product")
    int countProduct();

    /**
     * 方法说明：根据商品id查找商品
     *
     * @param id 商品id
     * @return com.luckymall.entity.Product 商品
     */
    @Select("select * from t_product where id = #{id}")
    Product findProductById(int id);

    /**
     * 方法说明：查询所有商品
     *
     * @return java.util.List<com.luckymall.entity.Product> 商品列表
     */
    @Select("select * from t_product")
    List<Product> findAllProduct();

    /**
     * 方法说明：根据种类id查询商品
     *
     * @param typeId 商品种类id
     * @return java.util.List<com.luckymall.entity.Product> 商品列表
     */
    @Select("select * from t_product where type_id = #{typeId}")
    List<Product> findProductByType(int typeId);

    /**
     * 方法说明：根据商品名模糊搜索
     *
     * @param name 搜索信息
     * @return java.util.List<com.luckymall.entity.Product> 商品列表
     */
    @Select("select * from t_product where name like '%${name}%'")
    List<Product> findProductByName(@Param("name") String name);


    /**
     * 方法说明：查询积分抽奖商品
     *
     * @return java.util.List<com.luckymall.entity.Product> 商品列表
     */
    @Select("select * from t_product where number>0 and status=1")
    List<Product> findLotteryProduct();

    /**
     * 方法说明：添加商品
     *
     * @param product 商品
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_product(name,image,price,number,status,type_id) values(#{product.name},"+
            "#{product.image},#{product.price},#{product.number},#{product.status},#{product.typeId})")
    @Options(useGeneratedKeys = true, keyProperty = "product.id", keyColumn = "id")
    int insertByProduct(@Param("product") Product product);

    /**
     * 方法说明：修改商品信息
     * @param product 商品
     * @return int 1/修改成功 0/修改失败
     */
    @Update("update t_product set name=#{name},image=#{image},price=#{price},number=#{number},status=#{status},type_id=#{typeId} where id=#{id}")
    int updateProductById(Product product);
}
