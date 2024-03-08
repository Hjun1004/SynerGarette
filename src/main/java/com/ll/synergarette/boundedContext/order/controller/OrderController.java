package com.ll.synergarette.boundedContext.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.member.controller.MemberController;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import com.ll.synergarette.boundedContext.order.exception.OrderIdNotMatchedException;
import com.ll.synergarette.boundedContext.order.service.OrderService;
import com.ll.synergarette.boundedContext.payment.config.TossPaymentConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;

import java.security.Principal;
import java.util.*;

import static javax.crypto.Cipher.SECRET_KEY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final Rq rq;
    private final OrderService orderService;

    private final GoodsService goodsService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    private final TossPaymentConfig tossPaymentConfig;

    @Value("${custom.site.baseUrl}")
    private String baseUrl;

    @GetMapping("/create/goods/{id}")
    @PreAuthorize("isAuthenticated")
    public String createForGoods(@PathVariable Long id){
        Member member = rq.getMember();

        Optional<Goods> goods = goodsService.findById(id);

        if(goods.isEmpty()){
            return rq.historyBack("선택하신 상품은 재고가 부족하거나 존재하지 않습니다.");
        }

        RsData<Order> rsOrder = orderService.createFromGoods(goods.get(), member);

        return rq.redirectWithMsg("/order/%d".formatted(rsOrder.getData().getId()), "%d번 주문이 생성되었습니다.".formatted(rsOrder.getData().getId()));
    }

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

        MyPage myPage = member.getMyPage();

        model.addAttribute("order", order);
        model.addAttribute("member", member);
        model.addAttribute("tossPaymentConfig", tossPaymentConfig);
        model.addAttribute("baseUrl", baseUrl);

        return "usr/order/orderDetail";
    }


    @GetMapping("/complete/{id}")
    public String orderComplete(@PathVariable Long id, Model model){

        Order order = orderService.findById(id).orElse(null);

        List<OrderItem> orderItemList = order.getOrderItemList();

        model.addAttribute("completeOrder", order);
        model.addAttribute("completeOrderItemList", orderItemList);

        return "usr/order/orderComplete";
    }

    @GetMapping("/completeOrder/{id}")
    public String completeOrderDetail(@PathVariable Long id, Model model){
        Member member = rq.getMember();
        Order order = orderService.findByIdAndPaid(id);

        Member buyer = order.getMember();

        model.addAttribute("buyer", buyer);
        model.addAttribute("completeOrder", order);
        model.addAttribute("orderDeliveryAddress", order.getOrderDeliveryAddress());

        return "/usr/order/completeOrderDetail";
    }

    @PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    @GetMapping("/management")
    public String showManagement(Model model){

        List<Order> paidOrderList = orderService.findPaidOrders();

        model.addAttribute("paidOrderList",paidOrderList);

        return "adm/order/management";
    }

    @AllArgsConstructor
    @Getter
    public class TrackingNumberForm{
        @NotBlank
        private String trackingNumber;
    }

    @PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    @GetMapping("/writeTrackingNumber/{id}")
    public String writeTackingNumber(@PathVariable Long id , Model model, TrackingNumberForm trackingNumberForm){
        Order order = orderService.findByIdAndPaid(id);

        model.addAttribute("order", order);

        return "adm/order/writeTrackingNumber";
    }

    @PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    @PostMapping("/writeTrackingNumber/{id}")
    @ResponseBody
    public String writeTrackingNumber(@Valid TrackingNumberForm trackingNumberForm, @PathVariable Long id ,BindingResult bindingResult, Model model, Principal principal){
        if(bindingResult.hasErrors()){
            return "adm/order/writeTrackingNumber";
        }

        Order order = orderService.findByIdAndPaid(id);

        order = orderService.writeTrackingNumber(order ,trackingNumberForm.trackingNumber);



        return "송장 번호 등록 -완-";
    }

    @GetMapping("/list")
    public String completeOrderList(Model model){
        Member member = rq.getMember();

        List<Order> paidOrderList = orderService.findPaidOrdersByMemberId(member.getId());

        model.addAttribute("paidOrderList", paidOrderList);

        return"/usr/order/paidList";
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

//            order.setPaid(true);
            System.out.println("이거 보이면 결제 성공");
            System.out.println("오더 이름은 = " + order.getName());

            RsData orderRsData = orderService.payDone(order);

            RsData addDeliveryForOrder = orderService.addDeliveryAddressForOrder(actor, order, order.getMember().getMyPage().getDeliveryAddress());

            RsData deleteCartItem = orderService.deleteCartItem(order, rq.getMember());

            // 디버깅용 로그
            System.out.println("setPaymentDone() called");

//            order.setPaymentDone();

//            return rq.redirectWithMsg(
//                    "/order/%d".formatted(order.getId()),
//                    "%d번 주문이 결제처리되었습니다.".formatted(order.getId())
//            );

            return rq.redirectWithMsg(
                    "/order/complete/%d".formatted(order.getId()),
                    "%d번 주문이 결제처리되었습니다.".formatted(order.getId())
            );
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());

            RsData deleteOrder = orderService.deleteOrder(order);

            return "order/fail";
        }

    }

}
