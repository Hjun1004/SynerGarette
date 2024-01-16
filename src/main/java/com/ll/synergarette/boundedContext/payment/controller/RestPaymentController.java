package com.ll.synergarette.boundedContext.payment.controller;

import com.ll.synergarette.boundedContext.payment.dto.PaymentReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goods")
public class RestPaymentController {
    @PostMapping("/purchase")
    public String showGoodsItemPurchase(@Valid @RequestParam PaymentReq paymentReq){


        return "usr/goods/payment";

    }
}
