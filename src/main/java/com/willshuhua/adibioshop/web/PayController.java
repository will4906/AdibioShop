package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.common.SnowflakeIdWorker;
import com.willshuhua.adibioshop.define.order.OrderStatus;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.Product;
import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import com.willshuhua.adibioshop.entity.order.OrderInfo;
import com.willshuhua.adibioshop.entity.order.OrderItem;
import com.willshuhua.adibioshop.properties.WechatProperties;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import com.willshuhua.adibioshop.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Controller
public class PayController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WechatProperties wechatProperties;

    private Logger logger = Logger.getLogger(PayController.class);

    @RequestMapping(value = "/pay_success", method = RequestMethod.GET)
    public String paySuccess(){
        return "pay_success";
    }

    @RequestMapping(value = "/pay_fail", method = RequestMethod.GET)
    public String payFail(){
        return "pay_fail";
    }

    @RequestMapping(value = "/direct_pay", method = RequestMethod.POST)
    @ResponseBody
    public Object payEvent(@ModelAttribute("patientDetail")PatientDetail patientDetail, HttpSession httpSession){
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

        Order order = new Order(orderId, customer.getCustomer_id(), product.getPrice(), OrderStatus.CREATION, null);
        OrderEvent orderEvent = new OrderEvent(UUID.randomUUID().toString(), orderId, new Date(), OrderStatus.CREATION, customer.getCustomer_id(), null);
        OrderItem orderItem = new OrderItem(orderId, UUID.randomUUID().toString(), productId, 1);
//      TODO:这是一段冗长的配置，日后会想想是否有什么更好地方式。
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_itemid(orderItem.getOrder_itemid());
        orderInfo.setOrder_infoid(UUID.randomUUID().toString());
        orderInfo.setProduct_id(patientDetail.getProduct_id());
        orderInfo.setName(patientDetail.getName());
        orderInfo.setGender(patientDetail.getGender());
        orderInfo.setAge(Float.valueOf(patientDetail.getAge()));
        orderInfo.setCountry("CHINA");
        orderInfo.setProvince(patientDetail.getProvince());
        orderInfo.setCity(patientDetail.getCity());
        orderInfo.setDistrict(patientDetail.getDistrict());
        orderInfo.setAddress(patientDetail.getAddress());
        orderInfo.setPhone(patientDetail.getPhone());
        orderInfo.setHas_diabetic(Integer.valueOf(patientDetail.getHas_diabetic()));
        orderInfo.setIs_pregnant(Integer.valueOf(patientDetail.getIs_pregnant()));
        if (!(patientDetail.getHeight() == null || patientDetail.getHeight().equals("null") || patientDetail.getHeight().equals(""))){
            orderInfo.setHeight(Float.valueOf(patientDetail.getHeight()));
        }
        if (!(patientDetail.getWeight() == null || patientDetail.getWeight().equals("null") || patientDetail.getWeight().equals(""))){
            orderInfo.setWeight(Float.valueOf(patientDetail.getWeight()));
        }

        logger.info(patientDetail);
        return new Result();
    }


}
