package com.kltn.medical_consultation.repository.redis;

import com.kltn.medical_consultation.entities.redis.ResetPasswordDTO;
import org.springframework.data.repository.CrudRepository;

public interface ResetPasswordRepository extends CrudRepository<ResetPasswordDTO, String> {
}
