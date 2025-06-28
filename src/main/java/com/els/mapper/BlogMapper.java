package com.els.mapper;

import com.els.pojo.domain.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author pengYuJun
* @description 针对表【tb_blog】的数据库操作Mapper
* @createDate 2025-06-28 15:02:53
* @Entity com.els.pojo.domain.Blog
*/
public interface BlogMapper extends BaseMapper<Blog> {
    void batchUpdateThumbCount(@Param("countMap") Map<Long, Long> countMap);
}





