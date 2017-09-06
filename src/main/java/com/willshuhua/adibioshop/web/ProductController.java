package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.access.Authorization;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.Product;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WechatProperties wechatProperties;

    private Logger logger = Logger.getLogger(ProductController.class);

    @RequestMapping(value = "/product_list", method = RequestMethod.GET)
    public ModelAndView productShow(HttpServletRequest request, HttpSession httpSession) throws IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        logger.info("code===" + code);
        logger.info(httpSession);
        ModelAndView modelAndView  = new ModelAndView("product_list");

        List<Product> productList = productService.queryAllProduct();
        modelAndView.addObject("productList", productList);

        if (code == null || code.equals("")){
            return modelAndView;
        }

        analyseCustomer(code, httpSession, false);
        return modelAndView;
    }

    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    public ModelAndView productDetail(HttpServletRequest request){
        String productId = request.getParameter("product_id");
        ModelAndView modelAndView = new ModelAndView("product_detail");
        modelAndView.addObject("productId", productId);
        return modelAndView;
    }

    @RequestMapping(value = "/pay_detail", method = RequestMethod.GET)
    public ModelAndView payDetail(HttpServletRequest request){
        String productId = request.getParameter("product_id");
        Product product = productService.queryProductByProductId(productId);
        ModelAndView modelAndView = new ModelAndView("pay_detail");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    private void analyseCustomer(String code, HttpSession httpSession, boolean isAsnyc) throws IOException {
        Retrofit retrofit = retrofitManager.getGsonRetrofit();
        WechatRequest wechatRequest = retrofit.create(WechatRequest.class);
        Call<Authorization> authorizationCall = wechatRequest.requestAuthorization(wechatProperties.getAppid(), wechatProperties.getAppsecret(), code);
        if (isAsnyc){
            authorizationCall.enqueue(new Callback<Authorization>() {
                @Override
                public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                    if (response.isSuccessful()){
                        Authorization authorization = response.body();
                        if (authorization == null){
                            return;
                        }
                        Customer customer = bindCustomer(authorization);
                        if (customer != null){
                            httpSession.setAttribute("customer", customer);
                        }
                        logger.info(customer);
                    }else {
                        call.clone().enqueue(this);
                    }
                }
                @Override
                public void onFailure(Call<Authorization> call, Throwable t) {
                    call.clone().enqueue(this);
                }
            });
        }else {
            Response<Authorization> response = authorizationCall.execute();
            Authorization authorization = response.body();
            if (authorization == null){
                return;
            }
            Customer customer = bindCustomer(authorization);
            if (customer != null){
                httpSession.setAttribute("customer", customer);
            }
            logger.info(customer);
        }
        logger.info(httpSession.toString());
    }

    private Customer bindCustomer(Authorization authorization){
        String openId = authorization.getOpenid();
        logger.info("openid===" + openId);
        if (openId == null || openId.equals("")){
            return null;
        }
        Customer customer = customerService.queryCustomerByOpenId(openId);
        if (customer == null){
            Customer createCus = new Customer();
            String customerId = UUID.randomUUID().toString();
            createCus.setCustomer_id(customerId);
            createCus.setWechat_id(openId);
            createCus.setRegister_time(new Date());
            customerService.createCustomerAccount(createCus);
            customer = createCus;
        }
        return customer;
    }
}
