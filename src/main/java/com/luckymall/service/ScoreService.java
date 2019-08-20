package com.luckymall.service;

import com.luckymall.common.Result;
import com.luckymall.entity.Score;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 积分规则服务层
 * @Author: wangming.chen
 * @Date: 2019/8/13 11:05
 */
public interface ScoreService {

    /**
     * 方法说明：获取积分规则列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分规则列表视图
     */
    public ModelAndView scoreList(int start, int size);

    /**
     * 方法说明：添加积分规则
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    public Result add(Score score);

    /**
     * 方法说明：跳转到积分规则修改界面
     *
     * @param id 积分规则id
     * @return org.springframework.web.servlet.ModelAndView 积分规则修改视图
     */
    public ModelAndView editScore(int id);

    /**
     * 方法说明：修改积分规则
     *
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    public Result edit(Score score);

    /**
     * 方法说明：删除积分规则
     *
     * @param id 积分规则id
     * @return com.luckymall.common.Result 结果
     */
    public Result delete(int id);

}
