package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.VnpayPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VnpayPaymentRepository extends JpaRepository<VnpayPayment, Long> {
    Optional<VnpayPayment> findByPayment_id(Long payment_id);
    List<VnpayPayment> findByPaymentId(Long paymentId);
}