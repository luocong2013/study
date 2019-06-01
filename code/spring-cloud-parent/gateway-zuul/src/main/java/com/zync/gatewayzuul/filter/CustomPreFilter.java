package com.zync.gatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 自定义前置过滤器
 * @date 2019/6/1 23:11
 */
public class CustomPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 定义filter的类型，有pre、post、route、error四种
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 定义filter的顺序，数字越小表示顺序越高、越先执行
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // 表示是否需要执行该filter，true表示执行，false表示不执行
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // filter需要执行的具体操作
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println("Request Method: " + request.getMethod() + " Request URL: " + request.getRequestURL().toString());
        return null;
    }
}
