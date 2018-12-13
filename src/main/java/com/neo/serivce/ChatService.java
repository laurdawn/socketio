package com.neo.serivce;

import com.neo.entity.Message;


public interface ChatService{

    void saveMessageData(Message entity);

    void sendApnData();
}
