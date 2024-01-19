package com.ll.synergarette.boundedContext.order.service;

import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.service.CartItemService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import com.ll.synergarette.boundedContext.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    private final OrderRepository orderRepository;


    @Transactional
    public Order createFromCart(Member buyer) {
        List<CartItem> cartItemList = cartItemService.getItemsByBuyer(buyer);

        List<OrderItem> orderItemList = new ArrayList<>();

        cartItemList.stream().map(CartItem::getGoodsItem).forEach(goods -> orderItemList.add(new OrderItem(goods)));
        // 장바구니 상품들을 가져와서 order 아이템 리스트에 담는다.

        cartItemList.stream().forEach(cartItem -> cartItemService.deleteCartItem(cartItem));

        return create(buyer, orderItemList);
    }

    @Transactional
    public Order create(Member buyer, List<OrderItem> orderItemList){
        Order order = Order
                .builder()
                .member(buyer)
                .build();

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }
}
