package com.zync.swx.nio;

import java.nio.IntBuffer;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.nio
 * @description TODO
 * @date 2017-12-8 14:25
 */
public class BufferDemo {
    public static void main(String[] args){
        IntBuffer intBuffer = IntBuffer.allocate(20);
        intBuffer.put(12345678);
        intBuffer.put(2222);

        //转换为读模式
        intBuffer.flip();

        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());

        //转换为写模式
//        intBuffer.compact();
        intBuffer.clear();
        intBuffer.put(333);
        intBuffer.put(67890);

        //转换为读模式
        intBuffer.flip();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
    }
}
