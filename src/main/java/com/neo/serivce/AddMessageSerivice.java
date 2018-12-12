package com.neo.serivce;

import com.alibaba.fastjson.JSONObject;
import com.neo.entity.AddMessage;
import com.neo.entity.UserEntity;


public interface AddMessageSerivice{

    /**
     * 添加好友、群组信息请求
     * @param msg
     */
    void saveAddMessage(AddMessage msg);

    /**
     * @description 查询消息盒子信息
     */
    JSONObject findAddInfo(String userId);


    /**
     * 拒绝添加群组，或者，好友
     * @param messageBoxId
     */
    void updateAddMessage(String messageBoxId);


    /**
     * 更新 添加消息数据
     * @param entity
     * @param messageId
     * @return
     */
    void updateAddMessage(UserEntity entity,String groupId,String messageId);

}
