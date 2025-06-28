package com.els.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.common.ResponseCode;
import com.els.exception.BusinessException;
import com.els.pojo.domain.User;
import com.els.service.UserService;
import com.els.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static com.els.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author pengYuJun
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2025-06-28 15:07:33
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        // 再查询，用户数据是否有效
        // todo: 校验用户是否合法
        try{
            Long userId = currentUser.getId();
            return this.getById(userId);
        }catch (Exception e){
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }
    }
}




