package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.GroupEntity;

@Component
public interface GroupDao extends BaseDao<GroupEntity> {
	
    /**
     * 根据群的名字查询所有的群
     *
     * @return
     */
    List<GroupEntity> findGroupsByGroupName(String groupName);


    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
//    List<GroupEntity> findMyGroupsByUserId(String userId);
    List<GroupEntity> findGroupByUserId(String userId);
    
}
