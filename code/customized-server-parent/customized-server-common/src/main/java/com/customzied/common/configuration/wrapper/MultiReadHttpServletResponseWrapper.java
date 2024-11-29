package com.customzied.common.configuration.wrapper;

import com.customzied.common.constant.Const;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * 多次读写BODY用HTTP REQUEST - 解决流只能读一次问题
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/2 17:11
 */
public class MultiReadHttpServletResponseWrapper extends ContentCachingResponseWrapper {

    /**
     * Create a new ContentCachingResponseWrapper for the given servlet response.
     *
     * @param response the original servlet response
     */
    public MultiReadHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public String getCharacterEncoding() {
        return Const.DEFAULT_CHARACTER_ENCODING;
    }
}
