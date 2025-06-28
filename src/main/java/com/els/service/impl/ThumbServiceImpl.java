package com.els.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.common.ResponseCode;
import com.els.constant.ThumbConstant;
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
import org.springframework.data.redis.core.RedisTemplate;
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
        implements ThumbService {

    private final UserService userService;

    private final BlogService blogService;

    private final TransactionTemplate transactionTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean hasThumb(Long blogId, Long userId) {
        return redisTemplate.opsForHash().hasKey(ThumbConstant.USER_THUMB_KEY_PREFIX + userId, blogId.toString());
    }


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
                Boolean exists = this.hasThumb(blogId, loginUser.getId());
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

                boolean success = update && this.save(thumb);

                // 点赞记录存入 Redis
                if (success) {
                    redisTemplate.opsForHash().put(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(), blogId.toString(), thumb.getId());
                }
                // 更新成功才执行
                return success;

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
                Object thumbIdObj = redisTemplate.opsForHash().get(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogId.toString());
                if (thumbIdObj == null) {
                    throw new RuntimeException("用户未点赞");
                }
                Long thumbId = Long.valueOf(thumbIdObj.toString());
                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumb_num = thumb_num - 1")
                        .update();
                boolean success = update && this.removeById(thumbId);
                // 点赞记录从 Redis 删除
                if (success) {
                    redisTemplate.opsForHash().delete(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogId.toString());
                }
                return success;
            });
        }
    }

}




