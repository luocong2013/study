package com.zync.swx.netty.http;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-12-12 16:17
 */
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {
    private HttpRequest request;
    private boolean isGet;
    private boolean isPost;

    /**
     * POST: http://localhost:8089/  body: bmsah=山东省院起诉受[2017]37000000094号
     * <p>
     * GET: http://localhost:8089?bmsah=山东省院起诉受[2017]37000000094号
     */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        //1 msg是HttpRequest
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            String uri = request.uri();

            //去除浏览器"/favicon.ico"的干扰
            if (uri.equals("/favicon.ico")) {
                return;
            }

            HttpMethod method = request.method();
            isGet = method.equals(HttpMethod.GET);
            isPost = method.equals(HttpMethod.POST);
            System.out.println(String.format("Uri:%s method %s", uri, method));

            if (isGet) {
                System.out.println("Get请求 doing something here.");
                String param = "bmsah";
                String str = getParamerByNameFromGET(param);
                System.out.println(param + ":" + str);
            }

            if (isPost) {
                System.out.println("Post请求 doing something here.");
            } else {
                String responseString = "返回的数据-------";
                writeHttpResponseJson(responseString, ctx, OK);
            }

        }

        //2 msg是HttpContent
        if (!isGet) {
            System.out.println("isPost");
            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                String bodyString = buf.toString(Charsets.UTF_8);
                System.out.println("body: " + bodyString);
                String l = getParamerByNameFromPOST("bmsah", bodyString);
                System.out.println("参数:" + l);
                buf.release();
                writeHttpResponseJson("post返回成功!", ctx, OK);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private String getParamerByNameFromGET(String name) {
        QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri(), StandardCharsets.UTF_8);
        return getParameterByName(name, decoderQuery);
    }

    private String getParamerByNameFromPOST(String name, String body) {
        QueryStringDecoder decoderQuery = new QueryStringDecoder("some?" + body, StandardCharsets.UTF_8);
        return getParameterByName(name, decoderQuery);
    }

    /**
     * 根据传入参数的key获取value
     *
     * @param name
     * @param decoderQuery
     * @return
     */
    private String getParameterByName(String name, QueryStringDecoder decoderQuery) {
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            String key = attr.getKey();
            for (String attrVal : attr.getValue()) {
                if (key.equals(name)) {
                    return attrVal;
                }
            }
        }
        return null;
    }

    private void writeHttpResponseImg(String responseString, ChannelHandlerContext ctx, HttpResponseStatus status)
            throws UnsupportedEncodingException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);

        //返回图片
        byte[] fileToByte = this.fileToByte("c://bh.png");
        response.content().writeBytes(fileToByte);
        response.headers().set(CONTENT_TYPE, "image/png; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, response.content().writerIndex());
        Channel ch = ctx.channel();
        ch.write(response);
        ctx.write(response);
        ctx.flush();
        ctx.close();
    }

    /**
     * @param responseString
     * @param ctx
     * @param status
     * @throws UnsupportedEncodingException
     */
    private void writeHttpResponseHtml(String responseString, ChannelHandlerContext ctx, HttpResponseStatus status)
            throws UnsupportedEncodingException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(responseString
                .getBytes(Charsets.UTF_8)));
        response.headers().set(CONTENT_TYPE, "text/html");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(EXPIRES, 0);
        Channel ch = ctx.channel();
        ch.write(response);
        ch.close();
    }

    private void writeHttpResponseJson(String responseString, ChannelHandlerContext ctx, HttpResponseStatus status)
            throws UnsupportedEncodingException {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(responseString
                .getBytes(Charsets.UTF_8)));
        response.headers().set(CONTENT_TYPE, "text/json");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(EXPIRES, 0);
        Channel ch = ctx.channel();
        ch.write(response);
        ch.close();
    }

    /**
     * 本地图片的文件流
     *
     * @param filename 本地文件地址
     * @return
     */
    private byte[] fileToByte(String filename) {
        byte[] b = null;
        BufferedInputStream is = null;
        try {
            System.out.println("开始>>>>" + System.currentTimeMillis());
            File file = new File(filename);
            b = new byte[(int) file.length()];
            is = new BufferedInputStream(new FileInputStream(file));
            is.read(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return b;
    }
}
