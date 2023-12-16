package com.ll.synergarette.boundedContext.goods.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.entity.GoodsCreateForm;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/goods")
@PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
public class AdminGoodsController {

    private final Rq rq;
    private final MemberService memberService;

    private final GoodsService goodsService;


//    @GetMapping("/create")
//    public String createGoods(){
//        return "adm/goods/create";
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    // @Valid를 붙여야 createGoods.java내의 NotBlank나 Size가 동작한다.
    public String createGoods(GoodsCreateForm goodsCreateForm) {

        return "adm/goods/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    // @Valid QuestionForm questionForm
    // questionForm 값을 바인딩 할 때 유효성 체크를 해라!
    // questionForm 변수와 bindingResult 변수는 model.addAttribute 없이 바로 뷰에서 접근할 수 있다.
    public String createGoods(@Valid GoodsCreateForm goodsCreateForm,
                              BindingResult bindingResult, Principal principal, @RequestBody String htmlContent){
        if(bindingResult.hasErrors()){
            return "adm/goods/create";
        }

        Goods goods = goodsService.createGoodsItem(goodsCreateForm.getGoodsName(), goodsCreateForm.getGoodsPrice(), goodsCreateForm.getGoodsDetail(), htmlContent);


        RsData rsdata = RsData.of("S-1", "상품 등록 성공");

        return rq.redirectWithMsg("/adm/home/main", rsdata.getMsg());
    }

}
