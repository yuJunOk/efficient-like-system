package com.els.pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class IdDto<T> implements Serializable {
    /**
     * id
     */
    private T id;

    @Serial
    private static final long serialVersionUID = 1L;
}
