package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.GroupUser;

@Component
public interface GroupUserDao extends BaseDao<GroupUser> {
	
    List<GroupUser> findGroupUserByUserId(String userId);

    /**
     * 获取 群 下面的所有人员
     * @return
     */
    List<GroupUser> findUsersByGroupId(String group_id);

}
