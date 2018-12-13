package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.Sysmsg;

@Component
public interface SysmsgDao extends BaseDao<Sysmsg> {

    /**
     * @description 查询消息盒子信息
     */
//    List<AddMessage> findAddInfo(String userId);
	
	/**
	 * 更新消息是否处理和是否可读的状态
	 * @param entity
	 */
	void updateStateEntity(Sysmsg entity);
	
	List<Sysmsg> selectSysmsgByReceiverId(String receiverId);

}
