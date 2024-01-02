package com.ll.synergarette.boundedContext.payment.entity;

import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.payment.PayType;
import com.ll.synergarette.boundedContext.payment.dto.PaymentResDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PaymentId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private Long amount;

    private String orderName;

    private String orderId;

    private boolean paySuccessYN;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Member customer;

    @Column
    private String paymentKey;
    @Column
    private String failReason;

    @Column
    private boolean cancelYN;

    @Column
    private String cancelReason;

    @CreatedDate
    private LocalDateTime createDate;

    public PaymentResDto toPaymentResDto() {
        return PaymentResDto.builder()
                .payType(payType.getDescription())
                .amount(amount)
                .orderName(orderName)
                .orderId(orderId)
                .customerEmail(customer.getEmail())
                .customerName(customer.getUsername())
                .createdAt(createDate.toString())
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }



}
