package com.mebay.config;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import com.mebay.common.Util;

/**
 * Created by gyh on 2018/12/15.
 */
public class Aliyun {
    // 阿里云accessKey
    public static final String accessKey = "LTAIN4Gg0VZbl7il";
    // 阿里云accessSecret
    public static final String accessSecret = "uH0t5b5KDTq1pptHlwLud0M1AkYoQz";
    // regionId
    public static final String regionId = "cn-shanghai";
    // 阿里云uid
    public static final String uid = "1303019373233848";
    // endPoint:  https://${uid}.iot-as-http2.${region}.aliyuncs.com
    public static final String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";

    public void config() {
        // 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);
        // 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);
        // 数据接收
        MessageCallback messageCallback = messageToken -> {
            Message m = messageToken.getMessage();
            Util.Log(this.getClass().getSimpleName(), m);
            System.out.println("receive : " + new String(messageToken.getMessage().getPayload()));
            return MessageCallback.Action.CommitSuccess;
        };
        client.setMessageListener(messageCallback);
        client.connect(messageToken -> {
            Message m = messageToken.getMessage();
            System.out.println("receive message from " + m);
            return MessageCallback.Action.CommitSuccess;
        });
    }

}
