package com.els.controller;

import com.els.common.ResponseEntity;
import com.els.pojo.dto.IdDto;
import com.els.service.ThumbService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("thumb")
public class ThumbController {  
    @Resource
    private ThumbService thumbService;
  
    @PostMapping("/do")
    public ResponseEntity<Boolean> doThumb(@RequestBody IdDto<Long> idDto, HttpServletRequest request) {
        Boolean success = thumbService.doThumb(idDto, request);
        return ResponseEntity.success(success);
    }

    @PostMapping("/undo")
    public ResponseEntity<Boolean> undoThumb(@RequestBody IdDto<Long> idDto, HttpServletRequest request) {
        Boolean success = thumbService.undoThumb(idDto, request);
        return ResponseEntity.success(success);
    }
}
