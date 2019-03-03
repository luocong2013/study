package com.ccyw.springboot.own.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author luoc
 * @version V1.0.0
 * @description 自定义Filter, 直接用@WebFilter配置
 * 需要注意一点的是@WebFilter这个注解是Servlet3.0的规范，并不是Spring boot提供的。
 * 除了这个注解以外，我们还需在启动类中加另外一个注解：@ServletComponetScan，指定扫描的包。
 * @date 2019/3/3 15:47
 */
@WebFilter(urlPatterns = "/*", filterName = "logCosFilter")
public class LogCosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.nanoTime();
        chain.doFilter(request, response);
        System.out.println("LogCosFilter Execute cost = " + (System.nanoTime() - start));
    }

    @Override
    public void destroy() {

    }
}
