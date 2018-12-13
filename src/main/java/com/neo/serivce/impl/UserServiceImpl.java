package com.neo.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.neo.dao.SysmsgDao;
import com.neo.dao.GroupDao;
import com.neo.dao.UserDao;
import com.neo.entity.User;
import com.neo.serivce.UserService;

@Component
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Resource
    GroupDao groupDao;


    @Resource
    SysmsgDao addMessageDao;

//        Query.query(Criteria.where("classObj.$id")
//                .is(new ObjectId("57fa4b99d4c68bb7d044d616"))), Student.class)


    @Override
    public User findUserByUserName(String name) {
        return userDao.findUserByUserName(name);
    }

    @Override
    public User findUserByToken(String access_token) {
        return userDao.findUserByToken(access_token);
    }

    /**
     * 注册
     *
     * @param name
     * @param password
     * @return
     */
//    @Override
//    public User register(String name, String password, String avatar) {
//
//        //用户名必须唯一
//        if (userDao.findUserByUserName(name) != null) {
//            throw new RepeatException(-1, "用户名不能重复");
//        }
//
//        //获取当前日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = new Date();
//        //获取三十天后日期
//        Calendar theCa = Calendar.getInstance();
//        theCa.setTime(today);
//        theCa.add(theCa.DATE, 30);
//        Date start = theCa.getTime();
//        String startDate = sdf.format(start);//三十天之后日期
//
//        User user = new User();
//        //test
//        user.setId(UUID.randomUUID().toString());
//        user.setAuth_token(UUID.randomUUID().toString());
//        user.setAuth_date(startDate);
//        user.setUsername(name);
//        user.setPassword(password);
//        user.setIconUrl(avatar);
//        user.setSign("一路有你");
//        userDao.saveEntity(user);
//        return user;
//    }

    /**
     * 根据用户名查询 对应的人员
     *
     * @return
     */
    @Override
    public List<User> findUsersByName(String name) {
        return userDao.findUsersByName(name);
    }

	@Override
	public User findUserById(String id) {
		return userDao.findEntityById(id);
	}

	@Override
	public void saveUser(User entity) {
		userDao.saveEntity(entity);
	}

	@Override
	public List<User> findAllUser() {
		return userDao.selectAll();
	}

	@Override
	public List<User> findUsersById(List<String> list) {
		return userDao.selectUsersById(list);
	}


}
