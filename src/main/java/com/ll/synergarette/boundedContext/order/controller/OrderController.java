package com.ll.synergarette.boundedContext.order.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final Rq rq;
    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated")
    public String create(){
        Member member = rq.getMember();
        Order order = orderService.createFromCart(member);

        return rq.redirectWithMsg("/order/%d".formatted(order.getId()), "%d번 주문이 생성되었습니다.".formatted(order.getId()));
    }

}
