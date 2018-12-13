package com.neo.serivce.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.neo.dao.GroupDao;
import com.neo.dao.GroupMemberDao;
import com.neo.entity.Group;
import com.neo.entity.GroupMember;
import com.neo.entity.User;
import com.neo.serivce.GroupService;
import com.neo.utils.DateUtils;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupDao groupDao;
    
    @Autowired
    GroupMemberDao groupMemberDao;


    @Transactional
    @Override
    public Group creatGroup(String name, String iconUrl, User user) {

        //创建 群组
        Group entity = new Group();
        entity.setId(UUID.randomUUID().toString());
        entity.setCreateTime(DateUtils.getDataTimeYMD());
        entity.setName(name);
        entity.setOwnerId(user.getId());
        entity.setOwnerName(user.getUsername());
        entity.setIconUrl(iconUrl);
        groupDao.saveEntity(entity);

        //把自己加入群组
        joinGroup(user, entity);

        return entity;
    }

    //加入群组
    @Override
    public GroupMember joinGroup(User user, Group group) {

        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(group.getId());
        groupMember.setGroupName(group.getName());
        groupMember.setUserId(user.getId());
        groupMember.setUsername(user.getUsername());

        groupMemberDao.saveEntity(groupMember);
        return groupMember;
    }



    /**
     * 根据群id  获取 群下面的所有成员
     *
     * @return
     */
    @Override
    public List<GroupMember> findMembersByGroupId(String groupId) {
        return groupMemberDao.findMembersByGroupId(groupId);
    }


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    @Override
    public List<Group> findGroupsByGroupName(String groupName) {
        return groupDao.findGroupsByName(groupName);
    }

    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
    @Override
    public List<Group> findMyGroupsByUserId(String id) {
    	List<Group> list = groupDao.findGroupByOwnerId(id);
    	List<GroupMember> members = groupMemberDao.findGroupByUserId(id);
        for (GroupMember member : members) {
            Group entity = groupDao.findEntityById(member.getGroupId());
            if (!entity.getOwnerId().equals(id)) { //不是自己创建的群 (只是加入的群)
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 通过id获取群信息
     */
	@Override
	public Group findGroupById(String id) {
		return groupDao.findEntityById(id);
	}



}
