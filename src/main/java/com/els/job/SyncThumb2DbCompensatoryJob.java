package com.els.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.els.constant.ThumbConstant;
import com.els.utils.RedisKeyUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 定时将 Redis 中的临时点赞数据同步到数据库的补偿措施  
 *
 * @author pengYuJun
 */  
@Component
@Slf4j
public class SyncThumb2DbCompensatoryJob {
  
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
  
    @Resource  
    private SyncThumb2DbJob syncThumb2DbJob;
  
    @Scheduled(cron = "0 0 2 * * *")
    public void run() {
        log.info("开始补偿数据");
        Set<String> thumbKeys = redisTemplate.keys(RedisKeyUtil.getTempThumbKey("") + "*");

        if (CollUtil.isEmpty(thumbKeys)) {
            log.info("没有需要补偿的临时数据");
            return;
        }

        Set<String> needHandleDataSet = new HashSet<>();
        thumbKeys.stream().filter(ObjUtil::isNotNull).forEach(thumbKey -> needHandleDataSet.add(thumbKey.replace(ThumbConstant.TEMP_THUMB_KEY_PREFIX.formatted(""), "")));
        // 补偿数据
        for (String date : needHandleDataSet) {
            syncThumb2DbJob.syncThumb2DbByDate(date);
        }
        log.info("临时数据补偿完成");
    }
}
