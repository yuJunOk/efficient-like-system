package com.els.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<BlogVo> getBlogVOList(List<Blog> blogList, HttpServletRequest request) {
        User loginUser = userService.getCurrentUser(request);
        Map<Long, Boolean> blogIdHasThumbMap = new HashMap<>();
        if (ObjUtil.isNotEmpty(loginUser)) {
            Set<Long> blogIdSet = blogList.stream().map(Blog::getId).collect(Collectors.toSet());
            // 获取点赞
            List<Thumb> thumbList = thumbService.lambdaQuery()
                    .eq(Thumb::getUserId, loginUser.getId())
                    .in(Thumb::getBlogId, blogIdSet)
                    .list();

            thumbList.forEach(blogThumb -> blogIdHasThumbMap.put(blogThumb.getBlogId(), true));
        }

        return blogList.stream()
                .map(blog -> {
                    BlogVo blogVo = BeanUtil.copyProperties(blog, BlogVo.class);
                    blogVo.setHasThumb(blogIdHasThumbMap.get(blog.getId()));
                    return blogVo;
                })
                .toList();
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




