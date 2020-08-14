package com.zync.security.session.interceptor;

import com.zync.security.session.model.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 简单的权限拦截器
 * @date 2020/7/11 15:31
 */
@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 调用方法之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验用户请求的url是否在用户的权限范围内
        Object object = request.getSession().getAttribute(UserDTO.SESSION_USER_KEY);
        if (Objects.isNull(object)) {
            writeContent(response, "请登录");
            return false;
        }
        UserDTO user = (UserDTO) object;
        // 请求的url
        String requestUri = request.getRequestURI();
        if (user.getAuthorities().contains("p1") && StringUtils.contains(requestUri, "/r/r1")) {
            return true;
        }
        if (user.getAuthorities().contains("p2") && StringUtils.contains(requestUri, "/r/r2")) {
            return true;
        }
        writeContent(response, user.getFullname() + " 没有权限，拒绝访问 Url: [" + requestUri + "]");
        return false;
    }

    /**
     * 响应信息给客户端
     * @param response
     * @param message
     * @throws IOException
     */
    private void writeContent(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(message);
        writer.close();
    }
}
