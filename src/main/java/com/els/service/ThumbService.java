package com.els.service;

import com.els.pojo.domain.Thumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.els.pojo.dto.IdDto;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_thumb】的数据库操作Service
* @createDate 2025-06-28 15:06:17
*/
public interface ThumbService extends IService<Thumb> {

    /**
     * 是否点赞
     * @param blogId
     * @param userId
     * @return
     */
    Boolean hasThumb(Long blogId, Long userId);

    /**
     * 点赞
     * @param idDto
     * @param request
     * @return {@link Boolean }
     */
    Boolean doThumb(IdDto<Long> idDto, HttpServletRequest request);

    /**
     * 取消点赞
     * @param idDto
     * @param request
     * @return {@link Boolean }
     */
    Boolean undoThumb(IdDto<Long> idDto, HttpServletRequest request);


}
