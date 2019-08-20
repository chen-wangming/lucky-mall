package com.luckymall.entity;

/**
 * @Description: 商品类
 * @Author: wangming.chen
 * @Date: 2019/8/9 14:08
 */
public class Product {
    /**
     * 商品id
     */
    private int id;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 商品价格
     */
    private double price;
    /**
     * 商品库存
     */
    private int number;
    /**
     * 商品状态 0代表已下架 1代表已上架
     */
    private int status;
    /**
     * 商品所属种类
     */
    private int typeId;

    public Product() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", status=" + status +
                ", typeId=" + typeId +
                '}';
    }
}
