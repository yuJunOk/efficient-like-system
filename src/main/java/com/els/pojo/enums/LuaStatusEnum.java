package com.els.pojo.enums;

import lombok.Getter;

/**
 * @author pengYuJun
 */

@Getter
public enum LuaStatusEnum {  
    // 成功  
    SUCCESS(1L),  
    // 失败  
    FAIL(-1L),  
    ;  
  
    private final long value;  
  
    LuaStatusEnum(long value) {  
        this.value = value;  
    }  
}
