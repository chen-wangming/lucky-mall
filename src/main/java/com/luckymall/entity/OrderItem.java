package com.luckymall.entity;

/**
 * @Description: 订单项类
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:41
 */
public class OrderItem {
    /**
     * 订单项id
     */
    private int id;
    /**
     * 订单项中商品数量
     */
    private int number;
    /**
     * 订单项中商品id
     */
    private int productId;
    /**
     * 订单项中用户id
     */
    private int orderId;

    /**
     * 方法说明：无参构造方法
     */
    public OrderItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", number=" + number +
                ", productId=" + productId +
                ", orderId=" + orderId +
                '}';
    }
}
