package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.define.order.OrderType;
import com.willshuhua.adibioshop.dto.access.Authorization;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.order.OrderDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import com.willshuhua.adibioshop.entity.order.MyOrder;
import com.willshuhua.adibioshop.entity.order.OrderQuery;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    private Logger logger = Logger.getLogger(OrderController.class);

    @RequestMapping(value = "/my_order", method = RequestMethod.GET)
    public ModelAndView myOrder(){
        return new ModelAndView("/info/my_order");
    }

    @RequestMapping(value = "/user_init_orders", method = RequestMethod.GET)
    @ResponseBody
    public Object userInitOrders(HttpSession httpSession, @RequestParam("type")String type){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setCustomer_id(customer.getCustomer_id());
        orderQuery.setLimit(10);
        List<MyOrder> myOrderList = orderService.getTopServeralOrders(orderQuery, type);
        return new Result(Result.OK, myOrderList);
    }

    @RequestMapping(value = "/load_more_orders", method = RequestMethod.GET)
    @ResponseBody
    public Object typeOrders(HttpSession httpSession, @RequestParam("type")String type, @RequestParam("row_id")long row_id){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setRow_id(row_id);
        orderQuery.setCustomer_id(customer.getCustomer_id());
        orderQuery.setLimit(10);
        List<MyOrder> myOrderList = orderService.getPartServeralOrders(orderQuery, type);
        return new Result(Result.OK, myOrderList);
    }

    @RequestMapping(value = "/order_detail_page", method = RequestMethod.GET)
    public ModelAndView orderDetailPage(HttpServletRequest request, @RequestParam("order_id")String orderId) throws Exception {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        HttpSession httpSession = request.getSession();
        if (code == null || code.equals("")){
            Customer customer = (Customer)httpSession.getAttribute("customer");
            if (customer == null){
                throw new Exception("Can't find the customer!");
            }
        }else {
            //TODO:处理订单详情
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            Retrofit retrofit = retrofitManager.getGsonRetrofit();
            WechatRequest wechatRequest = retrofit.create(WechatRequest.class);
            Call<Authorization> authorizationCall = wechatRequest.requestAuthorization(wechatProperties.getAppid(), wechatProperties.getAppsecret(), code);
            Response<Authorization> response = authorizationCall.execute();
            Authorization authorization = response.body();
            if (authorization == null){
                throw new Exception("Can't find the customer!");
            }
            String openId = authorization.getOpenid();
            if (openId == null || openId.equals("")){
                openId = ops.get("code." + code);
            }
            if (openId == null){
                return null;
            }
            ops.set("code." + code, openId,60, TimeUnit.SECONDS);
            Customer customer = customerService.queryCustomerByOpenId(openId);
            if (customer == null){
                return null;
            }else {
                httpSession.setAttribute("customer", customer);
            }
        }
        ModelAndView modelAndView = new ModelAndView("/info/order_detail");
        modelAndView.addObject("order_id", orderId);
        return modelAndView;
    }

    @RequestMapping(value = "/order_detail", method = RequestMethod.GET)
    @ResponseBody
    public Object orderInfo(@RequestParam("order_id")String orderId) throws Exception {
        return new Result(Result.OK, orderService.getOrderDetail(orderId));
    }

}










