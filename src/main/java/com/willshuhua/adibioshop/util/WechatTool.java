package com.willshuhua.adibioshop.util;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class WechatTool {

    public static final String SIGN_CUT = "_sign_cut";

    public static String generateMD5PaySign(SortedMap<String, String> sortedMap, String apiKey){
        StringBuilder sb = new StringBuilder();
        Set es = sortedMap.entrySet();//所有参与传参的参数按照accsii排序（升序）
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            k = k.replace(SIGN_CUT, "");
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(apiKey);
        System.out.println(sb.toString());
        return Encryption.md5(sb.toString()).toUpperCase();
    }
}
