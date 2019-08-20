package com.luckymall.mapper;

import com.luckymall.entity.Score;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 积分规则mapper
 * @Author: wangming.chen
 * @Date: 2019/8/13 0:26
 */
@Mapper
@Repository
public interface ScoreMapper {

    /**
     * 方法说明：根据id查询积分规则
     *
     * @param id 积分规则id
     * @return com.luckymall.entity.Score 积分规则
     */
    @Select("select * from t_score where id=#{id}")
    Score findScoreById(int id);

    /**
     * 方法说明：根据价位查询积分规则
     * @param price 价位
     * @return com.luckymall.entity.Score 积分规则
     */
    @Select("select * from t_score where price=#{price}")
    Score findScoreByPrice(int price);

    /**
     * 方法说明：查询所有积分规则
     *
     * @return java.util.List<com.luckymall.entity.Score> 积分规则列表
     */
    @Select("select * from t_score")
    List<Score> findAllScore();

    /**
     * 方法说明：查询所有积分规则，按价格降序排序
     *
     * @return java.util.List<com.luckymall.entity.Score> 积分规则列表
     */
    @Select("select * from t_score order by price desc")
    List<Score> findAllScoreOrderByPriceDesc();

    /**
     * 方法说明：添加积分规则
     *
     * @param score 积分规则
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_score(add_time,price,percent) values(#{score.addTime},#{score.price},#{score.percent})")
    @Options(useGeneratedKeys = true, keyProperty = "score.id", keyColumn = "id")
    int addScore(@Param("score") Score score);

    /**
     * 方法说明：更新积分规则
     * @param score 积分规则
     * @return int 1/更新成功 0/更新失败
     */
    @Update("update t_score set add_time=#{addTime},price=#{price},percent=#{percent} where id=#{id}")
    int updateScore(Score score);

    /**
     * 方法说明：删除指定id积分规则
     * @param id 积分规则
     * @return int 1/删除成功 0/删除失败
     */
    @Delete("delete from t_score where id=#{id}")
    int deleteScoreById(int id);
}
