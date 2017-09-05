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
    @Element(name = "appid", required = false)
    private String appid;
    @Element(name = "mch_id", required = false)
    private String mch_id;
    @Element(name = "nonce_str", required = false)
    private String nonce_str;
    @Element(name = "openid", required = false)
    private String openid;
    @Element(name = "sign", required = false)
    private String sign;
    @Element(name = "result_code", required = false)
    private String result_code;
    @Element(name = "prepay_id", required = false)
    private String prepay_id;
    @Element(name = "trade_type", required = false)
    private String trade_type;

}
