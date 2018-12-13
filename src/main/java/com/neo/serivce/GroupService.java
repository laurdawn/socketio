package com.neo.serivce;

import java.util.List;

import com.neo.entity.Group;
import com.neo.entity.GroupMember;
import com.neo.entity.User;

public interface GroupService{

	/**
	 * 通过id查询群信息
	 * @param id
	 * @return
	 */
	Group findGroupById(String id);
	
    /**
     * 创建群组
     *
     * @param name
     * @return
     */
    Group creatGroup(String name, String avatar, User userEntity);


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    List<Group> findGroupsByGroupName(String groupName);


    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
    List<Group> findMyGroupsByUserId(String id);


    /**
     * 加入群组
     * @param entity
     * @param groupId
     * @return
     */
    GroupMember joinGroup(User user, Group group);


    /**
     * 获取 群下面的所有成员
     *
     * @return
     */
    List<GroupMember> findMembersByGroupId(String groupId);

}
