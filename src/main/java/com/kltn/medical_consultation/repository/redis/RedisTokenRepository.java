package com.kltn.medical_consultation.repository.redis;

import com.kltn.medical_consultation.entities.redis.TokenDTO;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<TokenDTO, String> {
}