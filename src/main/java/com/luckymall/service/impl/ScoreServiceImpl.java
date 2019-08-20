package com.luckymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luckymall.common.Constant;
import com.luckymall.common.Result;
import com.luckymall.entity.Score;
import com.luckymall.mapper.ScoreMapper;
import com.luckymall.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 积分规则服务层实现类
 * @Author: wangming.chen
 * @Date: 2019/8/13 11:06
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    /**
     * 积分规则mapper
     */
    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 客户端请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreServiceImpl.class);


    /**
     * 方法说明：获取积分规则列表
     *
     * @param start 分页 当前页
     * @param size  分页 每页数据条数
     * @return org.springframework.web.servlet.ModelAndView 积分规则列表视图
     */
    @Override
    public ModelAndView scoreList(int start, int size) {
        LOGGER.info("====================获取积分规则列表====================");
        ModelAndView modelAndView = new ModelAndView("admin/score-list");
        PageHelper.startPage(start, size);
        List<Score> scoreList = scoreMapper.findAllScore();
        PageInfo<Score> page = new PageInfo<>(scoreList);
        LOGGER.info("积分规则列表：" + JSON.toJSONString(scoreList));
        LOGGER.info("分页情况：" + JSON.toJSONString(page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 方法说明：添加积分规则
     *
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(Score score) {
        LOGGER.info("================添加积分规则===============");
        LOGGER.info("积分规则：" + JSON.toJSONString(score));
        Result result = new Result();
        Score oldScore = scoreMapper.findScoreByPrice(score.getPrice());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String addTime = dateFormat.format(new Date());
        // 价位是否存在
        if (oldScore != null) {
            result.setMsg(Constant.ERROR_MSG);
            return result;
        }
        // 添加
        score.setAddTime(addTime);
        int flag = scoreMapper.addScore(score);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：跳转到积分规则修改界面
     *
     * @param id 积分规则id
     * @return org.springframework.web.servlet.ModelAndView 积分规则修改视图
     */
    @Override
    public ModelAndView editScore(int id) {
        LOGGER.info("================跳转到积分规则修改界面===============");
        ModelAndView modelAndView = new ModelAndView("admin/score-edit");
        Score score = scoreMapper.findScoreById(id);
        LOGGER.info("要修改的积分规则：" + JSON.toJSONString(score));
        modelAndView.addObject("score", score);
        return modelAndView;
    }

    /**
     * 方法说明：修改积分规则
     *
     * @param score 积分规则
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result edit(Score score) {
        LOGGER.info("================修改积分规则===============");
        Result result = new Result();
        Score oldScore = scoreMapper.findScoreById(score.getId());
        // 修改时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        String editTime = dateFormat.format(new Date());
        oldScore.setPrice(oldScore.getPrice());
        oldScore.setPercent(score.getPercent());
        oldScore.setAddTime(editTime);
        int flag = scoreMapper.updateScore(oldScore);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }

    /**
     * 方法说明：删除积分规则
     *
     * @param id 积分规则id
     * @return com.luckymall.common.Result 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(int id) {
        LOGGER.info("================删除积分规则===============");
        LOGGER.info("id: " + id);
        Result result = new Result();
        int flag = scoreMapper.deleteScoreById(id);
        if (flag == 1) {
            result.setMsg(Constant.SUCCESS_MSG);
        } else {
            result.setMsg(Constant.ERROR_MSG);
        }
        return result;
    }
}
