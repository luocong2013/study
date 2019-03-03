package com.ccyw.springboot.own.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luoc
 * @version V1.0.0
 * @description 自定义拦截器
 * @date 2019/3/3 16:18
 */
public class LogCosInterceptor implements HandlerInterceptor {

    long start = System.nanoTime();

    /**
     * 请求执行前执行
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        start = System.nanoTime();
        return true;
    }

    /**
     * 请求结束执行，但只有preHandle方法返回true的时候才会执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("Interceptor cost = " + (System.nanoTime() - start));
    }

    /**
     * 视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
