package com.ll.synergarette.boundedContext.order.repository;

import com.ll.synergarette.boundedContext.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT o FROM Order o JOIN FETCH o.member WHERE o.member.id = :memberId AND o.paidCheck = 1")
//    List<Order> findPaidOrdersByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT o FROM Order o JOIN FETCH o.member WHERE o.member.id = :memberId AND o.isPaid = true")
    List<Order> findPaidOrdersByMemberId(@Param("memberId") Long memberId);
}
