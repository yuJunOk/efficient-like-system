package com.els.constant;

/**
 * @author pengYuJun
 */
public interface UserConstant {

    /**
     * 盐值，混淆密码
     */
    String PASSWORD_SALT = "MyGoodPasswordSalt";

    /**
     * 用户登录态值
     */
    String USER_LOGIN_STATE = "userLoginState";

    // ---权限---
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

    /**
     * 检验账号是否包含特殊字符正则表达式
     */
    String VALID_LOGIN_NAME_PATTERN =  "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 账号最小长度
     */
    int LOGIN_NAME_MIN_LEN = 4;

    /**
     * 密码最小长度
     */
    int LOGIN_PWD_MIN_LEN = 8;

}
