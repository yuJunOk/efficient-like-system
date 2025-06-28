package com.els.pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author pengYuJun
 */
@Data
public class IdBatchDto<T> implements Serializable {
    /**
     * id
     */
    private List<T> idList;

    @Serial
    private static final long serialVersionUID = 1L;
}
