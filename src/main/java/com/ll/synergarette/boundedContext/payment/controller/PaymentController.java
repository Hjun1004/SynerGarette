package com.ll.synergarette.boundedContext.payment.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.payment.config.TossPaymentConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/payment/goods")
public class PaymentController {
    private final GoodsService goodsService;

    private final TossPaymentConfig tossPaymentConfig;

    private final Rq rq;
    @GetMapping("/{id}")
    public String showGoodsItemInfo(@PathVariable Long id, Model model, HttpServletRequest request,
                                    HttpServletResponse response){
        Optional<Goods> opGoods = goodsService.findById(id);

        if(!opGoods.isPresent()) return rq.historyBack("찾으려는 상품이 없습니다.");

        model.addAttribute("goodsItem", opGoods.get());
        model.addAttribute("tossPaymentConfig", tossPaymentConfig);

        return "usr/goods/payment";

    }

    @PostMapping()
}
