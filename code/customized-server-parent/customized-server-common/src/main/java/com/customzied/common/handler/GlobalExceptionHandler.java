package com.customzied.common.handler;

import com.customzied.common.constant.Const;
import com.customzied.common.exception.BaseMessage;
import com.customzied.common.exception.BaseResponseEntity;
import com.customzied.common.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @date 2023/11/1 10:45
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BizException Handler
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<BaseMessage> bizExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("BizException error, URI: {}", request.getRequestURI(), e);
        return BaseResponseEntity.builder(e.getStatus(), e.getMessage(), e.getDeveloperMessage(), e.toString());
    }

    /**
     * ServletRequestBindingException Handler
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<BaseMessage> servletRequestBindingExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("ServletRequestBindingException error, URI: {}", request.getRequestURI(), e);
        return BaseResponseEntity.builder(HttpStatus.BAD_REQUEST, "param is error", e.getMessage(), e.toString());
    }

    /**
     * MethodArgumentNotValidException Handler
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseMessage> methodArgumentNotValidExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("MethodArgumentNotValidException error, URI: {}", request.getRequestURI(), e);
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        // 按需重新封装需要返回的错误信息
        Map<String, String> invalidArguments = new HashMap<>(16);
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getFieldErrors()) {
            invalidArguments.put(error.getField(), error.getDefaultMessage());
        }
        return BaseResponseEntity.builder(HttpStatus.BAD_REQUEST, StringUtils.join(invalidArguments.values(), Const.SEMICOLON_CH), invalidArguments.toString(), e.toString());
    }

    /**
     * System Exception Handler
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseMessage> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Exception error, URI: {}", request.getRequestURI(), e);
        return BaseResponseEntity.builder(HttpStatus.INTERNAL_SERVER_ERROR, Const.GLOBAL_EXCEPTION_MESSAGE, e.getMessage(), e.toString());
    }

}
