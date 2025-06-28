package com.els.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.els.pojo.domain.Blog;
import com.els.service.BlogService;
import com.els.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author pengYuJun
* @description 针对表【tb_blog】的数据库操作Service实现
* @createDate 2025-06-28 15:02:53
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




