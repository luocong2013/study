package com.zync.security.session.init;

import com.zync.security.session.config.ApplicationConfig;
import com.zync.security.session.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 加载Spring容器，相对于web.xml配置文件
 * @date 2020/7/11 14:31
 */
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 指定 rootContext的配置类
     * Spring容器
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ ApplicationConfig.class };
    }

    /**
     * 指定 servletContext的配置类
     * Servlet容器
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    /**
     * url-mapping
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
