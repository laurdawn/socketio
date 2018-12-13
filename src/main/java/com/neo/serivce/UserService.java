package com.neo.serivce;

import java.util.List;

import com.neo.entity.User;

public interface UserService{

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User findUserById(String id);
    /**
     * 根据名称查具体的人
     *
     * @param name
     * @return
     */
    User findUserByUserName(String name);

    /**
     * 使用 token 查询 实体
     *
     * @param access_token
     * @return
     */
    User findUserByToken(String access_token);

    /**
     * 注册
     *
     * @param name
     * @param password
     * @return
     */
//    User register(String name, String password, String avatar);


    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    List<User> findUsersByName(String name);
    
    /**
     * 保存用户
     * @param entity
     */
    void saveUser(User entity);
    
    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUser();
    
    /**
     * 通过批量用户id批量查询用户信息
     * @param list
     * @return
     */
    List<User> findUsersById(List<String> list);
}
