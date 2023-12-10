package com.ll.synergarette.boundedContext.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
@PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
public class AdmHomeController {
    @GetMapping("")
    public String showIndex(){
        return "adm/home/main";
    }

}
