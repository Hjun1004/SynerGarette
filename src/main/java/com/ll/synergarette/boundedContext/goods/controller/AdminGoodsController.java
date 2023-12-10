package com.ll.synergarette.boundedContext.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/goods")
@PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
public class AdminGoodsController {
    @GetMapping("/create")
    public String createGoods(){
        return "adm/goods/create";
    }

}
