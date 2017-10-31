package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.common.TokenInstance;
import com.willshuhua.adibioshop.dto.access.WxJsConfig;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.share.CashbackInfo;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.CustomerWechat;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.util.Encryption;
import com.willshuhua.adibioshop.util.InitMsgUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    TokenInstance tokenInstance = TokenInstance.getInstance();

    private Logger logger = Logger.getLogger(CustomerController.class);

    @RequestMapping(value = "/customer_info", method = RequestMethod.GET)
    public ModelAndView customerInfo(HttpServletRequest request, HttpSession httpSession) throws IOException {
        Customer customer = (Customer)httpSession.getAttribute("customer");
        CustomerWechat customerWechat = customerService.getCustomerWechat(customer.getCustomer_id());
        ModelAndView modelAndView = new ModelAndView("/info/customer_info");
        modelAndView.addObject("wechat_info", customerWechat);
        return modelAndView;
    }

    @RequestMapping(value = "/share_info", method = RequestMethod.GET)
    public ModelAndView shareInfo(HttpServletRequest request, HttpSession httpSession) throws Exception {
//        添加微信分享配置
        Customer customer = (Customer)httpSession.getAttribute("customer");
        String weBaseUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        weBaseUrl += wechatProperties.getAppid();
        weBaseUrl += "&redirect_uri=";
        String myUrl = request.getScheme() + "%3A%2F%2F" + request.getServerName().replace("/", "%2F") + request.getContextPath().replace("/", "%2F") + "%2Findex";
        weBaseUrl += myUrl;
        weBaseUrl += "&response_type=code&scope=snsapi_userinfo&state=share_";
        String fromId = request.getParameter("from_id");
        if (fromId == null){
            throw new Exception("Can't find from_id");
        }
        weBaseUrl += fromId;
        String weUrl = weBaseUrl + "#wechat_redirect";
        logger.info(weUrl);
        if (customer == null){
            return new ModelAndView("redirect:" + weUrl);
        }else {
            if (!customer.getCustomer_id().equals(request.getParameter("from_id"))){
                return new ModelAndView("redirect:" + weUrl);
            }
        }
        WxJsConfig wxJsConfig = new WxJsConfig();
        wxJsConfig.setAppId(wechatProperties.getAppid());
        wxJsConfig.setNonceStr(Encryption.md5(UUID.randomUUID().toString()));
        wxJsConfig.setTimestamp(String.valueOf(new Date().getTime() / 1000));
        String jsTicket = tokenInstance.getJsapiTicket(redisTemplate, wechatProperties.getAppid(), wechatProperties.getAppsecret());
        String strUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/share_info?from_id=" + customer.getCustomer_id();
        logger.info(strUrl);
        String sortSignature = "jsapi_ticket=" + jsTicket + "&noncestr=" + wxJsConfig.getNonceStr() + "&timestamp=" + wxJsConfig.getTimestamp() + "&url=" + strUrl;
        wxJsConfig.setSignature(InitMsgUtil.SHA1(sortSignature));
        logger.info(wxJsConfig);
        ModelAndView modelAndView = new ModelAndView("/info/share_info");
        modelAndView.addObject("wxJsConfig", wxJsConfig);
//        添加用户返现用户信息
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("strUrl", strUrl);
        return modelAndView;
    }

    @RequestMapping(value = "/cashback_info", method = RequestMethod.GET)
    @ResponseBody
    public Object cashbackInfo(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        Map<String, String> cashbackMap = new HashMap<>();
        cashbackMap.put("zhifubao_account", customer.getZhifubao_account());
        cashbackMap.put("bank_card_number", customer.getBank_card_number());
        return new Result(Result.OK, cashbackMap);
    }

    @RequestMapping(value = "/update_cashback_info", method = RequestMethod.POST)
    @ResponseBody
    public Object updateCashbackInfo(HttpSession httpSession, @ModelAttribute("cashbackInfo") CashbackInfo cashbackInfo){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        customer.setZhifubao_account(cashbackInfo.getZhifubao_account());
        customer.setBank_card_number(cashbackInfo.getBank_card_number());
        customerService.updateCashbackInfo(customer);
        logger.info(customer);
        httpSession.setAttribute("customer", customer);
        return new Result(Result.OK, customer);
    }
}
