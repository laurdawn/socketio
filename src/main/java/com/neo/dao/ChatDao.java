package com.neo.dao;

import org.springframework.stereotype.Component;

import com.neo.entity.Message;

@Component
public interface ChatDao extends BaseDao<Message> {
}
