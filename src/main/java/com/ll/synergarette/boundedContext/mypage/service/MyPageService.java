package com.ll.synergarette.boundedContext.mypage.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    @Transactional
    public RsData<MyPage> setNowAddress(Member member, DeliveryAddress deliveryAddress) {
        MyPage myPage = member.getMyPage();

        System.out.println("지금 주소 설정 중이에요");

        if(myPage.isEmpty()){
            System.out.println("마이페이지 비었어요");
        }

        myPage.setDeliveryAddress(deliveryAddress);

        return RsData.of("S-1", "배송지가 설정되었습니다.", myPage);
    }
}
