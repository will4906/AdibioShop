/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.util;

import com.willshuhua.adibioshop.dto.init.InitToken;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by will on 2017/8/19.
 */
public class InitMsgUtil {

    private Logger logger = Logger.getLogger(InitMsgUtil.class);

    public String accessConfig(InitToken tokenReq, String wechatToken){
        String signature = tokenReq.getSignature();
        String timestamp = tokenReq.getTimestamp();
        String nonce = tokenReq.getNonce();
        String echostr = tokenReq.getEchostr();

        if (signature == null || timestamp == null || nonce == null || echostr == null){
            return "error";
        }
        //排序
        String sortString = sort(wechatToken, timestamp, nonce);
        //加密
        String mytoken = InitMsgUtil.SHA1(sortString);
        //校验签名
        if (!mytoken.equals("") && mytoken.equals(signature)) {
            logger.info("The sign passed!");
            logger.info(echostr);
            return echostr; //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            return "error";
        }
    }

    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
