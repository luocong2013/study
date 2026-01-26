package com.mountain.common.handler;

import com.mountain.common.base.ApiResponseEntity;
import com.mountain.common.base.Const;
import com.mountain.common.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * global exception handler for controller
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/1 10:45
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle BizException
     *
     * @param request HttpServletRequest
     * @param e       BizException
     */
    @ExceptionHandler(value = BizException.class)
    public ApiResponseEntity<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.error("BizException error, URI: {}", request.getRequestURI(), e);
        return ApiResponseEntity.error(e.getStatus(), e.getMessage(), e.getDeveloperMessage(), e.toString());
    }

    /**
     * Handle ServletRequestBindingException
     *
     * @param request HttpServletRequest
     * @param e       ServletRequestBindingException
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ApiResponseEntity<Object> handleServletRequestBindingException(HttpServletRequest request, ServletRequestBindingException e) {
        log.error("ServletRequestBindingException error, URI: {}", request.getRequestURI(), e);
        return ApiResponseEntity.error(HttpStatus.BAD_REQUEST, "param is error", e.getMessage(), e.toString());
    }

    /**
     * Handle MethodArgumentNotValidException
     *
     * @param request HttpServletRequest
     * @param e       MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponseEntity<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException error, URI: {}", request.getRequestURI(), e);
        // 按需重新封装需要返回的错误信息
        Map<String, String> invalidArguments = new HashMap<>(16);
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : e.getFieldErrors()) {
            invalidArguments.put(error.getField(), error.getDefaultMessage());
        }
        return ApiResponseEntity.error(HttpStatus.BAD_REQUEST, StringUtils.join(invalidArguments.values(), Const.SEMICOLON_CH), invalidArguments.toString(), e.toString());
    }

    /**
     * Handle Exception
     *
     * @param request HttpServletRequest
     * @param e       Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {
        log.error("Exception error, URI: {}", request.getRequestURI(), e);
        return ApiResponseEntity.error(HttpStatus.INTERNAL_SERVER_ERROR, Const.GLOBAL_EXCEPTION_MESSAGE, e.getMessage(), e.toString());
    }

}
