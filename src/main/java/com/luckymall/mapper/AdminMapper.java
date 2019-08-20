package com.luckymall.mapper;

import com.luckymall.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 管理员mapper
 * @Author: wangming.chen
 * @Date: 2019/8/13 17:03
 */
@Mapper
@Repository
public interface AdminMapper {

    /**
     * 方法说明：统计管理员数量
     *
     * @return int 管理员数量
     */
    @Select("select count(*) from t_admin")
    int countAdmin();

    /**
     * 方法说明：根据账户名查找管理员
     *
     * @param name 账号名
     * @return com.luckymall.entity.Admin 管理员
     */
    @Select("select * from t_admin where name=#{name}")
    Admin findAdminByName(@Param("name") String name);

    /**
     * 方法说明：根据账户名和密码查找管理员
     *
     * @param name     账号名
     * @param password 密码
     * @return com.luckymall.entity.Admin 管理员
     */
    @Select("select * from t_admin where name=#{name} and password=#{password}")
    Admin findAdminByNameAndPassword(@Param("name") String name, @Param("password") String password);

    /**
     * 方法说明：根据id查询管理员
     *
     * @param id 管理员id
     * @return com.luckymall.entity.Admin 管理员
     */
    @Select("select * from t_admin where id=#{id}")
    Admin findAdminById(@Param("id") int id);

    /**
     * 方法说明：查询所有管理员
     *
     * @return java.util.List<com.luckymall.entity.Admin> 管理员列表
     */
    @Select("select * from t_admin")
    List<Admin> findAllAdmin();

    /**
     * 方法说明：添加管理员
     *
     * @param admin 管理员
     * @return int 0/添加失败 1/添加成功
     */
    @Insert("insert into t_admin(name,password) values(#{admin.name},#{admin.password})")
    @Options(useGeneratedKeys = true, keyProperty = "admin.id", keyColumn = "id")
    int addAdmin(@Param("admin") Admin admin);

    /**
     * 方法说明：修改管理员信息
     *
     * @param admin 管理员
     * @return int  0/修改失败 1/修改成功
     */
    @Update("update t_admin set name=#{name},password=#{password} where id=#{id}")
    int updateAdmin(Admin admin);

    /**
     * 方法说明：删除指定id管理员
     *
     * @param id 管理员id
     * @return int 0/删除失败 1/删除成功
     */
    @Delete("delete from t_admin where id=#{id}")
    int deleteAdminById(int id);
}
