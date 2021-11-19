package com.zync.nacos.order.http;

import com.alibaba.fastjson.JSONObject;
import io.seata.integration.http.DefaultHttpExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 通过Http调用服务
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/18 12:33
 */
@Slf4j
@Component
public class OrderHttpExecutor {

    /**
     * 扣减库存
     * @param id
     * @param amount
     */
    public String reduceStock(Long id, Long amount) {
        try {
            JSONObject params = new JSONObject().fluentPut("id", id).fluentPut("amount", amount);
            HttpResponse response = DefaultHttpExecutor.getInstance().executePost("http://127.0.0.1:8081", "/storage/deductV2", params, HttpResponse.class);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("调用扣减库存服务失败", e);
            throw new RuntimeException("调用扣减库存服务失败");
        }
    }

    /**
     * 扣减余额
     * @param userId
     * @param money
     * @return
     */
    public String reduceBalance(String userId, Long money) {
        try {
            JSONObject params = new JSONObject().fluentPut("userId", userId).fluentPut("money", money);
            HttpResponse response = DefaultHttpExecutor.getInstance().executePost("http://127.0.0.1:8082", "/account/deductV2", params, HttpResponse.class);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("调用扣减余额服务失败", e);
            throw new RuntimeException("调用扣减余额服务失败");
        }
    }
}
