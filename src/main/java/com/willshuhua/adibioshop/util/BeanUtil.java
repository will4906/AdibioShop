/*
 * Copyright (c) 2017. willshuhua.
 */

package com.willshuhua.adibioshop.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.SortedMap;
import java.util.TreeMap;

public class BeanUtil {

//    TODO:需要好好学习一下泛型和反射
    /**
     * 将一个类的所有非null属性储存进一个map中，并输出
     * @param o 一个bean
     * @return 一个map
     * @throws NoSuchMethodException 没有方法异常
     * @throws InvocationTargetException 调用方法异常
     * @throws IllegalAccessException 不合法权限异常
     */
    public static SortedMap<String, String> beanToMap(Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        Class<?> oClass = o.getClass();
        Field[] fields = oClass.getDeclaredFields();
        for (Field field : fields){
            String name = field.getName();
            String mName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field.getType().toString();
            if (type.equals("class java.lang.String")){
                Method method = oClass.getMethod("get" + mName);
                String value = (String)method.invoke(o);
                if (value != null){
                    sortedMap.put(name, value);
                }
            }
        }
        return sortedMap;
    }
}
