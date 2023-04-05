package com.zync.chat.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 本系统使用的固定解析参数的 LengthFieldBasedFrameDecoder
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:28
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
