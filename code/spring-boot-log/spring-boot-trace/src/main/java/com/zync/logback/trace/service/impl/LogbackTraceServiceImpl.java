package com.zync.logback.trace.service.impl;

import com.zync.logback.trace.common.util.HttpClientUtil;
import com.zync.logback.trace.common.util.OkHttpUtil;
import com.zync.logback.trace.common.util.RestTemplateUtil;
import com.zync.logback.trace.service.LogbackTraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * logback trace 服务接口实现类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 11:04
 */
@Slf4j
@Service
public class LogbackTraceServiceImpl implements LogbackTraceService {

    @Override
    public String httpClient(String httpUrl) {
        log.info("Service: httpClient执行GET请求, httpUrl: [{}]", httpUrl);
        return HttpClientUtil.doGet(httpUrl);
    }

    @Override
    public String restTemplate(String httpUrl) {
        log.info("Service: restTemplate执行GET请求, httpUrl: [{}]", httpUrl);
        return RestTemplateUtil.doGet(httpUrl);
    }

    @Override
    public String okHttp(String httpUrl) {
        log.info("Service: okHttp执行GET请求, httpUrl: [{}]", httpUrl);
        return OkHttpUtil.doGet(httpUrl);
    }
}
