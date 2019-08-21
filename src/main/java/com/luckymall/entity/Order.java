package com.luckymall.entity;

/**
 * @Description: 订单类
 * @Author: wangming.chen
 * @Date: 2019/8/12 10:46
 */
public class Order {
    /**
     * 订单id
     */
    private int id;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 订单生成时间
     */
    private String createTime;
    /**
     * 订单状态 0/未支付 1/已支付
     */
    private int status;
    /**
     * 订单总金额
     */
    private double totalPrice;
    /**
     * 用户id
     */
    private int userId;

    /**
     * 方法说明：无参构造方法
     */
    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                '}';
    }
}
