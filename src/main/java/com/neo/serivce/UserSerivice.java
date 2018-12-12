package com.neo.serivce;

import java.util.List;

import com.neo.entity.UserEntity;

public interface UserSerivice{

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	UserEntity findUserById(String id);
    /**
     * 根据名称查具体的人
     *
     * @param name
     * @return
     */
    UserEntity findUserByUserName(String name);

    /**
     * 使用 token 查询 实体
     *
     * @param access_token
     * @return
     */
    UserEntity findUserByToken(String access_token);

    /**
     * 注册
     *
     * @param name
     * @param password
     * @return
     */
    UserEntity register(String name, String password, String avatar);


    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    List<UserEntity> findUsersByName(String name);
    
    /**
     * 保存用户
     * @param entity
     */
    void saveUser(UserEntity entity);
    
    /**
     * 查询所有用户
     * @return
     */
    List<UserEntity> findAllUser();
}
