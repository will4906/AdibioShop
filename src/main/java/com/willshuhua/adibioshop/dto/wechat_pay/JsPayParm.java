package com.willshuhua.adibioshop.dto.wechat_pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsPayParm {

    //    特别注意，此处大小写绝对不能修改！！！！！！！！！
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String package_sign_cut;        //由于java中package是关键字，无法用来定义变量，而微信使用package作为参数名，为便于处理这里使用_sign_cut标识供生成sign的函数切割。
    private String signType;
    private String paySign;

}
