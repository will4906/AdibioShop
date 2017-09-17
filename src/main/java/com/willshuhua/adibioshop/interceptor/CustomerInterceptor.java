package com.willshuhua.adibioshop.interceptor;

import com.willshuhua.adibioshop.entity.Customer;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CustomerInterceptor implements HandlerInterceptor{

    private Logger logger = Logger.getLogger(CustomerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!uri.contains("index")){
            HttpSession httpSession = request.getSession();
            Customer customer = (Customer) httpSession.getAttribute("customer");
            logger.info(customer);
            if (customer == null){
                throw new Exception("Cant't find customer!");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
