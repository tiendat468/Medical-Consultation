package com.kltn.medical_consultation.domains;

import com.kltn.medical_consultation.entities.redis.TokenDTO;
import com.kltn.medical_consultation.repository.redis.RedisTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenStore {
    @Autowired
    RedisTokenRepository redisTokenRepository;

    @Value("${access-token.user.time-to-live:60}")
    long accessTokenTimeToLive;

    public Optional<TokenDTO> getInfoByToken(String token){
        return redisTokenRepository.findById(token);
    }

    public TokenDTO insertToken(String token, Long userId, String email){
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setUserId(userId);
        tokenDTO.setPhoneNumber(email);
        tokenDTO.setUserId(userId);
        tokenDTO.setExpiredAt(accessTokenTimeToLive);
        return redisTokenRepository.save(tokenDTO);
    }

    public void removeToken(TokenDTO tokenDTO){
        redisTokenRepository.delete(tokenDTO);
    }

}
