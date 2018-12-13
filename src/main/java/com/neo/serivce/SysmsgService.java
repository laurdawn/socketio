package com.neo.serivce;

import com.alibaba.fastjson.JSONObject;
import com.neo.entity.Group;
import com.neo.entity.Sysmsg;
import com.neo.entity.User;


public interface SysmsgService{

    /**
     * 保存系统通知类信息（申请入群，邀请入群等信息）
     * @param msg
     */
    void saveSysmsg(Sysmsg msg);

    /**
     * @description 查询消息盒子信息
     */
    JSONObject findAddInfo(String userId);


    /**
     * 用户处理系统请求
     * @param entity
     */
    void updateStateSysmsg(Sysmsg entity);


    /**
     * 更新 添加消息数据
     * @param entity
     * @param messageId
     * @return
     */
    void updateAddMessage(User entity,Group group,String messageId);


}
