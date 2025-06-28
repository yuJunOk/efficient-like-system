package com.els.controller;

import com.els.common.ResponseEntity;
import com.els.pojo.domain.Blog;
import com.els.pojo.vo.BlogVo;
import com.els.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pengYuJun
 */
@RestController  
@RequestMapping("blog")  
public class BlogController {  
    
    @Resource  
    private BlogService blogService;  
  
    @GetMapping("/get")  
    public ResponseEntity<BlogVo> get(long blogId, HttpServletRequest request) {  
        BlogVo blogVo = blogService.getBlogVoById(blogId, request);  
        return ResponseEntity.success(blogVo);  
    }

    @GetMapping("/list")
    public ResponseEntity<List<BlogVo>> list(HttpServletRequest request) {
        List<Blog> blogList = blogService.list();
        List<BlogVo> blogVOList = blogService.getBlogVOList(blogList, request);
        return ResponseEntity.success(blogVOList);
    }
}
