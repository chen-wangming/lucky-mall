package com.luckymall.mapper;

import com.luckymall.entity.ScoreRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 积分记录mapper
 * @Author: wangming.chen
 * @Date: 2019/8/13 0:43
 */
@Mapper
@Repository
public interface ScoreRecordMapper {

    /**
     * 方法说明：统计积分记录数量
     *
     * @return int 积分记录数量
     */
    @Select("select count(*) from t_score_record")
    int countScoreRecord();

    /**
     * 方法说明：根据id查询积分记录
     * @param id 积分记录id
     * @return com.luckymall.entity.ScoreRecord 积分记录
     */
    @Select("select * from t_score_record where id=#{id}")
    ScoreRecord findScoreRecordById(int id);

    /**
     * 方法说明：根据用户id查找积分记录
     * @param userId 用户id
     * @return java.util.List<com.luckymall.entity.ScoreRecord> 积分记录列表
     */
    @Select("select * from t_score_record where user_id=#{userId}")
    List<ScoreRecord> findScoreRecordByUserId(int userId);

    /**
     * 方法说明：根据订单id查找积分记录
     * @param orderId 订单id
     * @return java.util.List<com.luckymall.entity.ScoreRecord> 积分记录列表
     */
    @Select("select * from t_score_record where order_id=#{orderId}")
    List<ScoreRecord> findScoreRecordByOrderId(int orderId);

    /**
     * 方法说明：查找所有积分记录
     *
     * @return java.util.List<com.luckymall.entity.ScoreRecord> 积分记录列表
     */
    @Select("select * from t_score_record")
    List<ScoreRecord> findAllScoreRecord();

    /**
     * 方法说明：添加积分记录
     * @param scoreRecord 积分记录
     * @return int 1/添加成功 0/添加失败
     */
    @Insert("insert into t_score_record(add_time,point,order_id,user_id) values(#{scoreRecord.addTime},#{scoreRecord.point},#{scoreRecord.orderId},#{scoreRecord.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "scoreRecord.id",keyColumn = "id")
    int addScoreRecord(@Param("scoreRecord") ScoreRecord scoreRecord);

    /**
     * 方法说明：修改积分记录
     * @param scoreRecord 积分记录
     * @return int 1/修改成功 0/修改失败
     */
    @Update("update t_score_record set add_time=#{addTime},point=#{point},order_id=#{orderId},user_id=#{userId} where id=#{id}")
    int updateScoreRecord(ScoreRecord scoreRecord);

    /**
     * 方法说明：删除指定id的积分记录
     * @param id 积分记录id
     * @return int 1/删除成功 0/删除失败
     */
    @Delete("delete from t_score_record where id=#{id}")
    int deleteScoreRecordById(int id);

    /**
     * 方法说明：删除指定用户id的积分记录
     * @param userId 用户id
     * @return int 删除的记录数
     */
    @Delete("delete from t_score_record where user_id=#{userId}")
    int deleteScoreRecordByUserId(int userId);
}
