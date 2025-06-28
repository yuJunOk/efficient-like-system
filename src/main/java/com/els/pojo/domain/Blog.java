package com.els.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author pengYuJun
 * @TableName tb_blog
 */
@TableName(value ="tb_blog")
@Data
public class Blog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

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
     * 更新时间
     */
    private Date updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}