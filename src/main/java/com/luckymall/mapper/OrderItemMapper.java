package com.luckymall.mapper;

import com.luckymall.entity.OrderItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 订单项mapper
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:50
 */
@Mapper
@Repository
public interface OrderItemMapper {

    /**
     * 方法说明：根据订单id查询订单项
     * @param orderId 订单id
     * @return java.util.List<com.luckymall.entity.OrderItem> 订单项列表
     */
    @Select("select * from t_order_item where order_id=#{orderId}")
    List<OrderItem> findOrderItemByOrderId(int orderId);

    /**
     * 方法说明：根据id查找订单项
     * @param id 订单项id
     * @return com.luckymall.entity.OrderItem 订单项
     */
    @Select("select * from t_order_id where id=#{id}")
    OrderItem findOrderItemById(int id);

    /**
     * 方法说明：添加订单项
     * @param orderItem 订单项
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_order_item(number,product_id,order_id) values(#{orderItem.number},#{orderItem.productId},#{orderItem.orderId})")
    @Options(useGeneratedKeys = true, keyProperty = "orderItem.id",keyColumn = "id")
    int addOrderItem(@Param("orderItem") OrderItem orderItem);

    /**
     * 方法说明：更新订单项
     * @param orderItem 订单项
     * @return int 1/更新成功 0/更新失败
     */
    @Update("update t_order_item set number=#{orderItem.number},product_id=#{orderItem.productId},order_id=#{orderItem.orderId} where id=#{orderItem.id}")
    int updateOrderItem(OrderItem orderItem);

    /**
     * 方法说明：删除指定订单id的订单项
     * @param orderId 订单id
     * @return int 删除的记录数
     */
    @Delete("delete from t_order_item where order_id = #{orderId}")
    int deleteOrderItemByOrderId(int orderId);
}
