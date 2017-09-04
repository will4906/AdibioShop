package com.willshuhua.adibioshop.dto.wechat_pay;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信支付发送统一下单请求后返回的参数。
 */
@Data
@Root(name = "xml")
public class UnifiedOrderBack {

    @Element(name = "return_code")
    private String return_code;             //返回状态码:SUCCESS/FAIL, 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    @Element(name = "return_msg")
    private String return_msg;
    @Element(name = "appid")
    private String appid;
    @Element(name = "mch_id")
    private String mch_id;
    @Element(name = "nonce_str")
    private String nonce_str;
    @Element(name = "openid")
    private String openid;
    @Element(name = "sign")
    private String sign;
    @Element(name = "result_code")
    private String result_code;
    @Element(name = "prepay_id")
    private String prepay_id;
    @Element(name = "trade_type")
    private String trade_type;

}
