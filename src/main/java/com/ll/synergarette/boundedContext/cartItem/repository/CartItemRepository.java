package com.ll.synergarette.boundedContext.cartItem.repository;

import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    List<CartItem> findAllByBuyerId(Long buyerId);

    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.goodsItem WHERE ci.member.id = :id")
    List<CartItem> findCartItemsWithGoodsByMemberId(@Param("id") Long id);

    List<CartItem> findAllByMemberId(Long id);

    CartItem findCartItemByGoodsItemIdAndMemberId(Long goodsId, Long memberId);

}
