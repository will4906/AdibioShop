package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.CustomerWechat;
import com.willshuhua.adibioshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer_info", method = RequestMethod.GET)
    public ModelAndView customerInfo(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        CustomerWechat customerWechat = customerService.getCustomerWechat(customer.getCustomer_id());
        ModelAndView modelAndView = new ModelAndView("/info/customer_info");
        modelAndView.addObject("wechat_info", customerWechat);
        return modelAndView;
    }
}
