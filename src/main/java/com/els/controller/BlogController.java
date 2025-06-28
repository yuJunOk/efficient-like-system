package com.els.controller;

import com.els.common.ResponseEntity;
import com.els.pojo.vo.BlogVo;
import com.els.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
