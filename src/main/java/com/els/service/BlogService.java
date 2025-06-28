package com.els.service;

import com.els.pojo.domain.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.els.pojo.vo.BlogVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author pengYuJun
* @description 针对表【tb_blog】的数据库操作Service
* @createDate 2025-06-28 15:02:53
*/
public interface BlogService extends IService<Blog> {

    /**
     * 根据id获取博客
     * @param blogId
     * @param request
     * @return
     */
    BlogVo getBlogVoById(long blogId, HttpServletRequest request);

    /**
     * 获取博客列表
     * @param blogList
     * @param request
     * @return
     */
    List<BlogVo> getBlogVOList(List<Blog> blogList, HttpServletRequest request);
}
