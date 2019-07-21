package com.zync;

import com.zync.common.Result;
import com.zync.common.User;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试类
 * @date 2019/7/21 11:28
 */
public class Client {

    public static void main(String[] args){
        Result result = Result.buildSuccessResult(200, "成功", new User("张三", "123", 20));
        System.out.println(result);
    }
}
