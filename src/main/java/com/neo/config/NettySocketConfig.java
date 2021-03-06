package com.neo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.neo.entity.User;
import com.neo.serivce.UserService;
import com.neo.utils.SessionUtil;

import io.netty.util.internal.StringUtil;

@Configuration
public class NettySocketConfig {

	@Value("${im.server.host}")
    private String host;
    @Value("${im.server.port}")
    private Integer port;

    @Autowired
    UserService userSerivice;

    @Bean
    public SocketIOServer socketIOServer() {

    	com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setPingInterval(5000);
        config.setPingTimeout(3000);
        config.setWorkerThreads(100);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);

        //设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
        config.setMaxFramePayloadLength(1024 * 1024);
        //设置http交互最大内容长度
        config.setMaxHttpContentLength(1024 * 1024);

//        // Instantiate Redisson configuration
//        Config redissonConfig = new Config();
//        redissonConfig.useSingleServer().setAddress("127.0.0.1:6379");
//
//// Instantiate Redisson connection
//        RedissonClient redisson = Redisson.create(redissonConfig);
//
//// Instantiate RedissonClientStoreFactory
//        RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);
//        config.setStoreFactory(redisStoreFactory);

        //身份认证
        config.setAuthorizationListener(hd -> {

            System.out.println(hd.getUrlParams());

            String auth_token = hd.getSingleUrlParam("auth_token");

            if (StringUtil.isNullOrEmpty(auth_token)) {
                return false;
            }

            User userEntity = userSerivice.findUserByToken(auth_token);
            //同一个账号登录多次登录 关闭之前的连接
            if (userEntity != null && SessionUtil.userId_socket_Map.containsKey(userEntity.getId())) {
                SocketIOClient socketIOClient = SessionUtil.userId_socket_Map.get(userEntity.getId());
                socketIOClient.sendEvent("otherLogin");
                return false;
            }

            // 移动设备不能同时登录 (android ios) 待处理

            //是否拦截 socket.io 连接
            return userEntity == null ? false : true;
        });

        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }
	
}
