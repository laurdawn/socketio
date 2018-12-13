package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.Group;

@Component
public interface GroupDao extends BaseDao<Group> {
	
    /**
     * 根据群的名字查询所有的群
     *
     * @return
     */
    List<Group> findGroupsByName(String name);


    /**
     * 获取 我所在的 所有的群
     *
     * @return
     */
//    List<GroupEntity> findMyGroupsByUserId(String userId);
    List<Group> findGroupByOwnerId(String ownerId);
    
}
