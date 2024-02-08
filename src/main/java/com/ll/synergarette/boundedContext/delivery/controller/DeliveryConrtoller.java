package com.ll.synergarette.boundedContext.delivery.controller;


import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.service.DeliveryService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import com.ll.synergarette.boundedContext.mypage.repository.MyPageRepository;
import com.ll.synergarette.boundedContext.mypage.service.MyPageService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final MyPageService myPageService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addAddressForm")
    public String showAddAddressForm(DeliveryForm deliveryForm){
        return "usr/delivery/deliveryForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addAddressForm")
    public String showAddAddressForm(@Valid DeliveryForm deliveryForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "usr/delivery/deliveryForm";
        }

        Member member = rq.getMember();


        DeliveryAddress deliveryAddress = deliveryService.addDelivery(member, deliveryForm);

        return rq.redirectWithMsg("/", "배송지가 등록되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addressList")
    public String addressList(Model model){
        Member member = rq.getMember();

        List<DeliveryAddress> deliveryAddressList = member.getDeliveryAddressesList();

        model.addAttribute("deliveryAddressList",deliveryAddressList);

        return "usr/delivery/addressList";
    }

    @AllArgsConstructor
    @Getter
    public class DeliveryIdForm {
        @NotNull
        private final Long nowDelivery;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/selectAddress")
    @ResponseBody
    public String selectAddress(@Valid DeliveryIdForm deliveryIdForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "usr/delivery/addressList";
        }

        Long nowDeliveryId = deliveryIdForm.getNowDelivery();

        DeliveryAddress deliveryAddress = deliveryService.findById(nowDeliveryId).orElse(null);

        Member member = rq.getMember();

        MyPage myPage = myPageService.setNowAddress(member, deliveryAddress).getData();




        return "배송지 지정 완료";
    }



    @PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    @GetMapping("/management")
    public String showManagement(Model model){

        return "adm/delivery/management";
    }

}
