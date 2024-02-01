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


        myPage.setDeliveryAddress(deliveryAddress);

        return RsData.of("S-1", "배송지가 설정되었습니다.", myPage);
    }
}
