package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/my_order", method = RequestMethod.GET)
    public ModelAndView myOrder(HttpServletRequest request, HttpSession httpSession){
//        Customer customer = (Customer)httpSession.getAttribute("customer");
//        if (customer == null){

//        }
        return new ModelAndView("/info/my_order");
    }

    @RequestMapping(value = "/order_info", method = RequestMethod.GET)
    @ResponseBody
    public Object orderInfo(@RequestParam("order_id")String orderId){
        return orderService.queryOrderInfoByOrderId(orderId);
    }

}
