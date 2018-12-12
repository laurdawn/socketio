package com.neo.dao;

import org.springframework.stereotype.Component;

import com.neo.entity.MessageEntity;

@Component
public interface ChatDao extends BaseDao<MessageEntity> {
}
