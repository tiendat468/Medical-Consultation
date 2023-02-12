package com.kltn.medical_consultation.repository.redis;

import com.kltn.medical_consultation.entities.redis.UserLoginDTO;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLoginDTO, String> {
}
