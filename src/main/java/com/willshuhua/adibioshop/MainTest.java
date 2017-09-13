package com.willshuhua.adibioshop;

import com.google.gson.Gson;
import com.willshuhua.adibioshop.dto.cart.AddCartItem;
import com.willshuhua.adibioshop.dto.wechat_pay.JsPayParm;
import com.willshuhua.adibioshop.dto.wechat_pay.ReturnPayNotify;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainTest {

    private static Logger logger = Logger.getLogger(MainTest.class);
    public static void main(String[] args) throws Exception {
        JsPayParm jsPayParm = new JsPayParm("1111","2222","3333","4444","5555","6666");
//        List<CartItem> cartItemList = new ArrayList<>();
//        cartItemList.add(new CartItem("12", "12", "32", 1));
//        cartItemList.add(new CartItem("12", "12", "32", 1));
//        cartItemList.add(new CartItem("12", "12", "32", 1));
        Gson gson = new Gson();
        String gsonCartList = gson.toJson(jsPayParm);
        logger.info(gsonCartList);
//        AddCartItem addCartItem = new AddCartItem();
//        String customerId = "60df649c-51c8-4d1d-b02c-47d44d4b7355";
//        PatientInfo patientInfo = new PatientInfo();
//        patientInfo.setCustomer_id(customerId);
//        patientInfo.setPatient_infoid(UUID.randomUUID().toString());
//        patientInfo.setName("张三");
//        patientInfo.setAge(20);
//        patientInfo.setGender("M");
//        patientInfo.setPhone("13829610228");
//        patientInfo.setCountry("CHINA");
//        patientInfo.setProvince("广东省");
//        patientInfo.setCity("深圳市");
//        patientInfo.setDistrict("南山区");
//        patientInfo.setAddress("深圳大学");
//        patientInfo.setHas_diabetic(0);
//        patientInfo.setIs_pregnant(0);
//
//        CartItem cartItem = new CartItem();
//        cartItem.setQuantity(1);
//        cartItem.setCart_id("8b077e8d-2881-47fa-90dc-210f273afeaa");
//        cartItem.setCart_itemid("68e5c479-43cc-4cd4-b67a-2094481e8e6d");
//        String product_id = "bda342a2-27c0-4cb1-bed3-11a786391365";
//        cartItem.setProduct_id(product_id);
//        addCartItem.setCartItem(cartItem);
//        addCartItem.setPatientInfo(patientInfo);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(addCartItem);
//        logger.info(json);
    }
}
