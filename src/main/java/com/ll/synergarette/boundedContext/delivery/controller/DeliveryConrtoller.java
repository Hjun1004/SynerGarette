package com.ll.synergarette.boundedContext.delivery.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.service.DeliveryService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryConrtoller {
    private final DeliveryService deliveryService;
    private final Rq rq;
    @GetMapping("/addAddressForm")
    public String showAddAddressForm(DeliveryForm deliveryForm){
        return "usr/delivery/deliveryForm";
    }

    @PostMapping("/addAddressForm")
    public String showAddAddressForm(@Valid DeliveryForm deliveryForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "usr/delivery/deliveryForm";
        }

        Member member = rq.getMember();


        DeliveryAddress deliveryAddress = deliveryService.addDelivery(member, deliveryForm);

        return "/";
    }


}
