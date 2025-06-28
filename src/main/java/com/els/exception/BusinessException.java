package com.els.exception;

import com.els.common.ResponseCode;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author pengYuJun
 */
@Getter
public class BusinessException extends RuntimeException {

    private final long code;

    private final String message;

    public BusinessException(long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public BusinessException(ResponseCode responseCode, String message) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = message;
    }
}
