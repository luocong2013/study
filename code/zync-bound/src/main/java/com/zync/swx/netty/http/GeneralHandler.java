package com.zync.swx.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-12-12 17:35
 */
public class GeneralHandler extends ChannelInboundHandlerAdapter {
    HttpHandler httpHandler;
    Integer respLength = Integer.MAX_VALUE;
    Map<String, String> head = new HashMap<>();
    String respContent = "";

    public GeneralHandler(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            for (Map.Entry entry : response.headers().entries()) {
                head.put((String) entry.getKey(), (String) entry.getValue());
            }
            if (response.headers().get("Content-Length") != null) {
                respLength = Integer.parseInt(response.headers().get("Content-Length"));
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            respContent += buf.toString(httpHandler.getCharset());
            ((HttpContent) msg).release();
            if (respContent.getBytes().length >= respLength || !buf.isReadable()) {
                ctx.channel().close();
                httpHandler.handler(head, respContent);
            }
        }
    }
}
