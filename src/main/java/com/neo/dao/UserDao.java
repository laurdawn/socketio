package com.neo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.neo.entity.User;

@Component
public interface UserDao extends BaseDao<User> {

    //根据名称查询 user
    User findUserByUserName(String name);

    //根据Token查询 user
    User findUserByToken(String access_token);


    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    List<User> findUsersByName(@Param("name") String name);
    
    /**
     * 通过批量用户id批量查询用户信息
     * @param list
     * @return
     */
    List<User> selectUsersById(List<String> list);
}
