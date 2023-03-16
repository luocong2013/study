package com.zync.netty.bytebuf;

import com.zync.netty.bytebuf.utils.ByteBufLogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * ByteBuf学习
 *
 * 直接内存创建和销毁的代价昂贵，但读写性能高（少一次内存复制），适合配合池化功能一起用
 * 直接内存对 GC 压力小，因为这部分内存不受 JVM 垃圾回收的管理，但也要注意及时主动释放
 *
 * 池化与非池化
 * 池化的最大意义在于可以重用 ByteBuf，优点有
 * 没有池化，则每次都得创建新的 ByteBuf 实例，这个操作对直接内存代价昂贵，就算是堆内存，也会增加 GC 压力
 * 有了池化，则可以重用池中 ByteBuf 实例，并且采用了与 jemalloc 类似的内存分配算法提升分配效率
 * 高并发时，池化功能更节约内存，减少内存溢出的可能
 *
 * 池化功能是否开启，可以通过下面的系统环境变量来设置
 * -Dio.netty.allocator.type={unpooled|pooled}
 *
 * 4.1 以后，非 Android 平台默认启用池化实现，Android 平台启用非池化实现
 * 4.1 之前，池化功能还不成熟，默认是非池化实现
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 21:21
 */
@Slf4j
public class TestByteBuf {

    public static void main(String[] args) {
        // 创建池化基于直接内存的 ByteBuf
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(16);
        log.debug("{}", buffer.getClass());
        ByteBufLogUtil.log(buffer);

        // 向ByteBuf中写入数据
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append("a");
        }
        buffer.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8));

        ByteBufLogUtil.log(buffer);


        // 创建池化基于堆内存的 ByteBuf
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.heapBuffer(16);
        log.debug("{}", buffer2.getClass());
    }
}
