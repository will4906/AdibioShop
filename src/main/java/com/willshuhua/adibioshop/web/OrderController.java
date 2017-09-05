package com.willshuhua.adibioshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    @RequestMapping(value = "/my_order", method = RequestMethod.GET)
    public ModelAndView myOrder(HttpServletRequest request){
//        Customer customer = (Customer)httpSession.getAttribute("customer");
//        if (customer == null){

//        }
        return new ModelAndView("my_order");
    }
}
