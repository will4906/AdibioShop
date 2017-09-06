package com.willshuhua.adibioshop;

import com.willshuhua.adibioshop.dto.wechat_pay.ReturnPayNotify;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String testString = "<xml><ToUserName><![CDATA[gh_60eb9922f62e]]></ToUserName>\n" +
                "<FromUserName><![CDATA[owNVIwdLGp07zeIjYZSlZTFDPak8]]></FromUserName>\n" +
                "<CreateTime>1504716393</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[VIEW]]></Event>\n" +
                "<EventKey><![CDATA[https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb32b1a2100838738&redirect_uri=http%3A%2F%2Fngrok.willshuhua.me%2Fproduct_list%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect]]></EventKey>\n" +
                "<MenuId>558380672</MenuId>\n" +
                "</xml>";

        Serializer serializer = new Persister();
        Map map = serializer.read(Map.class, testString);
        System.out.println(map);
//        ReturnPayNotify returnPayNotify = new ReturnPayNotify("hello", "world");
//
//        StringWriter resultWriter = new StringWriter();
//        String result = "";
//        serializer.write(returnPayNotify, resultWriter);
//        result = resultWriter.toString();
//        System.out.println(result);
    }
}
