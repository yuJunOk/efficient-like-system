package com.els.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.constant.ThumbConstant;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Lazy
    @Resource
    private ThumbService thumbService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
            List<Object> blogIdList = blogList.stream().map(blog -> blog.getId().toString()).collect(Collectors.toList());
            // 获取点赞
            List<Object> thumbList = redisTemplate.opsForHash().multiGet(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogIdList);
            for (int i = 0; i < thumbList.size(); i++) {
                if (thumbList.get(i) == null) {
                    continue;
                }
                blogIdHasThumbMap.put(Long.valueOf(blogIdList.get(i).toString()), true);
            }
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

        Boolean exist = thumbService.hasThumb(blog.getId(), loginUser.getId());
        blogVo.setHasThumb(exist);

        return blogVo;
    }
}




