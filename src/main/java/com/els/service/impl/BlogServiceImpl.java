package com.els.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.pojo.domain.Blog;
import com.els.pojo.domain.Thumb;
import com.els.pojo.domain.User;
import com.els.pojo.vo.BlogVo;
import com.els.service.BlogService;
import com.els.mapper.BlogMapper;
import com.els.service.ThumbService;
import com.els.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author pengYuJun
* @description 针对表【tb_blog】的数据库操作Service实现
* @createDate 2025-06-28 15:02:53
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

    @Resource
    private UserService userService;

    @Resource
    private ThumbService thumbService;

    @Override
    public BlogVo getBlogVoById(long blogId, HttpServletRequest request) {
        Blog blog = this.getById(blogId);
        User loginUser = userService.getCurrentUser(request);
        return this.getBlogVo(blog, loginUser);
    }

    private BlogVo getBlogVo(Blog blog, User loginUser) {
        BlogVo blogVo = new BlogVo();
        BeanUtil.copyProperties(blog, blogVo);

        if (loginUser == null) {
            return blogVo;
        }

        Thumb thumb = thumbService.lambdaQuery()
                .eq(Thumb::getUserId, loginUser.getId())
                .eq(Thumb::getBlogId, blog.getId())
                .one();
        blogVo.setHasThumb(thumb != null);

        return blogVo;
    }
}




