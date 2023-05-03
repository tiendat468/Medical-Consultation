package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.VnpayPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VnpayPaymentRepository extends JpaRepository<VnpayPayment, Long> {
    Optional<VnpayPayment> findByPaymentId(Long paymentId);
}