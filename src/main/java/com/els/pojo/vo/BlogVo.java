package com.els.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author pengYuJun
 */
@Data
public class BlogVo {
      
    private Long id;  
  
    /**  
     * 标题  
     */  
    private String title;  
  
    /**  
     * 封面  
     */  
    private String cover;
  
    /**  
     * 内容  
     */  
    private String content;  
  
    /**  
     * 点赞数  
     */  
    private Integer thumbNum;
  
    /**  
     * 创建时间  
     */  
    private Date createTime;
  
    /**  
     * 是否已点赞  
     */  
    private Boolean hasThumb;  
  
}
