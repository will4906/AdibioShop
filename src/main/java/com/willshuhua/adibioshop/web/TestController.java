package com.willshuhua.adibioshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(){
        return new ModelAndView("test");
    }
}


/*
* https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=http%3A%2F%2Fshop.adibio.cn%2Fwechat%2Forder_detail%2F&response_type=code&scope=snsapi_base&state=123#wechat_redirect
*/
/**
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb32b1a2100838738&redirect_uri=http%3A%2F%2Fshop.adibio.cn%2Fwechat%2Findex%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
 */