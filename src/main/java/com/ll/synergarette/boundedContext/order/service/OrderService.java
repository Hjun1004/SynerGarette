package com.ll.synergarette.boundedContext.order.service;

import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.service.CartItemService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberService memberService;
    private final CartItemService cartItemService;


    public Order createFromCart(Member member) {
        List<CartItem> cartItemList = cartItemService.getItemsByBuyer(member);

        List<OrderItem> orderItemList = new ArrayList<>();

        cartItemList.stream().map(e->e.getGoodsItem()).filter()
    }
}
