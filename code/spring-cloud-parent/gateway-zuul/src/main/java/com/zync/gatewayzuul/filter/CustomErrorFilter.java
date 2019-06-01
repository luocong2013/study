package com.zync.gatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletResponse;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 自定义错误过滤器
 * @date 2019/6/1 23:13
 */
public class CustomErrorFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        Throwable throwable = context.getThrowable();
        LOGGER.error("[ErrorFilter] error message: {}", throwable.getCause().getMessage());
        context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        context.set("error.exception", throwable.getCause());
        return null;
    }
}
