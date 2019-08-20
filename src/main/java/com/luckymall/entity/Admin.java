package com.luckymall.entity;

/**
 * @Description: 管理员类
 * @Author: wangming.chen
 * @Date: 2019/8/13 16:53
 */
public class Admin {

    /**
     * 管理员id
     */
    private int id;

    /**
     * 管理员登录名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    public Admin() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
