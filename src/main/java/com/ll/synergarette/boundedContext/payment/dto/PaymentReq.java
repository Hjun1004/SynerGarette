package com.ll.synergarette.boundedContext.payment.dto;

import com.ll.synergarette.boundedContext.payment.PayType;
import com.ll.synergarette.boundedContext.payment.entity.Payment;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReq {
    @NonNull
    private PayType payType;
    @NonNull
    private Long amount;
    @NonNull
    private String orderName;

    private String yourSuccessUrl;
    private String yourFailUrl;


    public Payment toEntity() {
        return Payment.builder()
                .payType(payType)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .build();
    }
}
