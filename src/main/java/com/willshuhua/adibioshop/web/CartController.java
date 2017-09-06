package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @RequestMapping(value = "/add_cart", method = RequestMethod.POST)
    @ResponseBody
    public Object addCart(){
        return new Result();
    }

    @RequestMapping(value = "/show_cart", method = RequestMethod.GET)
    public ModelAndView showCart(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new ModelAndView("redirect:/404");
        }
        return new ModelAndView();
    }
}
