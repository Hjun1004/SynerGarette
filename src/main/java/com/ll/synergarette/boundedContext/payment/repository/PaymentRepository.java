package com.ll.synergarette.boundedContext.payment.repository;

import com.ll.synergarette.boundedContext.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
