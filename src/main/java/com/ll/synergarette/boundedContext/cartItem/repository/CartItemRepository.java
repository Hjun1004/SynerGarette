package com.ll.synergarette.boundedContext.cartItem.repository;

import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
