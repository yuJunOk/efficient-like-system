package com.els.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.common.ResponseCode;
import com.els.exception.BusinessException;
import com.els.pojo.domain.Blog;
import com.els.pojo.domain.Thumb;
import com.els.pojo.domain.User;
import com.els.pojo.dto.IdDto;
import com.els.service.BlogService;
import com.els.service.ThumbService;
import com.els.mapper.ThumbMapper;
import com.els.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
* @author pengYuJun
* @description 针对表【tb_thumb】的数据库操作Service实现
* @createDate 2025-06-28 15:06:17
*/
@Service
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
    implements ThumbService{

    private final UserService userService;

    private final BlogService blogService;

    private final TransactionTemplate transactionTemplate;

    @Override
    public Boolean doThumb(IdDto<Long> idDto, HttpServletRequest request) {
        if (idDto == null || idDto.getId() == null || idDto.getId() == 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        User loginUser = userService.getCurrentUser(request);
        // 加锁
        synchronized (loginUser.getId().toString().intern()) {
            // 编程式事务
            return transactionTemplate.execute(status -> {
                Long blogId = idDto.getId();
                boolean exists = this.lambdaQuery()
                        .eq(Thumb::getUserId, loginUser.getId())
                        .eq(Thumb::getBlogId, blogId)
                        .exists();
                if (exists) {
                    throw new RuntimeException("用户已点赞");
                }

                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumb_num = thumb_num + 1")
                        .update();

                Thumb thumb = new Thumb();
                thumb.setUserId(loginUser.getId());
                thumb.setBlogId(blogId);
                // 更新成功才执行
                return update && this.save(thumb);
            });
        }
    }

    @Override
    public Boolean undoThumb(IdDto<Long> idDto, HttpServletRequest request) {
        if (idDto == null || idDto.getId() == null || idDto.getId() == 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        User loginUser = userService.getCurrentUser(request);
        // 加锁
        synchronized (loginUser.getId().toString().intern()) {
            // 编程式事务
            return transactionTemplate.execute(status -> {
                Long blogId = idDto.getId();
                Thumb thumb = this.lambdaQuery()
                        .eq(Thumb::getUserId, loginUser.getId())
                        .eq(Thumb::getBlogId, blogId)
                        .one();
                if (thumb == null) {
                    throw new RuntimeException("用户未点赞");
                }
                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumb_num = thumb_num - 1")
                        .update();
                return update && this.removeById(thumb.getId());
            });
        }
    }

}




