package com.zync.nio.netty.demo.protocol;

/**
 * 说明：消息对象
 *
 * @author luoc 2015年11月5日
 */
public class ProtocolMsg {

    private ProtocolHeader protocolHeader = new ProtocolHeader();
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    /**
     *
     */
    public ProtocolMsg() {
        // TODO Auto-generated constructor stub
    }

    public ProtocolHeader getProtocolHeader() {
        return protocolHeader;
    }

    public void setProtocolHeader(ProtocolHeader protocolHeader) {
        this.protocolHeader = protocolHeader;
    }


}
