package com.ll.synergarette.boundedContext.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import com.ll.synergarette.boundedContext.order.exception.OrderIdNotMatchedException;
import com.ll.synergarette.boundedContext.order.service.OrderService;
import com.ll.synergarette.boundedContext.payment.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.crypto.Cipher.SECRET_KEY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final Rq rq;
    private final OrderService orderService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    private final TossPaymentConfig tossPaymentConfig;

    @Value("${custom.site.baseUrl}")
    private String baseUrl;

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
        model.addAttribute("baseUrl", baseUrl);

        return "usr/order/orderDetail";
    }

    @RequestMapping("/{id}/success")
    public String confirmPayment(@PathVariable Long id,
                                 @RequestParam String orderId,
                                 @RequestParam String paymentKey,
                                 @RequestParam Long amount,
                                 Model model
    ) throws Exception {
        Order order = orderService.findForPrintById(id).get();

        String SECRET_KEY = tossPaymentConfig.getTestSecretKey();

        long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

        if (id != orderIdInputed) {
            throw new OrderIdNotMatchedException();
        }

        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        Member actor = rq.getMember();
//        long restCash = memberService.getRestCash(actor);
//        long payPriceRestCash = order.calculatePayPrice() - amount;

//        if (payPriceRestCash > restCash) {
//            throw new OrderNotEnoughRestCashException();
//        }

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

//            orderService.payByTossPayments(order, payPriceRestCash);

            return rq.redirectWithMsg(
                    "/order/%d".formatted(order.getId()),
                    "%d번 주문이 결제처리되었습니다.".formatted(order.getId())
            );
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "order/fail";
        }

    }

}
