package com.zync.pools.ftp.client;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Ftp连接信息
 * @date 2020/2/11 15:21
 */
@Data
public class FtpInfo {
    /**
     * 默认端口
     */
    private static final int DEFAULT_FTP_PORT = 21;
    /**
     * 默认 buffer size
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 16;
    /**
     * ftp client连接超时时间，如果10秒以内没连上，就超时
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    /**
     * 默认ftp DataTimeout是5分钟，注意，不是说5分钟必须传完，是两个packet之间的时间间隔不能超过5分钟
     * 设这么大是因为原来是无限长，暂不敢改太小
     */
    private static final int DEFAULT_DATA_TIMEOUT = 300 * 1000;
    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private int port = DEFAULT_FTP_PORT;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * buffer size
     */
    private int bufSize = DEFAULT_BUFFER_SIZE;
    /**
     * 连接超时时间
     */
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    /**
     * DataTimeout
     */
    private int dataTimeout = DEFAULT_DATA_TIMEOUT;
    /**
     * 字符集
     */
    private Charset charset = DEFAULT_CHARSET;
    /**
     * 由FTP控件连接使用的字符编码
     */
    private String controlEncoding = DEFAULT_CHARSET.toString();

    private FtpInfo(Builder builder) {
        setHost(builder.host);
        setPort(builder.port);
        setUserName(builder.userName);
        setPassWord(builder.passWord);
        setBufSize(builder.bufSize);
        setConnectTimeout(builder.connectTimeout);
        setDataTimeout(builder.dataTimeout);
        setCharset(builder.charset);
        setControlEncoding(builder.controlEncoding);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String host;
        private int port = DEFAULT_FTP_PORT;
        private String userName;
        private String passWord;
        private int bufSize = DEFAULT_BUFFER_SIZE;
        private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private int dataTimeout = DEFAULT_DATA_TIMEOUT;
        private Charset charset = DEFAULT_CHARSET;
        private String controlEncoding = DEFAULT_CHARSET.toString();

        private Builder() {
        }

        public Builder host(String val) {
            host = val;
            return this;
        }

        public Builder port(int val) {
            port = val;
            return this;
        }

        public Builder userName(String val) {
            userName = val;
            return this;
        }

        public Builder passWord(String val) {
            passWord = val;
            return this;
        }

        public Builder bufSize(int val) {
            bufSize = val;
            return this;
        }

        public Builder connectTimeout(int val) {
            connectTimeout = val;
            return this;
        }

        public Builder dataTimeout(int val) {
            dataTimeout = val;
            return this;
        }

        public Builder charset(Charset val) {
            charset = val;
            return this;
        }

        public Builder controlEncoding(String val) {
            controlEncoding = val;
            return this;
        }

        public FtpInfo build() {
            return new FtpInfo(this);
        }
    }
}
