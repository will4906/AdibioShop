package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.define.order.OrderType;
import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.order.MyOrder;
import com.willshuhua.adibioshop.entity.order.OrderQuery;
import com.willshuhua.adibioshop.service.CustomerService;
import com.willshuhua.adibioshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

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

    @RequestMapping(value = "/order_detail", method = RequestMethod.GET)
    @ResponseBody
    public Object orderInfo(@RequestParam("order_id")String orderId){
        return orderService.queryOrderInfoByOrderId(orderId);
    }

}
