package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.common.TokenInstance;
import com.willshuhua.adibioshop.define.order.OrderStatus;
import com.willshuhua.adibioshop.dto.access.Authorization;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.template.TemplateBack;
import com.willshuhua.adibioshop.dto.template.WechatTemplate;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.analysis.Analysis;
import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderPatientInfo;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import com.willshuhua.adibioshop.service.AnalysisService;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;
    @Autowired
    WechatProperties wechatProperties;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();
    private TokenInstance tokenInstance = TokenInstance.getInstance();

    private Logger logger = Logger.getLogger(AnalysisController.class);

//    TODO: 微信模板推送无法显示部分信息
    @RequestMapping(value = "/notify_analysis")
    @ResponseBody
    public Object notifyAnalysis(@RequestParam("analysis_id")String analysisId, HttpServletRequest request) throws IOException {
        Map<String,Object> analysisInfo = analysisService.getAnalysisInfo(analysisId);
        if (analysisInfo == null){
            return new Result(Result.ERR, "Can't find the analysis!");
        }
        Analysis analysis = (Analysis) analysisInfo.get("analysis");
        if (analysis.getAnalysis_date() == null || analysis.getCollection_date() == null){
            return new Result(Result.ERR, "Please check the input");
        }
        Customer customer = (Customer) analysisInfo.get("customer");
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(customer.getOpenid());
        wechatTemplate.setTemplate_id("ipyNGDNnzYpIadqN5Y_-nuVav7TlOMyqIlfdXXxwUZE");

        String strUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatProperties.getAppid() + "&redirect_uri=APP_URL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        strUrl = strUrl.replace("APP_URL", request.getScheme() + "%3A%2F%2F" + request.getServerName() + request.getContextPath() + "%2F" + analysis.getOrder_infoid() + "%2F" + "analysis_detail");

//        String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/analysis_detail";
        wechatTemplate.setUrl(strUrl);
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> first = new HashMap<>();
        first.put("value", "您好，检测结果已发布。");
        first.put("color", "#173177");
        data.put("first", first);

        Map<String, String> projectName = new HashMap<>();
        projectName.put("value", (String) analysisInfo.get("product_name"));
        projectName.put("color", "#173177");
        data.put("projectName", projectName);

        Map<String, String> time = new HashMap<>();
        time.put("value", analysis.getAnalysis_date().toString());
        time.put("color", "#173177");
        data.put("time", time);

        Map<String, String> address = new  HashMap<>();
        address.put("value", wechatProperties.getMerchant());
        address.put("color", "#173177");
        data.put("address", address);

        Map<String, String> result = new HashMap<>();
        result.put("value", analysis.getResult());
        result.put("color", "#173177");
        data.put("result", result);

        Map<String, String> remark = new HashMap<>();
        remark.put("value", "点击【详情】查看完整检测结果");
        remark.put("color", "#173177");
        data.put("remark", remark);

        wechatTemplate.setData(data);

        String access_token = tokenInstance.getAccessToken(redisTemplate, wechatProperties.getAppid(), wechatProperties.getAppsecret());

        Retrofit retrofit = retrofitManager.getGsonRetrofit();
        WechatRequest wechatRequest = retrofit.create(WechatRequest.class);

        Call<TemplateBack> backCall = wechatRequest.sendTemplate(access_token, wechatTemplate);
        backCall.enqueue(new Callback<TemplateBack>() {
            @Override
            public void onResponse(Call<TemplateBack> call, Response<TemplateBack> response) {
                logger.info(response);
            }

            @Override
            public void onFailure(Call<TemplateBack> call, Throwable t) {
                logger.error("发送失败");
                logger.warn(t);
            }
        });

        Order order = analysisService.selectOrderByAnalysisId(analysisId);
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder_eventid(UUID.randomUUID().toString());
        orderEvent.setEvent_title(OrderStatus.FINISHED);
        orderEvent.setEvent_time(new Date());
        orderEvent.setOrder_id(order.getOrder_id());
        orderEvent.setEvent_executor("admin");
        orderService.changeOrderStatus(orderEvent);
        return new Result();
    }

    @RequestMapping(value = "/{orderInfoId}/analysis_detail", method = RequestMethod.GET)
    public ModelAndView analysisDetail(@PathVariable("orderInfoId")String orderInfoId, HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        ModelAndView modelAndView = new ModelAndView("/info/analysis");
        Retrofit retrofit = retrofitManager.getGsonRetrofit();
        WechatRequest wechatRequest = retrofit.create(WechatRequest.class);
        Call<Authorization> authorizationCall = wechatRequest.requestAuthorization(wechatProperties.getAppid(), wechatProperties.getAppsecret(), code);
        Response<Authorization> response = authorizationCall.execute();
        Authorization authorization = response.body();
        if (authorization != null){
            String openid = authorization.getOpenid();
            Customer customer = customerService.queryCustomerByOpenId(openid);
            if (customer == null){
                throw new Exception("Please check the customer id");
            }
            Customer testCustomer = analysisService.selectCustomerByOrderInfoId(orderInfoId);
            if (testCustomer == null){
                throw new Exception("Please check the order_infoid");
            }
            if (customer.getCustomer_id().equals(testCustomer.getCustomer_id())){
                logger.info(orderInfoId);
                Map<String,Object> analysisInfo = analysisService.getAnalysisInfoByOrderInfo(orderInfoId);
                ((OrderPatientInfo)analysisInfo.get("patient_info")).setCountry("中国");
                logger.info(analysisInfo);
                modelAndView.addObject("analysisInfo", analysisInfo);
            }
        }
        return modelAndView;
    }
}
