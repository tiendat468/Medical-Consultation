package com.kltn.medical_consultation.entities.redis;

import com.kltn.medical_consultation.models.ShareConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash(ShareConstant.REDIS_HASH.TOKEN)
public class TokenDTO {
    @Id
    private String token;

    private Long userId;

    private String email;

    private int type;

    private String roleCode;

    private Integer tokenType;

    private String eventCode;

    private String eventSession;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private long expiredAt;
}
