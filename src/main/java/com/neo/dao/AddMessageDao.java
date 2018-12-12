package com.neo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neo.entity.AddMessage;

@Component
public interface AddMessageDao extends BaseDao<AddMessage> {

    /**
     * @description 查询消息盒子信息
     */
    List<AddMessage> findAddInfo(String userId);

}
