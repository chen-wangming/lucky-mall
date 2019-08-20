package com.luckymall.controller;

import com.luckymall.common.Result;
import com.luckymall.entity.Score;
import com.luckymall.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 积分规则控制层
 * @Author: wangming.chen
 * @Date: 2019/8/17 17:26
 */
@Controller
@RequestMapping("/score")
public class ScoreController {

    /**
     * 积分规则服务层
     */
    @Autowired
    private ScoreService scoreService;


    /**
     * 方法说明：添加积分规则
     *
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(Score score) {
        Result result = scoreService.add(score);
        return result;
    }

    /**
     * 方法说明：修改积分规则
     *
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(Score score) {
        Result result = scoreService.edit(score);
        return result;
    }

    /**
     * 方法说明：删除积分规则
     *
     * @param id 积分规则id
     * @return com.luckymall.common.Result 结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(int id) {
        Result result = scoreService.delete(id);
        return result;
    }

}
