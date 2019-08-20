package com.luckymall.service;


import com.luckymall.common.Result;
import com.luckymall.entity.Product;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 积分记录服务层
 * @Author: wangming.chen
 * @Date: 2019/8/13 11:04
 */
public interface ScoreRecordService {

    /**
     * 方法说明：用户查看积分明细
     *
     * @return org.springframework.web.servlet.ModelAndView 积分视图
     */
    public ModelAndView userScore();

    /**
     * 方法说明：用户积分抽奖
     *
     * @return org.springframework.web.servlet.ModelAndView 积分抽奖视图
     */
    public ModelAndView userLottery();

    /**
     * 方法说明：抽奖结果处理
     * @param product 抽中的商品
     * @return com.luckymall.common.Result 结果
     */
    public Result doLottery(Product product);

    /**
     * 方法说明：获取积分明细列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分明细列表视图
     */
    public ModelAndView scoreRecordList(int start, int size);

    /**
     * 方法说明：按会员id搜索积分明细
     *
     * @param id    会员id
     * @param start 分页 当前页
     * @param size  分页 每页条数
     * @return org.springframework.web.servlet.ModelAndView 积分明细展示视图
     */
    public ModelAndView searchScoreRecord(int id, int start, int size);
}
