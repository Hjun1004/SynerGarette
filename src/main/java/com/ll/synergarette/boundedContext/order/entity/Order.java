package com.ll.synergarette.boundedContext.order.entity;

import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.payment.PayType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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


    private boolean isPaid; // 결제여부

    private boolean isCanceled; // 취소여부

    private boolean isRefunded; // 환불여부

    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);

        this.orderItemList.add(orderItem);
    }




    /*
    토스 페이먼츠 쓰면서 만들었던거

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private Long amount;

    private String orderName; // 상품 이름

    private String orderId;

    private boolean paySuccessYN;

    @Column
    private String paymentKey;

    @Column
    private String failReason;

    @Column
    private boolean cancelYN;

    @Column
    private String cancelReason;

    */


}
