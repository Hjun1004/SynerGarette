package com.ll.synergarette.boundedContext.delivery.controller;


import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.service.DeliveryService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

        return rq.redirectWithMsg("/", "배송지가 등록되었습니다.");
    }

    @GetMapping("/addressList")
    public String addressList(Model model){
        Member member = rq.getMember();

        List<DeliveryAddress> deliveryAddressList = member.getDeliveryAddressesList();

        model.addAttribute("deliveryAddressList",deliveryAddressList);

        return "usr/delivery/addressList";
    }

    @PostMapping("/selectAddress")
    @ResponseBody
    public String selectAddress(@RequestParam("deliveryAddressId") Long deliveryAddressId){
        DeliveryAddress deliveryAddress = deliveryService.findById(deliveryAddressId).orElse(null);

        Member member = rq.getMember();

//        member.setNowDeliveryAddress(deliveryAddress);

        return "배송지 지정 완료";
    }

}
