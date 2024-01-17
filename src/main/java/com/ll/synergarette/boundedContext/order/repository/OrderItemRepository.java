package com.ll.synergarette.boundedContext.order.repository;

import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
