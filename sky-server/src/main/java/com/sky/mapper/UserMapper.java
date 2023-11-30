package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    /*
     * 根据openID查找
     * */
    @Select("select * from sky_take_out.user where openid=#{openid}")
    User getByOpenId(String openid);

    /*
     * 插入用户
     * */
    void insert(User user);

    /*
     * 根据主键查找
     * */
    @Select("select * from sky_take_out.user where id=#{id}")
    User getById(Long userId);

    Integer countByMap(Map map);
}
