package com.neo.serivce;

import com.neo.entity.MessageEntity;


public interface ChatSerivice{

    void saveMessageData(MessageEntity entity);

    void sendApnData();
}
