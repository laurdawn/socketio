package com.neo.socketio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastAckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.neo.entity.AddMessage;
import com.neo.entity.Group;
import com.neo.entity.GroupMember;
import com.neo.entity.Message;
import com.neo.entity.User;
import com.neo.serivce.SysmsgService;
import com.neo.serivce.ChatService;
import com.neo.serivce.GroupService;
import com.neo.serivce.UserService;
import com.neo.utils.DateUtils;
import com.neo.utils.SessionUtil;

@Component
public class MessageEventHandler {

    private final SocketIOServer server;

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SysmsgService addMessageService;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String auth_token = client.getHandshakeData().getSingleUrlParam("auth_token");
        User userEntity = userService.findUserByToken(auth_token);
        String userId = userEntity.getId();
        String userName = userEntity.getUsername();
        client.set("userId", userId);
        client.set("userName", userName);
        SessionUtil.userId_socket_Map.put(userId, client);

        //上线关联所在的群组
        List<Group> entityList = groupService.findMyGroupsByUserId(userId);

        for (Group entity : entityList) {
            logger.info(userName + "自动关联了群 " + entity.getName() + "   " + sdf.format(new Date()));
            client.joinRoom(entity.getId());
        }

        logger.info(userName + " connect...  " + client.getSessionId() + "   " + sdf.format(new Date()));
    }


    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        SessionUtil.userId_socket_Map.remove(client.get("userId"));
        logger.info(client.get("userName") + " disconnect... " + sdf.format(new Date()));
    }

//      server.addEventListener("protobufTest", byte[].class, (client, data, ackRequest) -> {
//        GpsData.gps_data gps_data = GpsData.gps_data.parseFrom(data);
//        System.out.println("after :" + gps_data.toString());
//    });


    //使用protobuf 测试传输的数据
