package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.access.Authorization;
import com.willshuhua.adibioshop.dto.info.SnsapiUserinfo;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.CustomerWechat;
import com.willshuhua.adibioshop.entity.Product;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import com.willshuhua.adibioshop.service.CartService;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
import java.util.concurrent.TimeUnit;

@Controller
public class ProductController {

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Logger logger = Logger.getLogger(ProductController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpSession httpSession) throws IOException {
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
//        logger.info("code===" + code);
//        logger.info(httpSession);
//        ModelAndView modelAndView  = new ModelAndView("index");
//        if (code == null || code.equals("")){
//            return modelAndView;
//        }
//        analyseCustomer(code, httpSession, false);


//        调试
        Customer customer = new Customer();
//        customer.setCustomer_id("de14cd03-2f57-4efd-a14b-8e2cebb7a890");
//        customer.setOpenid("owNVIwQFSY-BxMyKi10bqi_6761w");
        customer.setCustomer_id("60df649c-51c8-4d1d-b02c-47d44d4b7355");
        customer.setOpenid("owNVIwdLGp07zeIjYZSlZTFDPak8");
        httpSession.setAttribute("customer", customer);

        ModelAndView modelAndView  = new ModelAndView("index");
        return modelAndView;

    }
    @RequestMapping(value = "/product_list", method = RequestMethod.GET)
    public ModelAndView productShow(HttpServletRequest request, HttpSession httpSession) throws IOException {
        ModelAndView modelAndView  = new ModelAndView("/product/product_list");
        List<Product> productList = productService.queryAllProduct();
        modelAndView.addObject("productList", productList);

        return modelAndView;
    }

    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    public ModelAndView productDetail(HttpServletRequest request) throws Exception {
        String productId = request.getParameter("product_id");
        ModelAndView modelAndView = new ModelAndView("/product/product_detail");
        Product product = productService.queryProductByProductId(productId);
        if (product == null){
            throw new Exception("Can't find the product");
        }
        modelAndView.addObject("product", product);
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
        Customer hasCustomer = (Customer)httpSession.getAttribute("customer");
        if (hasCustomer != null){
            return;
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
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
                        Customer customer = bindCustomer(ops, authorization, code);
                        if (customer != null){
                            httpSession.setAttribute("customer", customer);
                        }
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
            Customer customer = bindCustomer(ops, authorization, code);
            if (customer != null){
                httpSession.setAttribute("customer", customer);
                Call<CustomerWechat> wechatCall = wechatRequest.requestWechatInfo(authorization.getAccess_token(), customer.getOpenid(), "zh_CN");
                wechatCall.enqueue(new Callback<CustomerWechat>() {
                    @Override
                    public void onResponse(Call<CustomerWechat> call, Response<CustomerWechat> response) {
                        CustomerWechat customerWechat = response.body();
                        logger.info(customerWechat);
                        if (customerWechat == null || customerWechat.getOpenid() == null){
                            return;
                        }
                        customerWechat.setCustomer_id(customer.getCustomer_id());
                        customerService.addOrUpdateWechatInfo(customerWechat);
                    }

                    @Override
                    public void onFailure(Call<CustomerWechat> call, Throwable t) {
                        logger.error(t);
                    }
                });
            }
        }
    }

    private Customer bindCustomer(ValueOperations<String, String> ops, Authorization authorization, String code){
        String openId = authorization.getOpenid();
        logger.info("openid===" + openId);
        if (openId == null || openId.equals("")){
            openId = ops.get("code." + code);
        }
        if (openId == null){
            return null;
        }
        ops.set("code." + code, openId,60, TimeUnit.SECONDS);
        Customer customer = customerService.queryCustomerByOpenId(openId);
        if (customer == null){
            Customer createCus = new Customer();
            String customerId = UUID.randomUUID().toString();
            createCus.setCustomer_id(customerId);
            createCus.setOpenid(openId);
            createCus.setRegister_time(new Date());
            customerService.createCustomerAccount(createCus);
            customer = createCus;
            cartService.createShoppingCart(new ShoppingCart(customerId, UUID.randomUUID().toString()));
        }
        logger.info(customer);
        return customer;
    }
}
