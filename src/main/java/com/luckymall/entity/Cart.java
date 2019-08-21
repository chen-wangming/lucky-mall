package com.luckymall.entity;

/**
 * @Description: 购物车类
 * @Author: wangming.chen
 * @Date: 2019/8/10 15:44
 */
public class Cart {

    /**
     * 购物车id
     */
    private int id;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 商品数量
     */
    private int number;
    /**
     * 商品id
     */
    private int productId;
    /**
     * 用户id
     */
    private int userId;

    /**
     * 方法说明：无参构造方法
     */
    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", addTime='" + addTime + '\'' +
                ", number=" + number +
                ", productId=" + productId +
                ", userId=" + userId +
                '}';
    }
}
