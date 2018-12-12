package com.neo.serivce.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.neo.dao.GroupDao;
import com.neo.dao.GroupUserDao;
import com.neo.entity.GroupEntity;
import com.neo.entity.GroupUser;
import com.neo.entity.UserEntity;
import com.neo.exception.RepeatException;
import com.neo.serivce.GroupSerivice;
import com.neo.utils.DateUtils;

@Component
public class GroupSeriviceImpl implements GroupSerivice {

    @Autowired
    GroupDao groupDao;
    
    @Autowired
    GroupUserDao groupUserDao;


    @Transactional
    @Override
    public GroupEntity creatGroup(String name, String avatar, UserEntity user) {

        //不允许 创建重复的群名字
        if (groupDao.findGroupsByGroupName(name).size() > 0) {
            throw new RepeatException(-1, "群名称不能重复");
        }

        //创建 群组
        GroupEntity entity = new GroupEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setCreat_date(DateUtils.getDataTimeYMD());
        entity.setGroupname(name);
        entity.setUser_id(user.getId());
        entity.setUser_name(user.getUsername());
        entity.setAvatar(avatar);
        groupDao.saveEntity(entity);

        //把自己加入群组
        joinGroup(user, entity.getId());

        return entity;
    }

    //加入群组
    @Override
    public GroupUser joinGroup(UserEntity user, String groupId) {

        GroupUser groupUser = new GroupUser();
        groupUser.setId(UUID.randomUUID().toString());
        groupUser.setGroup_id(groupId);
        groupUser.setUser_id(user.getId());
        groupUser.setUsername(user.getUsername());
        groupUser.setAvatar(user.getAvatar());
        groupUser.setSign(user.getSign());
        groupUser.setCreateTime(DateUtils.getDataTimeYMDHMS());

        groupUserDao.saveEntity(groupUser);
        return groupUser;
    }



    /**
     * 根据群id  获取 群下面的所有成员
     *
     * @return
     */
    @Override
    public List<GroupUser> findUsersByGroupId(String group_id) {
        return groupUserDao.findUsersByGroupId(group_id);
    }


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    @Override
    public List<GroupEntity> findGroupsByGroupName(String groupName) {
        return groupDao.findGroupsByGroupName(groupName);
    }

    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
    @Override
    public List<GroupEntity> findMyGroupsByUserId(String id) {
    	List<GroupEntity> list = groupDao.findGroupByUserId(id);
    	List<GroupUser> users = groupUserDao.findGroupUserByUserId(id);
        for (GroupUser user : users) {
            GroupEntity entity = (GroupEntity) groupDao.findEntityById(user.getGroup_id());
            if (!entity.getUser_id().equals(id)) { //不是自己创建的群 (只是加入的群)
                list.add(entity);
            }
        }
        return list;
    }



}
