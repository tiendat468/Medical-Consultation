package com.kltn.medical_consultation.repository.redis;

import com.kltn.medical_consultation.entities.redis.EmailOtpDTO;
import org.springframework.data.repository.CrudRepository;

public interface MailOtpRepository extends CrudRepository<EmailOtpDTO, String> {
}
