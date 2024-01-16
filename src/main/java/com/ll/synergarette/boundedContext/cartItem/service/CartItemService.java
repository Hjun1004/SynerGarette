package com.ll.synergarette.boundedContext.cartItem.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.repository.CartItemRepository;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public RsData<CartItem> addCartItem(Member member, Goods goodsItem) {
        CartItem cartItem = CartItem
                .builder()
                .member(member)
                .goodsItem(goodsItem)
                .build();

        cartItemRepository.save(cartItem);

        return RsData.of("S-1","장바구니에 추가되었습니다.", cartItem);
    }
}
