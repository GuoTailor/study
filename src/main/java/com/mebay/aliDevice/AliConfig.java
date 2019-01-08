package com.mebay.aliDevice;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by gyh on 2019/1/6.
 */
@Component
public class AliConfig implements ApplicationContextAware {
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);
        // 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);

        for (String beanName : applicationContext.getBeanNamesForAnnotation(AliController.class)) {
            Object o = applicationContext.getBean(beanName);
            for (Method method : o.getClass().getDeclaredMethods()){
                RequstTopic topic = method.getAnnotation(RequstTopic.class);
                if (topic != null) {
                    MessageCallback messageCallback = messageToken -> {
                        try {
                            return (MessageCallback.Action) method.invoke(o, messageToken);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return null;
                    };
                    client.setMessageListener(topic.value(), messageCallback);
                }
            }
        }

        client.connect(messageToken -> {
            Message m = messageToken.getMessage();
            System.out.println("receive message from " + m);
            return MessageCallback.Action.CommitSuccess;
        });
    }
}
