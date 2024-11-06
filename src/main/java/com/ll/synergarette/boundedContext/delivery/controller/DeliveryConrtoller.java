package com.ll.synergarette.boundedContext.delivery.controller;


import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.service.DeliveryService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import com.ll.synergarette.boundedContext.mypage.repository.MyPageRepository;
import com.ll.synergarette.boundedContext.mypage.service.MyPageService;
import com.ll.synergarette.boundedContext.order.entity.Order;
import com.ll.synergarette.boundedContext.order.service.OrderService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DeliveryConrtoller {
    private final DeliveryService deliveryService;

    private final MemberService memberService;

    private final OrderService orderService;

    private final MyPageService myPageService;
    private final Rq rq;

    private final HttpSession session;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addAddressForm")
    public String showAddAddressForm(DeliveryForm deliveryForm){
        return "usr/delivery/deliveryForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addAddressForm")
    @ResponseBody
    public String showAddAddressForm(@Valid DeliveryForm deliveryForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "usr/delivery/deliveryForm";
        }

        Member member = rq.getMember();

        DeliveryAddress deliveryAddress = deliveryService.addDelivery(member, deliveryForm);

        Member memberSession = memberService.findByUsername(member.getUsername()).get();

        session.setAttribute("member", memberSession);

//        return rq.historyBack("배송지가 등록되었습니다.");
//        return rq.redirectWithMsg("/", "배송지가 등록되었습니다.");
        return "배송지가 등록되었습니다.";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addressList")
    public String addressList(Model model){
        Member member = rq.getMember();

//        log.info("member는 현재 : ", member.getUsername());
        System.out.println("member는 현재 : " + member.getUsername());
        System.out.println("member의 ID는 현재 : " + member.getId());

        List<DeliveryAddress> deliveryAddressList = member.getDeliveryAddressesList();

        if(deliveryAddressList.isEmpty()) System.out.println("현재 딜리버리 리스트는 비어있습니다.");

        if(!deliveryAddressList.isEmpty()){
            for(DeliveryAddress deliveryAddress : deliveryAddressList){
                System.out.println("수령인은 = " + deliveryAddress.getRecipient());
            }
        }

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

//        Member member = rq.getMember();
        Member member = rq.getMemberPersistenceContext();

        MyPage myPage = myPageService.setNowAddress(member, deliveryAddress).getData();

        return "배송지 지정 완료";
    }


}
