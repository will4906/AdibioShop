package com.willshuhua.adibioshop.web;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.willshuhua.adibioshop.dto.init.InitToken;
import com.willshuhua.adibioshop.dto.message.TextMessage;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.service.MessageService;
import com.willshuhua.adibioshop.util.InitMsgUtil;
import com.willshuhua.adibioshop.util.XmlTool;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MessageController {

    private Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String initMessage(@ModelAttribute("InitToken") InitToken tokenReq) {
        InitMsgUtil initMsgUtil = new InitMsgUtil();
        return initMsgUtil.accessConfig(tokenReq, wechatProperties.getToken());
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST, consumes = { "text/xml" }, produces = {"application/xml" })
    public String answerMessage(@RequestBody String wechatData) throws Exception {
        logger.info(wechatData);

        Map map = XmlTool.readStringXmlOut(wechatData);
        String returnMsg = messageService.answerMessage(map);
        logger.info(returnMsg);
        return returnMsg;
    }
}
