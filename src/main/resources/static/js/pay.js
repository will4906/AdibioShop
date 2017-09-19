var wechat_ready = false;
function onBridgeReady(){
    wechat_ready = true;
}
if (typeof WeixinJSBridge == "undefined"){
    if( document.addEventListener ){
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    }else if (document.attachEvent){
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
}else{
    onBridgeReady();
}

function writeObj(obj){
    var description = "";
    for(var i in obj){
        var property=obj[i];
        description+=i+" = "+property+"\n";
    }
    alert(description);
}

function requestWechatPayment(data) {
    if (data.result === "ok" && wechat_ready === true){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":data.parm.appId,     //公众号名称，由商户传入
                "timeStamp":data.parm.timeStamp,         //时间戳，自1970年以来的秒数
                "nonceStr":data.parm.nonceStr, //随机串
                "package":data.parm.package_sign_cut,
                "signType":data.parm.signType,         //微信签名方式：
                "paySign":data.parm.paySign //微信签名
            },
            function(res){
                writeObj(res);
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                    window.location.href = context + "/pay_success";
                }else if (res.err_msg == "get_brand_wcpay_request:fail"){
                    window.location.href = context + "/pay_fail";
                }else {
                    //TODO:此处填写取消支付的处理，还没想好
                }
            }
        );
    }
}