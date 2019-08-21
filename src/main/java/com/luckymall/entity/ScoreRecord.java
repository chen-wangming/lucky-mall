package com.luckymall.entity;

/**
 * @Description: 积分记录类
 * @Author: wangming.chen
 * @Date: 2019/8/13 0:13
 */
public class ScoreRecord {
    /**
     * 积分记录id
     */
    private int id;
    /**
     * 积分获得时间
     */
    private String addTime;
    /**
     * 获得积分点数
     */
    private int point;
    /**
     * 产生积分的订单id
     */
    private int orderId;
    /**
     * 获得积分的用户id
     */
    private int userId;

    /**
     * 方法说明：无参构造方法
     */
    public ScoreRecord() {
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ScoreRecord{" +
                "id=" + id +
                ", addTime='" + addTime + '\'' +
                ", point=" + point +
                ", orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }
}
