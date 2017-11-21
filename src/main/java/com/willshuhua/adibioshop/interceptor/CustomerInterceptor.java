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
        logger.info("request_uri=====" + uri);
        String[] allowUris = new String[]{"/index", "/message", "/wechat_pay_notify", "/error", "/order_detail_page", "/share_info", "/notify_analysis", "/analysis_detail"};
        for (String allow : allowUris){
            if (uri.contains(allow)){
                return true;
            }
        }
        if (uri.equals("/")){
            return true;
        }
        HttpSession httpSession = request.getSession();
        Customer customer = (Customer) httpSession.getAttribute("customer");
        if (customer == null){
            throw new Exception("Cant't find customer!");
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
