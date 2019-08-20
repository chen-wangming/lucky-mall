package com.luckymall.entity;

/**
 * @Description: 积分规则类
 * @Author: wangming.chen
 * @Date: 2019/8/13 0:21
 */
public class Score {
    /**
     * 积分规则id
     */
    private int id;
    /**
     * 积分规则添加时间
     */
    private String addTime;
    /**
     * 计算积分所满足的最低价格
     */
    private int price;
    /**
     * 积分占订单总计的百分比
     */
    private double percent;

    public Score() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", addTime='" + addTime + '\'' +
                ", price=" + price +
                ", percent=" + percent +
                '}';
    }
}
