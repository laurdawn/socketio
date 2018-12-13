package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.GroupMember;

@Component
public interface GroupMemberDao extends BaseDao<GroupMember> {
	
    List<GroupMember> findGroupByUserId(String userId);

    /**
     * 获取 群 下面的所有人员通过群ID
     * @return
     */
    List<GroupMember> findMembersByGroupId(String groupId);

    /**
     * 获取 群 通过群名称
     * @return
     */
    GroupMember findMembersByGroupName(String groupName);

}
