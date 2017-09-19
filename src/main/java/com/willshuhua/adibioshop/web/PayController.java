package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.common.SnowflakeIdWorker;
import com.willshuhua.adibioshop.common.TokenInstance;
import com.willshuhua.adibioshop.define.order.OrderStatus;
import com.willshuhua.adibioshop.define.template.TemplateId;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.dto.template.TemplateBack;
import com.willshuhua.adibioshop.dto.template.WechatTemplate;
import com.willshuhua.adibioshop.dto.wechat_pay.*;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.Product;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.retrofit.RetrofitManager;
import com.willshuhua.adibioshop.retrofit.wechat.WechatRequest;
import com.willshuhua.adibioshop.service.CartService;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import com.willshuhua.adibioshop.service.ProductService;
import com.willshuhua.adibioshop.util.BeanUtil;
import com.willshuhua.adibioshop.util.Encryption;
import com.willshuhua.adibioshop.util.WechatTool;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class PayController {

    private RetrofitManager retrofitManager = RetrofitManager.getInstance();

    private TokenInstance tokenInstance = TokenInstance.getInstance();
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Logger logger = Logger.getLogger(PayController.class);

    @RequestMapping(value = "/pay_success", method = RequestMethod.GET)
    public String paySuccess(){
        return "/pay/pay_success";
    }

    @RequestMapping(value = "/pay_fail", method = RequestMethod.GET)
    public String payFail(){
        return "/pay/pay_fail";
    }

    @RequestMapping(value = "/direct_pay", method = RequestMethod.POST)
    @ResponseBody
    public Object directPay(HttpServletRequest request, HttpSession httpSession, @RequestParam("patient_infoid")String patientInfoId, @RequestParam("product_id")String productId) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        Customer customer = (Customer) httpSession.getAttribute("customer");
        logger.info("patient_Infoid===" + patientInfoId);
        logger.info("product_id===" + productId);
        if (patientInfoId == null || productId == null){
            return new Result(Result.ERR, "the parameter is error!");
        }
        Product product = productService.queryProductByProductId(productId);
        PatientInfo patientInfo = customerService.hasPatientInfoId(patientInfoId);
        if (product == null || patientInfo == null){
            return new Result(Result.ERR, "the parameter is error!");
        }
        //配置订单
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String orderId = String.valueOf(idWorker.nextId());
        Order order = new Order(orderId, customer.getCustomer_id(), product.getUnit_price(), OrderStatus.CREATION, null);
        OrderEvent orderEvent = new OrderEvent(UUID.randomUUID().toString(), orderId, new Date(), OrderStatus.CREATION, customer.getCustomer_id(), null);
        OrderItem orderItem = new OrderItem(orderId, UUID.randomUUID().toString(), productId, 1);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_itemid(orderItem.getOrder_itemid());
        orderInfo.setOrder_infoid(UUID.randomUUID().toString());
        orderInfo.setProduct_id(productId);
        orderInfo.setPatient_infoid(patientInfo.getPatient_infoid());
        orderService.createOrder(order, orderInfo, orderEvent, orderItem);
        return notifyUnifiedOrder(request, order, product.getProduct_name(), customer);
    }

    /*
    *
    * @RequestMapping(value = "/direct_pay", method = RequestMethod.POST)
    @ResponseBody
    public Object payEvent(@ModelAttribute("patientDetail")PatientDetail patientDetail, HttpServletRequest request, HttpSession httpSession) throws Exception {
        logger.info(patientDetail);
//        校验各种东西，到时候需要重新构造 TODO:考虑使用spring security或者其他方式处理
        Customer customer = (Customer) httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "Can't find the openid.Please retry !");
        }
        String productId = patientDetail.getProduct_id();
        if (productId == null || productId.equals("null") || productId.equals("")){
            return new Result(Result.ERR, "Can't find the product_id");
        }
        Product product = productService.queryProductByProductId(productId);
        if (product == null){
            return new Result(Result.ERR, "Can't find the product");
        }
//      配置订单
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String orderId = String.valueOf(idWorker.nextId());

        Order order = new Order(orderId, customer.getCustomer_id(), product.getUnit_price(), OrderStatus.CREATION, null);
        OrderEvent orderEvent = new OrderEvent(UUID.randomUUID().toString(), orderId, new Date(), OrderStatus.CREATION, customer.getCustomer_id(), null);
        OrderItem orderItem = new OrderItem(orderId, UUID.randomUUID().toString(), productId, 1);
//      TODO:这是一段冗长的配置，日后会想想是否有什么更好地方式。
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_itemid(orderItem.getOrder_itemid());
        orderInfo.setOrder_infoid(UUID.randomUUID().toString());
        orderInfo.setProduct_id(patientDetail.getProduct_id());

        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setCustomer_id(customer.getCustomer_id());
        patientInfo.setName(patientDetail.getName());
        patientInfo.setGender(patientDetail.getGender());
        patientInfo.setAge(Float.valueOf(patientDetail.getAge()));
        patientInfo.setCountry("CHINA");
        patientInfo.setProvince(patientDetail.getProvince());
        patientInfo.setCity(patientDetail.getCity());
        patientInfo.setDistrict(patientDetail.getDistrict());
        patientInfo.setAddress(patientDetail.getAddress());
        patientInfo.setPhone(patientDetail.getPhone());
        patientInfo.setHas_diabetic(Integer.valueOf(patientDetail.getHas_diabetic()));
        patientInfo.setIs_pregnant(Integer.valueOf(patientDetail.getIs_pregnant()));
        if (!(patientDetail.getHeight() == null || patientDetail.getHeight().equals("null") || patientDetail.getHeight().equals(""))){
            patientInfo.setHeight(Float.valueOf(patientDetail.getHeight()));
        }
        if (!(patientDetail.getWeight() == null || patientDetail.getWeight().equals("null") || patientDetail.getWeight().equals(""))){
            patientInfo.setWeight(Float.valueOf(patientDetail.getWeight()));
        }

        PatientInfo hasPatient = customerService.hasPatientInfo(patientInfo);
        if (hasPatient != null){
            patientInfo = hasPatient;
        }else {
            patientInfo.setPatient_infoid(UUID.randomUUID().toString());
            customerService.createPatientInfo(patientInfo);
        }

        orderInfo.setPatient_infoid(patientInfo.getPatient_infoid());
        orderService.createOrder(order, orderInfo, orderEvent, orderItem);
        return notifyUnifiedOrder(request, order, product.getProduct_name(), customer);
    }
    * */

    @RequestMapping(value = "/buy_cart_selects", method = RequestMethod.POST)
    @ResponseBody
    public Object buyCartSelects2(HttpServletRequest request, HttpSession httpSession, @RequestBody List<CartItem> cartItemList) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "Can't find the customer");
        }

        Order order = new Order();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String orderId = String.valueOf(idWorker.nextId());
        order.setOrder_id(orderId);
        order.setCustomer_id(customer.getCustomer_id());
        order.setStatus(OrderStatus.CREATION);

        OrderEvent orderEvent = new OrderEvent(UUID.randomUUID().toString(), orderId, new Date(), OrderStatus.CREATION, customer.getCustomer_id(), null);

        List<OrderInfo> orderInfoList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();

        BigDecimal wholePrice = new BigDecimal(0);
        for (CartItem cartItem : cartItemList){
            cartItem = cartService.getCartItem(cartItem.getCart_itemid());
            if (cartItem == null){
                return new Result(Result.ERR, "Can't find CartItem!");
            }
            List<Map<String, Object>> cartPatientInfoList = cartService.queryCartPatientInfos(cartItem);
            OrderItem orderItem = new OrderItem(order.getOrder_id(), UUID.randomUUID().toString(), cartItem.getProduct_id(), cartItem.getQuantity());
            orderItemList.add(orderItem);
            Product product = productService.queryProductByProductId(cartItem.getProduct_id());
            BigDecimal unit_price = product.getUnit_price();
            wholePrice = wholePrice.add(unit_price.multiply(new BigDecimal(cartItem.getQuantity())));
            for (Map map : cartPatientInfoList){
                OrderInfo orderInfo = new OrderInfo(orderItem.getOrder_itemid(), UUID.randomUUID().toString(), cartItem.getProduct_id(), (String) map.get("patient_infoid"));
                orderInfoList.add(orderInfo);
            }
        }
        order.setPrice(wholePrice);
        orderService.createOrder(order, orderInfoList, orderEvent, orderItemList);
        logger.info(cartItemList);
        return notifyUnifiedOrder(request, order, "批量检测", customer);
    }

    @RequestMapping(value = "/wechat_pay_notify", method = RequestMethod.POST)
    @ResponseBody
    public String wechatPayNotify(@RequestBody WechatPayBack wechatPayBack, HttpServletRequest request) throws Exception {
        logger.info(wechatPayBack);

        Serializer serializer = new Persister();
        ReturnPayNotify returnPayNotify = new ReturnPayNotify("SUCCESS", "OK");

        StringWriter resultWriter = new StringWriter();
        String result = "";
        serializer.write(returnPayNotify, resultWriter);
        result = resultWriter.toString();

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey("pay." + wechatPayBack.getOut_trade_no())){
            return result;
        }else {
            ops.set("pay." + wechatPayBack.getOut_trade_no(), "send", 1, TimeUnit.DAYS);
        }

        Customer customer = customerService.queryCustomerByOpenId(wechatPayBack.getOpenid());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder_eventid(UUID.randomUUID().toString());
        orderEvent.setEvent_title(OrderStatus.PAID);
        orderEvent.setDescription(wechatPayBack.toString());
        orderEvent.setEvent_time(new Date());
        orderEvent.setOrder_id(wechatPayBack.getOut_trade_no());
        orderEvent.setEvent_executor(customer.getCustomer_id());
        orderService.changeOrderStatus(orderEvent);

        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(customer.getOpenid());
        wechatTemplate.setTemplate_id(TemplateId.CHECKOUT_SUCCESS);
        wechatTemplate.setUrl(request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() + request.getContextPath() + "/order_info?order_id=" + wechatPayBack.getOut_trade_no());
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> first = new HashMap<>();
        first.put("value", "您好，您已下单成功。");
        first.put("color", "#173177");
        data.put("first", first);
        Map<String, String> keyword1 = new HashMap<>();
        keyword1.put("value", orderEvent.getOrder_id());
        keyword1.put("color", "#173177");
        data.put("keyword1", keyword1);
        Map<String, String> keyword2 = new HashMap<>();
        keyword2.put("value", "￥" + (new BigDecimal(wechatPayBack.getCash_fee()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)).toString());
        keyword2.put("color", "#173177");
        data.put("keyword2", keyword2);
        Map<String, String> keyword3 = new HashMap<>();
        keyword3.put("value", "微信支付");
        keyword3.put("color", "#173177");
        data.put("keyword3", keyword3);
        Map<String, String> keyword4 = new HashMap<>();
        keyword4.put("value", "需要");
        keyword4.put("color", "#173177");
        data.put("keyword4", keyword4);
        Map<String, String> remark = new HashMap<>();
        remark.put("value", "点击【详情】查看完整订单信息");
        remark.put("color", "#173177");
        data.put("remark", remark);
        wechatTemplate.setData(data);

        String access_token = tokenInstance.getAccessToken(wechatProperties.getAppid(), wechatProperties.getAppsecret());

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
        return result;
    }

    private Result notifyUnifiedOrder(HttpServletRequest request, Order order, String orderName, Customer customer) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
