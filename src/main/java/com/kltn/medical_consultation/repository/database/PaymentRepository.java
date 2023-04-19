package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.vnpTxnRef = ?1")
    Payment findByVnpTxnRef(String vnpTxnRef);

    @Query("SELECT p FROM Payment p WHERE p.vnpBankTranNo = ?1")
    Payment findByVnpBankTranNo(String vnpBankTranNo);

}