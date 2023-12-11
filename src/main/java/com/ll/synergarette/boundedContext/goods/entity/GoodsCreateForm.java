package com.ll.synergarette.boundedContext.goods.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class GoodsCreateForm {
    @NotBlank(message = "제목은 필수항목입니다.") // 아래의 변수는 비어있으면 안된다.
    @Size(max = 65, message = "제목을 200자 이하로 설정해주세요.") // 최대 200까지 가능하다.
    private String goodsName;

    @Size(max = 100000, message = "제목을 100000자 이하로 설정해주세요.")
    private String goodsDetail;

    @NotNull(message = "가격은 필수항목입니다.")
    private Long goodsPrice;


}
