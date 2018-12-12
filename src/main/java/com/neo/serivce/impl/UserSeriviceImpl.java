package com.neo.serivce.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.neo.dao.AddMessageDao;
import com.neo.dao.GroupDao;
import com.neo.dao.UserDao;
import com.neo.entity.UserEntity;
import com.neo.exception.RepeatException;
import com.neo.serivce.UserSerivice;

@Component
public class UserSeriviceImpl implements UserSerivice {

    @Resource
    UserDao userDao;

    @Resource
    GroupDao groupDao;


    @Resource
    AddMessageDao addMessageDao;

//        Query.query(Criteria.where("classObj.$id")
//                .is(new ObjectId("57fa4b99d4c68bb7d044d616"))), Student.class)


    @Override
    public UserEntity findUserByUserName(String name) {
        return userDao.findUserByUserName(name);
    }

    @Override
    public UserEntity findUserByToken(String access_token) {
        return userDao.findUserByToken(access_token);
    }

    /**
     * 注册
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public UserEntity register(String name, String password, String avatar) {

        //用户名必须唯一
        if (userDao.findUserByUserName(name) != null) {
            throw new RepeatException(-1, "用户名不能重复");
        }

        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //获取三十天后日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, 30);
        Date start = theCa.getTime();
        String startDate = sdf.format(start);//三十天之后日期

        UserEntity user = new UserEntity();
        //test
        user.setId(UUID.randomUUID().toString());
        user.setAuth_token(UUID.randomUUID().toString());
        user.setAuth_date(startDate);
        user.setUsername(name);
        user.setPassword(password);
        user.setAvatar(avatar);
        user.setSign("一路有你");
        userDao.saveEntity(user);
        return user;
    }

    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    @Override
    public List<UserEntity> findUsersByName(String name) {
        return userDao.findUsersByName(name);
    }

	@Override
	public UserEntity findUserById(String id) {
		return userDao.findEntityById(id);
	}

	@Override
	public void saveUser(UserEntity entity) {
		userDao.saveEntity(entity);
	}

	@Override
	public List<UserEntity> findAllUser() {
		return userDao.selectAll();
	}


}
