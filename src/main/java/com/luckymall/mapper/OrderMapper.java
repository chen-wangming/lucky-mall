package com.luckymall.mapper;

import com.luckymall.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 订单mapper
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:51
 */
@Mapper
@Repository
public interface OrderMapper {

    /**
     * 方法说明：统计订单数量
     *
     * @return int 订单数量
     */
    @Select("select count(*) from t_order")
    int countOrder();

    /**
     * 方法说明：根据订单id查询订单
     * @param id 订单id
     * @return com.luckymall.entity.Order 订单
     */
    @Select("select * from t_order where id=#{id}")
    Order findOrderByOrderId(int id);

    /**
     * 方法说明：根据订单号查询订单
     * @param orderCode 订单号
     * @return com.luckymall.entity.Order 订单
     */
    @Select("select * from t_order where order_code=#{orderCode}")
    Order findOrderByOrderCode(String orderCode);

    /**
     * 方法说明：根据用户id查询订单
     * @param userId 用户id
     * @return java.util.List<com.luckymall.entity.Order> 订单列表
     */
    @Select("select * from t_order where user_id=#{userId}")
    List<Order> findOrderByUserId(@Param("userId") int userId);

    /**
     * 方法说明：根据用户id和订单状态查询订单
     * @param userId 用户id
     * @param status 订单状态 0/未支付 1/已支付
     * @return java.util.List<com.luckymall.entity.Order> 订单列表
     */
    @Select("select * from t_order where user_id=#{userId} and status=#{status}")
    List<Order> findOrderByUserIdAndStatus(@Param("userId") int userId,@Param("status") int status);

    /**
     * 方法说明：查询所有订单
     *
     * @return java.util.List<com.luckymall.entity.Order>   订单列表
     */
    @Select("select * from t_order")
    List<Order> findAllOrder();

    /**
     * 方法说明：添加订单
     * @param order 订单
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_order(order_code,create_time,status,total_price,user_id) values(#{order.orderCode},#{order.createTime},#{order.status},#{order.totalPrice},#{order.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "order.id",keyColumn = "id")
    int insertOrder(@Param("order") Order order);

    /**
     * 方法说明：修改订单
     * @param order 订单
     * @return int 1/修改成功 0/修改失败
     */
    @Update("update t_order set order_code=#{orderCode},create_time=#{createTime},status=#{status},total_price=#{totalPrice},user_id=#{userId} where id=#{id}")
    int updateOrder(Order order);

    /**
     * 方法说明：根据id删除订单
     * @param id 订单id
     * @return int 删除的记录数
     */
    @Delete("delete from t_order where id=#{id}")
    int deleteOrderById(int id);
}
