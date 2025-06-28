package com.els.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 调用返回类
 * @author pengYuJun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseEntity<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -3461717234455860399L;

    private long code;

    private T data;

    private String message;

    public ResponseEntity(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ResponseEntity(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public ResponseEntity(ResponseCode responseCode, T data, String message) {
        this.code = responseCode.getCode();
        this.message = message;
        this.data = data;
    }

    static public <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(ResponseCode.SUCCESS, data);
    }

    static public <T> ResponseEntity<T> failed(ResponseCode responseCode) {
        return new ResponseEntity<>(responseCode);
    }

    static public <T> ResponseEntity<T> failed(ResponseCode responseCode, String message) {
        return new ResponseEntity<>(responseCode, null, message);
    }

    static public <T> ResponseEntity<T> failed(String message) {
        return new ResponseEntity<>(ResponseCode.ERROR, null, message);
    }
}
