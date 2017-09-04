package com.willshuhua.adibioshop.dto.wechat_pay;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信支付统一下单需要发送的参数
 */
@Data
@Root(name = "xml")
public class UnifiedOrder {

    //    以下参数带 * 为必填参数，不带为非必填. 文档链接：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
//    特别注意，此处大小写绝对不能修改！！！！！！！！！
    @Element(name = "appid")
    private String appid;                           //    * 公众账号ID
    @Element(name = "mch_id")
    private String mch_id;                          //    * 商户号
    @Element(name = "nonce_str")
    private String nonce_str;                      //     * 随机字符串
    @Element(name = "sign")
    private String sign;                            //     * 签名
    @Element(name = "body")
    private String body;                            //     * 商品描述
    @Element(name = "out_trade_no")
    private String out_trade_no;                   //     * 商户订单号
    @Element(name = "total_fee")
    private String total_fee;                       //     * 标价金额，单位：分
    @Element(name = "spbill_create_ip")
    private String spbill_create_ip;               //     * 终端IP
    @Element(name = "notify_url")
    private String notify_url;                      //     * 通知地址
    @Element(name = "trade_type")
    private String trade_type;                      //     * 交易类型
    @Element(name = "openid")
    private String openid;                          //      * 用户标识
    @Element(name = "device_info")
    private String device_info;                    //    设备号
    @Element(name = "sign_type")
    private String sign_type;                      //     签名类型
    @Element(name = "detail")
    private String detail;                          //     商品详情
    @Element(name = "attach")
    private String attach;                          //     附加数据
    @Element(name = "fee_type")
    private String fee_type;                        //     标价币种
    @Element(name = "time_start")
    private String time_start;                      //     交易起始时间
    @Element(name = "time_expire")
    private String time_expire;                     //     交易结束时间
    @Element(name = "goods_tag")
    private String goods_tag;                       //     订单优惠标记
    @Element(name = "product_id")
    private String product_id;                      //     商品ID
    @Element(name = "limit_pay")
    private String limit_pay;                       //     指定支付方式
    @Element(name = "scene_info")
    private String scene_info;                      //      场景信息
}