//    @OnEvent(value = "protobufTest")
//    public void onProtobufTest(SocketIOClient client, AckRequest ackRequest, byte[] data) throws InvalidProtocolBufferException, UnsupportedEncodingException {
//        if (ackRequest.isAckRequested()) {
//            ackRequest.sendAckData(data);
//        }
//        GpsData.gps_data gps_data = GpsData.gps_data.parseFrom(data);
//        System.out.println("after :" + gps_data.getTerminalId() + gps_data.getDataTime());
//    }


    //申请加入群组
    @OnEvent(value = "addGroup")
    public void onEventJoin(SocketIOClient client, AckRequest ackRequest, AddMessage msg) {

        String id = msg.getToUid();
        //查询群下面的所有人  如果有当前群包含了 自己则说明是重复加群
        List<GroupMember> groupUsers = groupService.findMembersByGroupId(msg.getGroupId());
        for (GroupMember user : groupUsers) {
            if (user.getUserId().equals(msg.getFromUid())) {
                ackRequest.sendAckData("请勿重复加群");
                logger.info("重复加群了。。。。。。兄弟");
                return;
            }
        }

        ackRequest.sendAckData("");
        addMessageService.saveAddMessage(msg);

        if (!StringUtils.isEmpty(id) && SessionUtil.userId_socket_Map.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtil.userId_socket_Map.get(id);
            socketIOClient.sendEvent("addGroup");
        }
        logger.info(msg.toString());
    }

    //拒绝加入群组
    @OnEvent(value = "refuseAddGroup")
    public void refuseAddGroup(SocketIOClient client, AckRequest ackRequest, JSONObject object) {

        ackRequest.sendAckData("");

        String id = object.getString("toUid");
        addMessageService.updateAddMessage(object.getString("messageBoxId"));
//
        if (!StringUtils.isEmpty(id) && SessionUtil.userId_socket_Map.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtil.userId_socket_Map.get(id);
            socketIOClient.sendEvent("refuseAddGroup");
        }
        logger.info(object.toString());
    }

    //同意加入群组
    @OnEvent(value = "agreeAddGroup")
    public void agreeAddGroup(SocketIOClient client, AckRequest ackRequest, JSONObject object) {


        String id = object.getString("toUid");
        String groupId = object.getString("groupId");
        
        User user = userService.findUserById(id);
        Group group = groupService.findGroupById(groupId);

        ackRequest.sendAckData("");

        addMessageService.updateAddMessage(user, group, object.getString("messageBoxId"));
//
        if (!StringUtils.isEmpty(id) && SessionUtil.userId_socket_Map.containsKey(id)) {
            SocketIOClient socketIOClient = SessionUtil.userId_socket_Map.get(id);
            socketIOClient.sendEvent("agreeAddGroup");
        }
        logger.info(object.toString());
    }


    /**
     * 退群
     * @param client
     * @param ackRequest
     * @param msg
     */
    @OnEvent(value = "leave")
    public void onEventLeave(SocketIOClient client, AckRequest ackRequest, Message msg) {

    }

    /**
     * 发送信息
     * @param client
     * @param ackRequest
     * @param msg
     */
    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "chat")
    public void onEvent(SocketIOClient client, AckRequest ackRequest, Message msg) {

        boolean isChat = msg.getChat_type().toString().equals("chat");

        //isAckRequested 如果ack request来自client，则返回true
        if (ackRequest.isAckRequested()) {

            if (msg.getFrom_user().equals(msg.getTo_user())) {
                ackRequest.sendAckData("请不要给自己发消息");
                return;
            }

            //将数据保存到服务器
            chatService.saveMessageData(msg);

            String toName = "";
            if (isChat) {
                toName = msg.getTo_user();
            } else {
                toName = "群： " + msg.getTo_user();
            }
            logger.info("给 " + toName + " 发送的数据 服务器已经收到， 日期： " + sdf.format(new Date()));
            //发送ack回调数据到客户端
            ackRequest.sendAckData("");
        }


        String to_user_id = msg.getTo_user_id(); //如果是 群聊，则对应群的id
        String to_user_name = msg.getTo_user();

        if (isChat) { //单聊
            // 如果对方在线 则找到对应的client 给其发送消息
            if (SessionUtil.userId_socket_Map.containsKey(to_user_id)) {
                SessionUtil.userId_socket_Map.get(to_user_id).sendEvent("chat", new AckCallback<String>(String.class) {
                    //对方客户端接收到消息 返回给服务器端
                    @Override
                    public void onSuccess(String result) {
                    	//TODO 发送成功需要做的事。。。
                        logger.info(to_user_name + "已收到消息 ， ack 回复 ： " + result + "    日期： " + sdf.format(new Date()));
                    }

                    //发送消息超时
                    @Override
                    public void onTimeout() {
                    	//TODO 发送超时的时候处理
                        System.out.println(to_user_name + "---------> send message time out " + sdf.format(new Date()));
                    }
                }, msg);
            } else { 
            	//TODO 离线消息处理
                logger.info(to_user_name + "---------》需要转 apns 消息推送 " + sdf.format(new Date()));
            }
        } else {
        	//群聊
            logger.info("========================发送群消息==================================");

            //server.getBroadcastOperations().sendEvent("groupChat",msg);系统广播
            //房间（群内）广播
            server.getRoomOperations(to_user_id).sendEvent("chat", msg, new BroadcastAckCallback<String>(String.class) {
                @Override
                protected void onClientTimeout(SocketIOClient client) {
                    logger.info("群消息: " + client.get("userName") + " 发送超时了");
                }

                @Override
                protected void onClientSuccess(SocketIOClient client, String result) {
                    logger.info("群消息: " + client.get("userName") + " 已接收到" + DateUtils.getDataTimeYMDHMSS());
                }
            });
        }

    }


//    public void sendMessageToAllClient(String userName) {
//        Collection<SocketIOClient> clients = server.getAllClients();
//        for (SocketIOClient client : clients) {
//
//        }
//    }
//
}
