package com.neo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.neo.entity.UserEntity;

@Component
public interface UserDao extends BaseDao<UserEntity> {

    //根据名称查询 user
    UserEntity findUserByUserName(String name);

    //根据Token查询 user
    UserEntity findUserByToken(String access_token);


    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    List<UserEntity> findUsersByName(@Param("name") String name);
}
