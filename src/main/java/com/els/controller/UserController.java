package com.els.controller;

import com.els.common.ResponseCode;
import com.els.exception.BusinessException;
import com.els.pojo.domain.User;
import com.els.pojo.dto.IdDto;
import com.els.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.els.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody IdDto<Long> idDto, HttpServletRequest request) {
        if (idDto == null || idDto.getId() == null || idDto.getId() == 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "参数为空");
        }
        User user = userService.getById(idDto.getId());
        // LOGIN_NAME_MIN_LEN. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        return ResponseEntity.ok(currentUser);
    }
}
