package com.luckymall.mapper;

import com.luckymall.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 用户mapper
 * @Author: wangming.chen
 * @Date: 2019/8/2 9:28
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 方法说明：统计用户数量
     *
     * @return int 用户数量
     */
    @Select("select count(*) from t_user")
    int countUser();

    /**
     * 方法说明：根据用户id查询用户
     * @param id 用户id
     * @return com.luckymall.entity.User 用户
     */
    @Select("select * from t_user where id=#{id}")
    User findUserById(@Param("id")int id);

    /**
     * 方法说明：根据用户名和密码查询用户
     * @param username  用户名
     * @param password  密码
     * @return com.luckymall.entity.User 用户
     */
    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findUserByNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 方法说明：根据用户名查找用户
     * @param username 用户名
     * @return com.luckymall.entity.User 用户
     */
    @Select("select * from t_user where username=#{username}")
    User findUserByUsername(@Param("username")String username);

    /**
     * 方法说明：根据用户名模糊搜索用户
     * @param username 搜索词
     * @return java.util.List<com.luckymall.entity.User> 用户列表
     */
    @Select("select * from t_user where username like '%${username}%'")
    List<User> searchUserByUsername(@Param("username") String username);

    /**
     * 方法说明：插入用户,调用user.getId()可获得自增后的主键id
     * @param user 用户
     * @return 返回1/插入成功 0/插入失败
     */
    @Insert("insert into t_user(username,password,phone,email,score,status,image,nickname) values(#{user.username},#{user.password},#{user.phone},#{user.email},#{user.score},#{user.status},#{user.image},#{user.nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id",keyColumn = "id")
    int insertByUser(@Param("user") User user);

    /**
     * 方法说明：查询所有用户
     *
     * @return java.util.List<com.luckymall.entity.User> 所有结果集
     */
    @Select("select * from t_user")
    List<User> findAllUser();

    /**
     * 方法说明：更新用户信息
     * @param user 用户
     * @return 返回1/更新成功 0/更新失败
     */
    @Update("update t_user set username=#{username},password=#{password},phone=#{phone},email=#{email},score=#{score},status=#{status},image=#{image},nickname=#{nickname} where id=#{id}")
    int updateUserById(User user);

    /**
     * 方法说明：删除指定id用户
     * @param id 用户id
     * @return 返回1/删除成功 0/删除失败
     */
    @Delete("delete from t_user where id=#{id}")
    int deleteUserById(@Param("id")int id);

}
