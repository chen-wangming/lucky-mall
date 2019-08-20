package com.luckymall.common;

/**
 * @Description: 返回数据封装类
 * @Author: wangming.chen
 * @Date: 2019/8/4 9:30
 */
public class Result<T> {
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private T data;

    public Result() {
    }

    public Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
