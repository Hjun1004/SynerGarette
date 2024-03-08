package com.ll.synergarette.boundedContext.order.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.service.CartItemService;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.entity.OrderItem;
import com.ll.synergarette.boundedContext.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberService memberService;
    private final CartItemService cartItemService;

    private final OrderRepository orderRepository;


    @Transactional
    public RsData<Order> createFromCart(Member buyer) {
        List<CartItem> cartItemList = cartItemService.getItemsByBuyer(buyer);

        if(cartItemList.isEmpty()){
            return RsData.of("F-1", "장바구니에 상품이 없습니다.");
        }


        List<OrderItem> orderItemList = new ArrayList<>();

        cartItemList
                .stream()
                .map(CartItem::getGoodsItem)
                .forEach(goodsItem -> orderItemList.add(new OrderItem(goodsItem)));
        // 장바구니 상품들을 가져와서 order 아이템 리스트에 담는다.
// 이 부분 장바구니 삭제 안되게 하려고 잠시 3원 5일에 주석 처리 함
//        cartItemList.stream().forEach(cartItem -> cartItemService.deleteCartItem(cartItem));

        return create(buyer, orderItemList);
    }

    @Transactional
    public RsData<Order> createFromGoods(Goods goods, Member buyer) {
        Order order = Order
                .builder()
                .member(buyer)
                .paidCheck(0) // 이 부분이 0이면 결제 안한애들
                .build();

        order.addOrderItem(new OrderItem(goods));

        order.makeName();

        orderRepository.save(order);

        return RsData.of("S-1", "주문이 성공적으로 생성되었습니다.", order) ;
    }

    @Transactional
    public RsData<Order> create(Member buyer, List<OrderItem> orderItemList){
        Order order = Order
                .builder()
                .member(buyer)
                .paidCheck(0) // 이 부분이 0이면 결제 안한애들
                .build();

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        order.makeName();

        orderRepository.save(order);

        return RsData.of("S-1", "주문이 성공적으로 생성되었습니다.", order) ;
    }

    public Optional<Order> findForPrintById(Long id) {
        return findById(id);
    }

    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }

    public List<Order> findPaidOrdersByMemberId(Long id) {
        return orderRepository.findPaidOrdersByMemberId(id);
    }

    @Transactional
    public RsData payDone(Order order) {
        order.setPaymentDone();
        orderRepository.save(order);

        // 디버깅용 로그
        System.out.println("Order saved!");

        return RsData.of("S-1", "결제 성공");
    }

    public Order findByIdAndPaid(Long id) {
        return orderRepository.findByIdAndIsPaid(id, true);
    }

    public List<Order> findPaidOrders() {
        return orderRepository.findPaidOrders();
    }

    @Transactional
    public Order writeTrackingNumber(Order order, String trackingNumber) {
        order.setTrackingNumber(trackingNumber);

        return order;
    }


    public RsData deleteOrder(Order order) {
        if(order.isPaid()){
           return RsData.of("F-1", "결제가 되어있는 주문 입니다. 삭제하지 못하였습니다.", order);
        }

        orderRepository.delete(order);

        return RsData.of("S-1", "삭제되었습니다.", order);
    }


    @Transactional
    @Scheduled(fixedDelay = 1860 * 1000) // 31분 마다 실행 (단위: 밀리초) 1 * 1000 는 1초 // 31분 마다 만들어지고 30분이 지났는데 결제가 안된 주문들은 삭제
    public void deleteExpiredOrders() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime minusTime = currentTime.minusMinutes(30L);

        List<Order> expiredOrders = orderRepository.findByCreateDateBeforeAndIsPaid(minusTime, false);

        orderRepository.deleteAll(expiredOrders);
    }

    @Transactional
    public RsData deleteCartItem(Order order, Member buyer) {
        if(order.getOrderItemList().isEmpty()){
            return RsData.of("F-1", "결제한 상품이 없습니다.", order);
        }

        List<OrderItem> orderItemList = order.getOrderItemList();

        List<CartItem> cartItemList = new ArrayList<>();

        orderItemList
                .stream()
                .map(OrderItem::getGoods)
                .forEach(goods -> cartItemList.add(cartItemService.findCartItemByGoodsIdAndMemberId(goods.getId(), buyer.getId())));


        System.out.println("카트 아이템 찾아옴?");

        cartItemList.stream().map(CartItem::getGoodsItem).forEach(goods -> System.out.println(goods.getGoodsName()));

        for(CartItem nowCartItem : cartItemList){
            cartItemService.deleteCartItem(nowCartItem);
        }

//        cartItemList.stream().forEach(cartItem -> cartItemService.deleteCartItem(cartItem));


        return RsData.of("S-1", "삭제했습니다.");
    }


    @Transactional
    public RsData addDeliveryAddressForOrder(Member actor, Order order, DeliveryAddress deliveryAddress) {
        if(!actor.equals(deliveryAddress.getMember())){
            return RsData.of("F-1", "주문속의 계정과 결제의 계정이 다릅니다.");
        }

        order.setOrderDeliveryAddress(deliveryAddress);
        if(order.getOrderDeliveryAddress() != null){
            System.out.println("주문건에 배송지가 지정되었어요!!!!!!");
        }

        return RsData.of("S-1", "주문건의 배송지가 저장되었습니다.");
    }


}
