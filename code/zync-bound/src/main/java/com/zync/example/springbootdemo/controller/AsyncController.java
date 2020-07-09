package com.zync.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description Spring异步请求
 * @date 2020/7/7 11:24
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 方式一：Servlet方式实现异步请求
     * @param request
     * @param response
     */
    @GetMapping("/servletRequest")
    public void servletRequest(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        // 设置监听器：可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                // 这里可以做一些清理资源的操作
                System.out.println("执行完成...");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                // 这里可以做一些超时后的相关操作
                System.out.println("执行超时...");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("执行错误..." + asyncEvent.getThrowable());
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("执行开始...");
            }
        });

        // 设置超时事件
        asyncContext.setTimeout(20000);
        asyncContext.start(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("内部线程：" + Thread.currentThread().getName());
                asyncContext.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());
                asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                asyncContext.getResponse().getWriter().println("这是异步的请求返回" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 异步请求完成通知
                // 此时整个请求才完成
                asyncContext.complete();
            }
        });

        // 此时 request 的线程连接已经释放了
        System.out.println("外部线程：" + Thread.currentThread().getName());
    }

    /**
     * 方式二：使用很简单，直接返回的参数包裹一层callable即可，可以继承WebMvcConfigurerAdapter类来设置默认线程池和超时处理
     * @return
     */
    @GetMapping("/callableRequest")
    public Callable<String> callableRequest() {
        System.out.println("外部线程：" + Thread.currentThread().getName());

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("内部线程：" + Thread.currentThread().getName());
                return "callable! " + Thread.currentThread().getName();
            }
        };
    }

    /**
     * 方式三：和方式二差不多，在Callable外包一层，给WebAsyncTask设置一个超时回调，即可实现超时处理
     * @return
     */
    @GetMapping("/webAsyncTaskRequest")
    public WebAsyncTask<String> webAsyncTaskRequest() {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        Callable<String> result = () -> {
            System.out.println("内部线程：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("内部线程返回：" + Thread.currentThread().getName());
            return "success";
        };
        WebAsyncTask<String> wat = new WebAsyncTask<>(3000L, result);
        wat.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "超时";
            }
        });
        return wat;
    }

    @GetMapping("/deferredResultRequest")
    public DeferredResult<String> deferredResultRequest() {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        // 设置超时时间
        DeferredResult<String> result = new DeferredResult<>(60 * 1000L);
        // 处理超时事件，采用委托机制
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("DeferredResult超时");
                result.setResult("超时了");
            }
        });
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                // 完成后
                System.out.println("调用完成");
            }
        });
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 处理业务逻辑
                System.out.println("内部线程：" + Thread.currentThread().getName());
                // 返回结果
                result.setResult("DeferredResult!!");
            }
        });
        return result;
    }
}
