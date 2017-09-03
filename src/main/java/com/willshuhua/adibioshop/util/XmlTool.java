/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlTool {

    public static Map readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<>();
        Document document;
        try {
            // 将字符串转为XML
            document = DocumentHelper.parseText(xml);
            // 获取根节点
            Element root = document.getRootElement();

            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                map.put(element.getName(), element.getStringValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String mapToXml(Map<String, String> map){
        StringBuilder strXml = new StringBuilder("<xml>");
        for(Map.Entry<String, String> entry : map.entrySet())
        {
            String strLine = "<" + entry.getKey().toLowerCase() + "><![CDATA[" + entry.getValue() + "]]></" + entry.getKey().toLowerCase() + ">";
            strXml.append(strLine);
        }
        strXml.append("</xml>");
        return strXml.toString();
    }
}
