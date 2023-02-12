package com.kltn.medical_consultation.repository.redis;

import com.kltn.medical_consultation.entities.redis.BlockIP;
import org.springframework.data.repository.CrudRepository;

public interface BlockIpRepository extends CrudRepository<BlockIP, String> {
}
