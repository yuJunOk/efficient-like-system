package com.els.service;

import com.els.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_user】的数据库操作Service
* @createDate 2025-06-28 15:07:33
*/
public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getCurrentUser(HttpServletRequest request);
}
