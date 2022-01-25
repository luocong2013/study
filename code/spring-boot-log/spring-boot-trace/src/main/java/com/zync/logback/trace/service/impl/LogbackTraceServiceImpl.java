package com.zync.logback.trace.service.impl;

import com.zync.logback.trace.common.util.HttpClientUtil;
import com.zync.logback.trace.common.util.OkHttpUtil;
import com.zync.logback.trace.common.util.RestTemplateUtil;
import com.zync.logback.trace.service.LogbackTraceService;
import com.zync.logback.trace.wrapper.ThreadPoolExecutorMdcWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void threadPool(final String httpUrl) {
        log.info("Service: 使用线程池的okHttp执行GET请求, httpUrl: [{}]", httpUrl);
        ThreadPoolExecutorMdcWrapper pool = new ThreadPoolExecutorMdcWrapper(2, 2, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new CustomizableThreadFactory("pool-"));
        pool.execute(() -> OkHttpUtil.doGet(httpUrl));
    }
}
