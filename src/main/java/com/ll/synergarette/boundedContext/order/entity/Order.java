package com.ll.synergarette.boundedContext.order.entity;

import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.payment.PayType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "goods_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime refundDate;

    private LocalDateTime payDate;

    private LocalDateTime cancelDate;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "order" ,  fetch = FetchType.LAZY, cascade = ALL,  orphanRemoval = true)
    List<OrderItem> orderItemList = new ArrayList<>();

    private String trackingNumber;

    private int paidCheck;

    private boolean isPaid; // 결제여부

    private boolean isCanceled; // 취소여부

    private boolean isRefunded; // 환불여부


    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);

        this.orderItemList.add(orderItem);

    }

    // 여기부터

    public void setIsPaid(boolean a){
        if(a){
            this.isPaid = true;
        }
    }

    public void setPaymentDone() {
        payDate = LocalDateTime.now();

        for (OrderItem orderItem : orderItemList) {
            orderItem.setPaymentDone();
        }

        isPaid = true;

        // 디버깅용 로그
        System.out.println("Payment Done. paidCheck set to 1");
    }
    // 여기까지 수정

    public Long totalPrice(){
        Long totalPrice = 0L;
        totalPrice = orderItemList.stream().mapToLong(e -> e.getGoodsPrice()).sum();

        return totalPrice;
    }

    public void makeName(){
        String name = orderItemList.get(0).getGoods().getGoodsName();

        if(orderItemList.size() > 1){
            name += "외 %d건".formatted(orderItemList.size() - 1);
        }

        this.name = name;
    }

}
