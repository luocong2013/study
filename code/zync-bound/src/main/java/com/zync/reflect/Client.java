package com.zync.reflect;

import com.zync.reflect.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 测试类
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/13 10:11
 */
public class Client {

    public static void main(String[] args) throws Exception {
        User user = new User();
        System.out.println(user);

        BeanInfoUtil.setProperty(user, "id", 1);
        System.out.println(user);
        System.out.println(BeanInfoUtil.getProperty(user, "id"));

        BeanInfoUtil.setPropertyByIntrospector(user, "userName", "张三");
        System.out.println(user);
        System.out.println(BeanInfoUtil.getPropertyByIntrospector(user, "userName"));

        // apache beanutils
        BeanUtils.setProperty(user, "age", 20);
        System.out.println(user);
        System.out.println(BeanUtils.getProperty(user, "age"));

        PropertyUtils.setProperty(user, "email", "123@qq.com");
        System.out.println(user);
        System.out.println(PropertyUtils.getProperty(user, "email"));
    }
}
