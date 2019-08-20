package com.luckymall.mapper;

import com.luckymall.entity.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 购物车mapper
 * @Author: wangming.chen
 * @Date: 2019/8/10 15:50
 */
@Mapper
@Repository
public interface CartMapper {

    /**
     * 方法说明：根据用户id和商品id查询购物车
     * @param useId 用户id
     * @param productId 购物车id
     * @return com.luckymall.entity.Cart 购物车
     */
    @Select("select * from t_cart where user_id = #{userId} and product_Id = #{productId}")
    Cart findCartByUserIdAndProductId(@Param("userId") int useId,@Param("productId") int productId);

    /**
     * 方法说明：根据用户id查询购物车
     * @param userId 用户id
     * @return java.util.List<com.luckymall.entity.Cart> 购物车列表
     */
    @Select("select * from t_cart where user_id = #{userId}")
    List<Cart> findCartByUserId(@Param("userId")int userId);

    /**
     * 方法说明：根据购物车id查询购物车
     * @param id 购物车id
     * @return com.luckymall.entity.Cart 购物车
     */
    @Select("select * from t_cart where id = #{id}")
    Cart findCartByCartId(@Param("id")int id);

    /**
     * 方法说明：根据用户id查找购物车里的数量
     * @param userId 用户id
     * @return int 商品数量
     */
    @Select("select count(*) from t_cart where user_id = #{userId} group by user_id")
    int countByUserId(@Param("userId")int userId);

    /**
     * 方法说明：添加购物车
     * @param cart 购物车
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_cart(add_time,number,product_id,user_id) values(#{cart.addTime},#{cart.number},#{cart.productId},#{cart.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "cart.id",keyColumn = "id")
    int addToCart(@Param("cart") Cart cart);

    /**
     * 方法说明：更新购物车
     * @param cart 购物车
     * @return int 1/更新成功 0/更新失败
     */
    @Update("update t_cart set add_time=#{addTime},number=#{number},product_id=#{productId},user_id=#{userId} where id=#{id}")
    int updateCart(Cart cart);

    /**
     * 方法说明：删除指定购物车id的购物车
     * @param id 购物车id
     * @return int 1/删除成功 0/删除失败
     */
    @Delete("delete from t_cart where id=#{id}")
    int deleteCartByCartId(@Param("id")int id);

    /**
     * 方法说明：删除某个用户的购物车
     * @param userId 用户id
     * @return int 删除的记录数
     */
    @Delete("delete from t_cart where user_id=#{userId}")
    int deleteCartByUserId(@Param("userId")int userId);

}
