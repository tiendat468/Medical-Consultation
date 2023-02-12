package com.kltn.medical_consultation.entities.redis;

import com.kltn.medical_consultation.models.ShareConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash(ShareConstant.REDIS_HASH.USER_LOGIN)
public class UserLoginDTO {
    @Id
    private String token;

    private String otp;
    private int action;
    private int step;

    private String email;

    private Long otpCreatedAt;
    private Long expiredTimeOtp;
    private int counterResendOtp;
    private int counterOtpInvalid ;
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiredAt;

}
