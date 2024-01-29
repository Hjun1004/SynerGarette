package com.ll.synergarette.boundedContext.delivery.service;

import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.delivery.entity.DeliveryForm;
import com.ll.synergarette.boundedContext.delivery.repository.DeliveryRepository;
import com.ll.synergarette.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
