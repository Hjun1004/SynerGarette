package com.ll.synergarette.boundedContext.member.controller;

import com.ll.synergarette.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("member/join")
    public String showJoin(){
        return "usr/member/join";
    }

    @PostMapping("member/join")
    public String join(){
        return "redirect:/";
    }


    @GetMapping("usr/member/login")
    public String showLogin(){
        return "usr/member/login";
    }
}
