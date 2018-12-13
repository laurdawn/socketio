package com.neo.serivce.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.neo.dao.SysmsgDao;
import com.neo.dao.GroupDao;
import com.neo.dao.UserDao;
import com.neo.entity.Sysmsg;
import com.neo.entity.AddMessage;
import com.neo.entity.Group;
import com.neo.entity.GroupMember;
import com.neo.entity.User;
import com.neo.enums.AddMessageType;
import com.neo.exception.RepeatException;
import com.neo.serivce.SysmsgService;
import com.neo.serivce.GroupService;
import com.neo.utils.DateUtils;

@Component
public class SysmsgServiceImpl implements SysmsgService{

    @Autowired
    private SysmsgDao sysmsgDao;
    
    /**
     *保存系统通知类信息（申请入群，邀请入群等信息）
     * @param msg
     */
    @Override
    public void saveSysmsg(Sysmsg msg) {
    	sysmsgDao.saveEntity(msg);
    }

    /**
     * 查询收到的用户未处理的系统信息
     */
    @Override
    public JSONObject findSysmsg(String userId) {

//        JSONObject obj = new JSONObject();
//
//        List<AddMessage> list = addMessageDao.findAddInfo(userId);
//
//        List<Sysmsg> infos = new ArrayList<>();
//        for (AddMessage message : list) {
//            Sysmsg info = new Sysmsg();
//            if (message.getType().equals("group")) {
//                Group entity = (Group) groupDao.findEntityById(message.getGroupId());
//                info.setContent("申请加入 '" + entity.getName() + "' 群聊!");
//            } else {
//                info.setContent("申请添加你为好友");
//            }
//
//            info.setId(message.getId());
//            info.setType(message.getType());
//            info.setFrom(message.getFromUid());
//            info.setUid(message.getToUid());
//            info.setRead(message.getMsgResult().toString());
//            User userEntity = userDao.findEntityById(message.getFromUid());
//            userEntity.setPassword("");
//            userEntity.setAuth_token("");
//            info.setUser(userEntity);
//            info.setFrom_group(message.getGroupId());
//            info.setTime(message.getTime());
//            info.setRemark(message.getRemark());
//            infos.add(info);
//        }
//
//        obj.put("list", infos);
//        obj.put("pages", 1);
//        return obj;
    }


    /**
     * 更新 添加消息数据
     *
     * @param entity
     * @param messageId
     * @return
     */
//    @Override
//    @Transactional
//    public void updateAddMessage(User entity, Group group, String messageId) {
//
//        String userId = entity.getId(); //申请加群的人
//        //查询群下面的所有人  如果有当前群包含了 自己则说明是重复加群
//        List<GroupMember> groupUsers = groupService.findMembersByGroupId(group.getId());
//        for (GroupMember user : groupUsers) {
//            if (user.getUserId().equals(userId)) {
//                throw new RepeatException(-1, "不能重复加群");
//            }
//        }
//
//        AddMessage addMessage = (AddMessage) addMessageDao.findEntityById(messageId);
//        addMessage.setMsgResult(AddMessageType.Agree);
//        addMessageDao.updateEntityById(messageId, addMessage);
//
//        groupService.joinGroup(entity, group);
//    }

    /**
     * 用户处理系统请求（接收，拒绝，已读等）
     *
     * @param messageBoxId
     */
    @Override
    public void updateStateSysmsg(Sysmsg entity) {
        sysmsgDao.updateStateEntity(entity);
    }


}
