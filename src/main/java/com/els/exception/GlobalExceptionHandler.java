package com.els.exception;

import com.els.common.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pengYuJun
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常处理
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessExceptionHandler(BusinessException e) {
        log.error("businessException", e);
        return new ResponseEntity<>(e.getCode(), null, e.getMessage());
    }

    /**
     * 运行异常处理
     * @param e 运行异常
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResponseEntity.failed(e.getMessage());
    }
}
