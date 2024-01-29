package com.ll.synergarette.boundedContext.delivery.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DeliveryForm {

    @NotBlank(message = "수령인은 필수항목입니다.") // 아래의 변수는 비어있으면 안된다.
    private String receiverName;

    private String addressName;

//    @NotBlank(message = "수령인은 필수항목입니다.") // 아래의 변수는 비어있으면 안된다.
    private String phoneNumber;

    @NotBlank(message = "우편번호는 필수항목입니다.") // 아래의 변수는 비어있으면 안된다.
    private String postNumber;

    @NotBlank(message = "주소는 필수항목입니다.") // 아래의 변수는 비어있으면 안된다.
    private String address;

    private String detailAddress;

    private String noted;
}
