package com.zync.swx.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.socket
 * @description TODO
 * @date 2017-12-11 10:04
 */
public class InetAddressDemo {

    public static void main(String[] args) throws UnknownHostException {
        //获取本机的InetAddress实例
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("计算机名：" + address.getHostName());
        System.out.println("IP地址：" + address.getHostAddress());
        System.out.println(address);
        byte[] b = address.getAddress();
        System.out.println("字节数组形式的IP：" + Arrays.toString(b));

//		InetAddress address2 = InetAddress.getByName("Luo-PC");
        InetAddress address2 = InetAddress.getByName("192.168.2.168");
        System.out.println("计算机名：" + address2.getHostName());
        System.out.println("IP地址：" + address2.getHostAddress());
    }
}
