package com.willshuhua.adibioshop;

import com.willshuhua.adibioshop.dto.wechat_pay.ReturnPayNotify;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) throws Exception {
        List<Integer> cartItemList = new ArrayList<>();
        cartItemList.add(1);
        cartItemList.add(2);
        cartItemList.add(3);
        cartItemList.add(4);
        cartItemList.add(5);

        int i = 0;
        for (i = 0; i < cartItemList.size(); i ++){
            int cartItem = cartItemList.get(i);
            if (cartItem == 8){
                break;
            }
        }
        if (i == cartItemList.size()){
            System.out.println("iiiii");
        }
    }
}
