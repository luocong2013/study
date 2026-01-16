package com.customzied.common.configuration.wrapper;

import com.customzied.common.utils.JacksonUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 多次读写BODY用HTTP REQUEST - 解决流只能读一次问题
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/1 10:04
 */
@Slf4j
public class MultiReadHttpServletRequestWrapper extends ContentCachingRequestWrapper {

    private final AtomicBoolean isFirst = new AtomicBoolean(true);

    /**
     * Create a new ContentCachingRequestWrapper for the given servlet request.
     *
     * @param request the original servlet request
     */
    public MultiReadHttpServletRequestWrapper(HttpServletRequest request) {
        // 限制缓存的请求体大小为10M，超过将丢弃
        super(request, 10485760);
    }

    /**
     * 如果要在 filterChain.doFilter 之前读取请求体数据，那么就需要从写 getInputStream 方法
     * 如果只在 filterChain.doFilter 之后读取请求体数据，那么就用 ContentCachingRequestWrapper 即可，类似 org.springframework.web.filter.CommonsRequestLoggingFilter 记录日志
     *
     * ContentCachingRequestWrapper 的 getInputStream 还是只能读取一次数据流
     * 它只是将读取到的数据缓存起来而已，读取过一次之后这个流本身就空了，后续再读数据只能通过 getContentAsByteArray 从缓存中读取
     *
     * 那么问题来了，为什么Spring不用这种方式呢？这样用起来会更加方便。
     * 个人认为有两个主要原因：
     * 首先，这种方式存在内存占用问题。我们的自定义Wrapper一上来就把整个请求缓存起来，而且一直持续到请求处理完成。
     * 对于请求体较大的情况，会对系统的内存造成一定的压力，因此一定要设置一个阈值，超过这个阈值时应降级，避免缓存过大的请求体。
     * 其次，我们完全自定义了一个ServletInputStream，其行为和支持的功能跟原始的ServletInputStream不同，而且也不支持异步的Dispatch类型。
     * 虽然我们的应用可以承受这种不一致带来的风险，但是作为一个框架就不能这么做。
     *
     * 因此，个人不太建议使用这种方式的，更倾向于采用 ContentCachingRequestWrapper 的方案。如果非要用这种方案，务必限制请求大小，并添加黑白名单功能，以尽量减小影响范围。
     *
     * @return
     * @throws IOException
     */
    @Nonnull
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isFirst.get()) {
            isFirst.set(false);
            return super.getInputStream();
        }
        return new DelegatingServletInputStream(new ByteArrayInputStream(getContentAsByteArray()));
    }

    @Nonnull
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }

    /**
     * 将前端请求的表单数据转换成json字符串
     *
     * @param request request
     * @return java.lang.String
     */
    public String getBodyJsonStrByForm(ServletRequest request){
        Map<String, String[]> bodyMap = request.getParameterMap();
        return JacksonUtil.toJson(bodyMap);
    }


    /**
     * 将前端传递的json数据转换成json字符串
     *
     * @param request
     * @return
     */
    public String getBodyJsonStrByJson(ServletRequest request) {
        StringBuilder json = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch(Exception e) {
            log.error("请求参数转换错误!", e);
        }
        return json.toString();
    }
}
