package com.ll.synergarette.boundedContext.delivery.repository;

import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryAddress, Long> {
}