//        TODO:还得做用户取消支付的删除订单，和超时未支付的订单取消
        UnifiedOrder unifiedOrder = new UnifiedOrder();

        String appId = wechatProperties.getAppid();
        String muh_id = wechatProperties.getMch_id();
        String nonce_str = Encryption.md5(UUID.randomUUID().toString());
        String body = wechatProperties.getMerchant() + "-" + orderName;
        String out_trade_no = order.getOrder_id();
        String total_fee = order.getPrice().multiply(new BigDecimal(100)).toBigInteger().toString();
        String spbill_create_ip = request.getRemoteAddr();
        String notify_url = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() + request.getContextPath() + "/wechat_pay_notify";
        String trade_type = "JSAPI";
        String openid = customer.getOpenid();

        unifiedOrder.setAppid(appId);
        unifiedOrder.setMch_id(muh_id);
        unifiedOrder.setNonce_str(nonce_str);
        unifiedOrder.setBody(body);
        unifiedOrder.setOut_trade_no(out_trade_no);
        unifiedOrder.setTotal_fee(total_fee);
        unifiedOrder.setSpbill_create_ip(spbill_create_ip);
        unifiedOrder.setNotify_url(notify_url);
        unifiedOrder.setTrade_type(trade_type);
        unifiedOrder.setOpenid(openid);
        //计算sign
        SortedMap<String, String> sortedMap = BeanUtil.beanToMap(unifiedOrder);
        if (sortedMap == null){
            return new Result(Result.ERR, "Can't parse the order.");
        }
        String sign = WechatTool.generateMD5PaySign(sortedMap, wechatProperties.getApikey());
        unifiedOrder.setSign(sign);

        Retrofit retrofit = retrofitManager.getXmlRetrofit();
        WechatRequest wechatRequest = retrofit.create(WechatRequest.class);

        Call<UnifiedOrderBack> unifiedOrderBackCall = wechatRequest.requestUnifiedOrder(unifiedOrder);
        Response<UnifiedOrderBack> backResponse = unifiedOrderBackCall.execute();
        UnifiedOrderBack unifiedOrderBack = backResponse.body();

        JsPayParm jsPayParm = new JsPayParm();
        if (unifiedOrderBack != null){
            jsPayParm.setAppId(wechatProperties.getAppid());
            jsPayParm.setPackage_sign_cut("prepay_id=" + unifiedOrderBack.getPrepay_id());
            jsPayParm.setNonceStr(Encryption.md5(UUID.randomUUID().toString()));
            jsPayParm.setTimeStamp(String.valueOf(new Date().getTime() / 1000));
            jsPayParm.setSignType("MD5");

            //计算sign
            SortedMap<String, String> jsSortMap = BeanUtil.beanToMap(jsPayParm);
            if (jsSortMap == null){
                return new Result(Result.ERR, "Can't parse the order.");
            }
            String paySign = WechatTool.generateMD5PaySign(jsSortMap, wechatProperties.getApikey());
            jsPayParm.setPaySign(paySign);

            logger.info(jsPayParm);
            return new Result(Result.OK, jsPayParm);
        }else {
            return new Result(Result.ERR, "Happened some errors!");
        }
    }

}
