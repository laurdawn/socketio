package com.neo.serivce.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.neo.dao.ChatDao;
import com.neo.entity.Message;
import com.neo.serivce.ChatService;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@Component
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDao chatDao;
	
    @Override
    public void saveMessageData(Message entity) {
        long time = new Date().getTime();
        entity.setTimestamp(time);
        chatDao.saveEntity(entity);
    }

    @Override
    public void sendApnData() {
        final ApnsClient apnsClient;

        try {
            apnsClient = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                    .setSigningKey(ApnsSigningKey.loadFromPkcs8File(new ClassPathResource("AuthKey.p8").getFile(),
                            "5HBP8N48W6", "SXZZL6BZ83"))
                    .build();
            final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
            payloadBuilder.setAlertBody("Example!");
            payloadBuilder.setBadgeNumber(1);
            payloadBuilder.setAlertBody("sdfsdfsfsddf0");
            final String payload = payloadBuilder.buildWithDefaultMaximumLength();
            final String token = "431b9699945a0fa11e692f9a281e3be5deec70ea61c8530aef7efbcc098b7e71";

            SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "com.example.myApp", payload);
            final Future sendNotificationFuture = apnsClient.sendNotification(pushNotification);

            sendNotificationFuture.addListener(new GenericFutureListener<Future<PushNotificationResponse>>() {

                @Override
                public void operationComplete(final Future<PushNotificationResponse> future) throws Exception {
                    // This will get called when the sever has replied and returns immediately
                    final PushNotificationResponse response = future.getNow();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
