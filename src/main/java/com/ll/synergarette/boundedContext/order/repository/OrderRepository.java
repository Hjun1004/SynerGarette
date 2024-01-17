package com.ll.synergarette.boundedContext.order.repository;

import com.ll.synergarette.boundedContext.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
