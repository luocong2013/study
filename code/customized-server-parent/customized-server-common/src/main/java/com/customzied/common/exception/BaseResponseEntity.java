package com.customzied.common.exception;

import com.customzied.common.constant.Const;
import com.customzied.common.handler.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;

/**
 * 统一异常响应类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/1 11:11
 */
@Slf4j
public class BaseResponseEntity<T> extends ResponseEntity<T> {

    public BaseResponseEntity(T body, HttpStatusCode status) {
        super(body, status);
    }

    /**
     * 成功消息
     * @param message
     * @return
     */
    public static BaseResponseEntity<BaseMessage> ok(String message) {
        BaseMessage body = new BaseMessage.BaseMessageBuilder()
                .code(HttpStatus.OK.value())
                .userMessage(message)
                .developerMessage(message)
                .build();
        return builder(body, HttpStatus.OK);
    }

    /**
     * 构建 BaseResponseEntity<T>
     * @param status            HttpStatusCode
     * @param userMessage       描述信息（用户）
     * @param developerMessage  简要描述信息（开发者）
     * @param moreInfo          更多详细信息
     * @return BaseResponseEntity<BaseMessage>
     */
    public static BaseResponseEntity<BaseMessage> builder(HttpStatusCode status, String userMessage, String developerMessage, String moreInfo) {
        BaseMessage body = new BaseMessage.BaseMessageBuilder()
                .code(status.value())
                .userMessage(userMessage)
                .developerMessage(developerMessage)
                .moreInfo(moreInfo)
                .build();
        return builder(body, status);
    }

    /**
     * 构建 BaseResponseEntity<T>
     *
     * @param body       body
     * @param status     HttpStatusCode
     * @return BaseResponseEntity<T>
     * @param <T> body
     */
    public static <T> BaseResponseEntity<T> builder(T body, HttpStatusCode status) {
        return new BaseResponseEntity<>(body, status);
    }

    /**
     * 使用response输出JSON
     *
     * @param response
     */
    public void out(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding(Const.DEFAULT_CHARACTER_ENCODING);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(this.getStatusCode().value());
            out = response.getWriter();
            ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
            String msg = objectMapper.writeValueAsString(this.getBody());
            out.println(msg);
        } catch (Exception e) {
            log.error("输出JSON出错", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 使用response输出Exception
     *
     * @param response
     * @param e
     */
    public static void out(HttpServletResponse response, Exception e) {
        log.error("BaseResponseEntity out Exception", e);
        BaseMessage body = new BaseMessage.BaseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .userMessage(Const.GLOBAL_EXCEPTION_MESSAGE)
                .developerMessage(e.getMessage())
                .moreInfo(e.toString())
                .build();
        builder(body, HttpStatusCode.valueOf(body.getCode())).out(response);
    }

}
