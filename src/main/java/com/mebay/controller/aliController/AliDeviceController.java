package com.mebay.controller.aliController;

import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback.Action;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import com.mebay.aliDevice.AliController;
import com.mebay.aliDevice.RequstTopic;
import com.mebay.common.Util;

/**
 * Created by gyh on 2019/1/6.
 */
@AliController
public class AliDeviceController {

    @RequstTopic("/a14HNyLVQE5/+/thing/event/property/post")
    public Action post(MessageToken messageToken) {
        Message m = messageToken.getMessage();
        Util.Log(this.getClass().getSimpleName(), m);
        System.out.println("nmka");
        System.out.println("receive : " + new String(messageToken.getMessage().getPayload()));
        return MessageCallback.Action.CommitSuccess;
    }
}
