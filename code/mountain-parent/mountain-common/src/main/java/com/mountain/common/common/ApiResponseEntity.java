package com.mountain.common.common;

import com.mountain.common.handler.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.PrintWriter;

/**
 * api 响应
 *
 * @author luocong
 * @version 1.0
 * @since 2026/1/15 14:15
 **/
@Slf4j
public class ApiResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {

    public ApiResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public ApiResponseEntity(ApiResponse<T> body, HttpStatusCode status) {
        super(body, status);
    }

    public ApiResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    public ApiResponseEntity(ApiResponse<T> body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, headers, statusCode);
    }

    /**
     * 成功消息
     *
     * @param <T> 泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success() {
        return success("操作成功");
    }

    /**
     * 成功消息
     *
     * @param message 描述信息（用户）
     * @param <T>     泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success(String message) {
        return success(message, null);
    }

    /**
     * 成功消息
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success(T data) {
        return success(HttpStatus.OK.value(), data);
    }

    /**
     * 成功消息
     *
     * @param code 状态码
     * @param data 数据
     * @param <T>  泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success(int code, T data) {
        return success(code, HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 成功消息
     *
     * @param message 描述信息（用户）
     * @param data    数据
     * @param <T>     泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success(String message, T data) {
        return success(HttpStatus.OK.value(), message, data);
    }

    /**
     * 成功消息
     *
     * @param code    状态码
     * @param message 描述信息（用户）
     * @param data    数据
     * @param <T>     泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> success(int code, String message, T data) {
        return status(HttpStatus.OK, code, message, data);
    }

    /**
     * 状态消息
     *
     * @param status  Http状态码
     * @param code    状态码
     * @param message 描述信息（用户）
     * @param data    数据
     * @param <T>     泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> status(HttpStatusCode status, int code, String message, T data) {
        ApiResponse<T> body = new ApiResponse.ApiResponseBuilder<T>()
                .code(code)
                .message(message)
                .data(data)
                .build();
        return new ApiResponseEntity<>(body, status);
    }

    /**
     * 错误消息
     *
     * @param status           Http状态码
     * @param message          描述信息（用户）
     * @param developerMessage 简要描述信息（开发者）
     * @param moreInfo         更多详细信息
     * @param <T>              泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> error(HttpStatusCode status, String message, String developerMessage, String moreInfo) {
        return error(status, message, developerMessage, moreInfo, null);
    }

    /**
     * 错误消息
     *
     * @param status           Http状态码
     * @param message          描述信息（用户）
     * @param developerMessage 简要描述信息（开发者）
     * @param moreInfo         更多详细信息
     * @param data             数据
     * @param <T>              泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> error(HttpStatusCode status, String message, String developerMessage, String moreInfo, T data) {
        return builder(status, status.value(), message, developerMessage, moreInfo, data);
    }

    /**
     * 构建消息
     *
     * @param status           Http状态码
     * @param code             状态码
     * @param message          描述信息（用户）
     * @param developerMessage 简要描述信息（开发者）
     * @param moreInfo         更多详细信息
     * @param data             数据
     * @param <T>              泛型
     * @return 响应消息
     */
    public static <T> ApiResponseEntity<T> builder(HttpStatusCode status, int code, String message, String developerMessage, String moreInfo, T data) {
        ApiResponse<T> body = new ApiResponse.ApiResponseBuilder<T>()
                .code(code)
                .message(message)
                .developerMessage(developerMessage)
                .moreInfo(moreInfo)
                .data(data)
                .build();
        return new ApiResponseEntity<>(body, status);
    }

    /**
     * 使用response输出Exception
     *
     * @param response HttpServletResponse
     * @param e        Exception
     */
    public static void out(HttpServletResponse response, Exception e) {
        log.error("ApiResponseEntity out Exception", e);
        error(HttpStatus.INTERNAL_SERVER_ERROR, Const.GLOBAL_EXCEPTION_MESSAGE, e.getMessage(), e.toString()).out(response);
    }

    /**
     * 使用response输出JSON
     *
     * @param response HttpServletResponse
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

}
