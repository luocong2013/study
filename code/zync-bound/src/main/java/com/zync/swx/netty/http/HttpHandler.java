package com.zync.swx.netty.http;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description http响应的异步回调
 * @date 2017-12-12 17:10
 */
public interface HttpHandler {

    void handler(Map<String, String> headMap, String body);

    Charset getCharset();
}
