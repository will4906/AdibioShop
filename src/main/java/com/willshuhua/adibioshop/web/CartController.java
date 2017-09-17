package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.cart.AddCartItem;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.entity.Product;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.CartPatientInfo;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.service.CartService;
import com.willshuhua.adibioshop.service.ProductService;
import com.willshuhua.adibioshop.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    private Logger logger = Logger.getLogger(CartController.class);

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/add_cart", method = RequestMethod.POST)
    @ResponseBody
    public Object addCart(HttpSession httpSession, @RequestBody AddCartItem addCartItem) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Customer customer = (Customer)httpSession.getAttribute("customer");
        PatientInfo patientInfo = addCartItem.getPatientInfo();
        logger.info(addCartItem);
        logger.info("patientInfo======" + patientInfo);
        patientInfo.setCustomer_id(customer.getCustomer_id());
        patientInfo.setCountry("CHINA");
        CartItem cartItem = cartService.addCartItem(addCartItem);
        if (cartItem == null){
            return new Result(Result.ERR, "cart item is error");
        }
        return new Result(Result.OK, cartItem);
    }

    @RequestMapping(value = "/reduce_cart_item", method = RequestMethod.POST)
    @ResponseBody
    public Object reduceCartItem(@RequestBody CartPatientInfo cartPatientInfo){
        CartItem cartItem = cartService.reduceCartItem(cartPatientInfo);
        if (cartItem == null){
            return new Result(Result.ERR, "can't find the cart_item");
        }
        return new Result(Result.OK, cartItem);
    }

    @RequestMapping(value = "/show_cart_info", method = RequestMethod.GET)
    @ResponseBody
    public Object showCartInfo(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        return new Result(Result.OK, cartService.queryCartForCustomer(customer.getCustomer_id()));
    }

    @RequestMapping(value = "/cart_patient_infos", method = RequestMethod.POST)
    @ResponseBody
    public Object cartPatientInfo(@RequestBody CartItem cartItem){
        List<Map<String, Object>> cartPatientInfos = cartService.queryCartPatientInfos(cartItem);
        if (cartPatientInfos == null){
            return new Result(Result.ERR, "can't find the cart_item");
        }
        return new Result(Result.OK, cartPatientInfos);
    }

    @RequestMapping(value = "/shopping_cart", method = RequestMethod.GET)
    public ModelAndView shoppingCart(HttpSession httpSession) throws Exception {
        Customer customer = (Customer)httpSession.getAttribute("customer");
        List<ItemProduct> itemProductList = cartService.queryCartForCustomer(customer.getCustomer_id());
        ModelAndView modelAndView = new ModelAndView("shopping_cart");
        modelAndView.addObject("itemProductList", itemProductList);
        return modelAndView;
    }
}
