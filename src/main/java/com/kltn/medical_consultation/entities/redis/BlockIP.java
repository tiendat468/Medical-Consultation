package com.kltn.medical_consultation.entities.redis;

import com.kltn.medical_consultation.models.ShareConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash(ShareConstant.REDIS_HASH.BLOCK_IP)
@Data
public class BlockIP {
    @Id
    private String ip;
    private int counter = 0;
    private long blockTime;
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiredAt;
}
