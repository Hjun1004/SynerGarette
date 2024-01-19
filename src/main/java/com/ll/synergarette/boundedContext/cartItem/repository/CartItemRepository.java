package com.ll.synergarette.boundedContext.cartItem.repository;

import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    List<CartItem> findAllByBuyerId(Long buyerId);

    List<CartItem> findAllByMemberId(Long id);
}
