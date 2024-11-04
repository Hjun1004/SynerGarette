package com.ll.synergarette.boundedContext.mypage.service;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberService memberService;

    private final Rq rq;

    private final HttpSession session;

    @Transactional
    public RsData<MyPage> setNowAddress(Member member, DeliveryAddress deliveryAddress) {
        MyPage myPage = member.getMyPage();

        myPage.setDeliveryAddress(deliveryAddress);

        /*
        if(rq.isLogin()){
            Member memberSession = memberService.findByUsername(member.getUsername()).get();

            session.setAttribute("member", memberSession);
        }*/

        return RsData.of("S-1", "배송지가 설정되었습니다.", myPage);
    }
}
