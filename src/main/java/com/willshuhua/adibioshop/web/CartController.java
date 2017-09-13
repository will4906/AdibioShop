package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.cart.AddCartItem;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.CartPatientInfo;
import com.willshuhua.adibioshop.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    private Logger logger = Logger.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/add_cart", method = RequestMethod.POST)
    @ResponseBody
    public Object addCart(HttpSession httpSession, @RequestBody AddCartItem addCartItem){
        logger.info(addCartItem);
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
//        String customerId = "60df649c-51c8-4d1d-b02c-47d44d4b7355";
        PatientInfo patientInfo = addCartItem.getPatientInfo();
        patientInfo.setCustomer_id(customer.getCustomer_id());
//        patientInfo.setCustomer_id(customerId);
        patientInfo.setCountry("CHINA");
        CartItem cartItem = cartService.addCartItem(addCartItem);
        if (cartItem == null){
            return new Result(Result.ERR, "cart item is error");
        }
        return new Result(Result.OK, cartItem);
    }

    @RequestMapping(value = "/reduce_cart_item", method = RequestMethod.POST)
    @ResponseBody
    public Object reduceCartItem(HttpSession httpSession, @RequestBody CartItem cartItem){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        cartItem = cartService.reduceCartItem(cartItem);
        if (cartItem == null){
            return new Result(Result.ERR, "can't find the cart_item");
        }
        return new Result(Result.OK, cartItem);
    }

    @RequestMapping(value = "/show_cart_info", method = RequestMethod.GET)
    @ResponseBody
    public Object showCartInfo(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "Can't find the customer");
        }
        return new Result(Result.OK, cartService.queryCartForCustomer(customer.getCustomer_id()));
    }

    @RequestMapping(value = "/cart_patient_infos", method = RequestMethod.POST)
    @ResponseBody
    public Object cartPatientInfo(HttpSession httpSession, @RequestBody CartItem cartItem){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "Can't find the customer");
        }
        List<Map<String, Object>> cartPatientInfos = cartService.queryCartPatientInfos(cartItem);
        if (cartPatientInfos == null){
            return new Result(Result.ERR, "can't find the cart_item");
        }
        return new Result(Result.OK, cartPatientInfos);
    }

    @RequestMapping(value = "/shopping_cart", method = RequestMethod.GET)
    public ModelAndView shoppingCart(HttpSession httpSession){
        return new ModelAndView("shopping_cart");
    }
}
