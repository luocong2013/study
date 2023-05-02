package com.zync.chat.service.impl;

import com.zync.chat.service.HelloService;

/**`
 * 服务实现
 *
 * @author luocong
 * @version v1.0
 * @date 2023/5/2 22:33
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        int i = 1 / 0;
        return "你好, " + name;
    }
}
