package com.luckymall.entity;

/**
 * @Description: 商品种类
 * @Author: wangming.chen
 * @Date: 2019/8/9 9:38
 */
public class ProductType {
    /**
     * 商品种类id
     */
    private int id;
    /**
     * 商品种类名
     */
    private String name;
    /**
     * 最后修改时间
     */
    private String updateTime;

    /**
     * 方法说明：无参构造方法
     */
    public ProductType() {
    }

    /**
     * 方法说明：构造方法
     * @param id 类别id
     * @param name 类别名
     * @param updateTime 更新时间
     */
    public ProductType(int id, String name, String updateTime) {
        this.id = id;
        this.name = name;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
