package com.ll.synergarette.boundedContext.order.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import com.ll.synergarette.boundedContext.order.service.OrderService;
import com.ll.synergarette.boundedContext.payment.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final Rq rq;
    private final OrderService orderService;

    private final TossPaymentConfig tossPaymentConfig;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated")
    public String create(){
        Member member = rq.getMember();
        RsData<Order> rsOrder = orderService.createFromCart(member);

        if(rsOrder.isFail()){
            return rq.historyBack(rsOrder.getMsg());
        }

        Order order = rsOrder.getData();

        return rq.redirectWithMsg("/order/%d".formatted(order.getId()), "%d번 주문이 생성되었습니다.".formatted(order.getId()));
    }

    @GetMapping("/{id}")
    public String showOrderDetail(@PathVariable Long id, Model model){
        Order order = orderService.findForPrintById(id).orElse(null);

        if(order==null){
            return rq.historyBack("주문 상품을 찾을 수 없습니다.");
        }

        Member member = rq.getMember();

        List<OrderItem> orderItemList = order.getOrderItemList();

        model.addAttribute("orderItemList", orderItemList);


        model.addAttribute("order", order);
        model.addAttribute("member", member);
        model.addAttribute("tossPaymentConfig", tossPaymentConfig);

        return "usr/order/orderDetail";
    }

}
