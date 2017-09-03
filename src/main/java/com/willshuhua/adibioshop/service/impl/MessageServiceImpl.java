/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.service.impl;

import com.thoughtworks.xstream.XStream;
import com.willshuhua.adibioshop.dto.message.TextMessage;
import com.willshuhua.adibioshop.define.message.EventType;
import com.willshuhua.adibioshop.define.message.MsgType;
import com.willshuhua.adibioshop.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public String answerMessage(Map weMap) {
        String msgType = (String) weMap.get("MsgType");
        String msgFrom = (String) weMap.get("FromUserName");
        String msgTo = (String) weMap.get("ToUserName");

        switch (msgType){
            case MsgType.TEXT:
                return createTextMessage(msgFrom, msgTo, "我们已经接收到了你的消息，待处理后会尽快答复！");
            case MsgType.EVENT:{
                String eventType = (String)weMap.get("Event");
                switch (eventType){
                    case EventType.SUBSCRIBE:
                        return createTextMessage(msgFrom, msgTo, "Hello,欢迎关注糖老师知道！");
                    default:
                        break;
                }
            }
            default:
                break;
        }
        return "SUCCESS";
    }

    private String createTextMessage(String to, String from, String content){
        XStream xStream = new XStream();
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        textMessage.setFromUserName(from);
        textMessage.setToUserName(to);
        textMessage.setMsgType(MsgType.TEXT);
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }
}
