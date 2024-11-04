package com.ll.synergarette.boundedContext.delivery.service;

import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.repository.DeliveryRepository;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.SimpleTimeZone;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryAddress addDelivery(Member member, DeliveryForm deliveryForm) {
        DeliveryAddress deliveryAddress = DeliveryAddress
                .builder()
                .member(member)
                .recipient(deliveryForm.getReceiverName())
                .addressName(deliveryForm.getAddressName())
                .phoneNumber(deliveryForm.getPhoneNumber())
                .postNumber(deliveryForm.getPostNumber())
                .address(deliveryForm.getAddress())
                .detailAddress(deliveryForm.getDetailAddress())
                .noted(deliveryForm.getNoted())
                .build();

        deliveryRepository.save(deliveryAddress);


        return deliveryAddress;
    }

    @Transactional
    public DeliveryAddress addDelivery_For_NotProd(Member member, String receiverName, String addressName, String phoneNumber, String postNumber, String address, String detailAddress, String noted) {
        DeliveryAddress deliveryAddress = DeliveryAddress
                .builder()
                .member(member)
                .recipient(receiverName)
                .addressName(addressName)
                .phoneNumber(phoneNumber)
                .postNumber(postNumber)
                .address(address)
                .detailAddress(detailAddress)
                .noted(noted)
                .build();

        deliveryRepository.save(deliveryAddress);

        return deliveryAddress;
    }

    public Optional<DeliveryAddress> findById(Long deliveryAddressId) {
        System.out.println(deliveryAddressId + "딜리버리 아이디!!!!!!!!");
        return deliveryRepository.findById(deliveryAddressId);
    }
}
