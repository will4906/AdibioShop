package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.dto.order.PatientDetail;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.cart.CartItem;
import com.willshuhua.adibioshop.entity.cart.ItemProduct;
import com.willshuhua.adibioshop.entity.cart.ShoppingCart;
import com.willshuhua.adibioshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/add_cart", method = RequestMethod.POST)
    @ResponseBody
    public Object addCart(HttpSession httpSession, @ModelAttribute("PatientDetail")PatientDetail patientDetail){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        CartItem cartItem = cartService.hasCartItem(customer.getCustomer_id(), patientDetail.getProduct_id());
        if (cartItem == null){
            ShoppingCart shoppingCart = cartService.queryShoppingCart(customer.getCustomer_id());
            cartItem = new CartItem(shoppingCart.getCart_id(), UUID.randomUUID().toString(), patientDetail.getProduct_id(), 1);
            cartService.insertCartItem(cartItem);
        }else {
            cartService.addCartItemQuantity(cartItem.getCart_id());
        }
        return new Result(Result.OK, cartItem);
    }

    @RequestMapping(value = "/reduce_cart_item", method = RequestMethod.POST)
    @ResponseBody
    public Object reduceCartItem(HttpSession httpSession, @ModelAttribute("cartItem")CartItem cartItem){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        CartItem queryCartItem = cartService.hasCartItem(customer.getCustomer_id(), cartItem.getProduct_id());
        if (queryCartItem != null){
            cartService.reduceCartItem(queryCartItem);
        }else {
            return new Result(Result.ERR, "can't find cart_item");
        }
        return new Result(Result.OK, queryCartItem);
    }

    @RequestMapping(value = "/show_cart", method = RequestMethod.GET)
    @ResponseBody
    public Object showCart(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "Can't find the customer");
        }
        return new Result(Result.OK, cartService.queryCartForCustomer(customer.getCustomer_id()));
    }
}
