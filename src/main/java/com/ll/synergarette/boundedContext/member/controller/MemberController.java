package com.ll.synergarette.boundedContext.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import com.ll.synergarette.standard.util.Ut;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final Rq rq;


    @AllArgsConstructor
    @Getter
    public static class JoinForm {
        @NotBlank
        @Size(min = 4, max = 30)
        private final String username;
        @NotBlank
        @Size(min = 4, max = 30)
        private final String password;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/join")
    public String showJoin(){
        return "usr/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/join")
    public String join(@Valid JoinForm joinForm) {
        RsData<Member> joinRs = memberService.join(joinForm.getUsername(),  joinForm.getPassword());

        if(joinRs.isFail()) return rq.historyBack("회원가입에 실패했습니다.");

        return rq.redirectWithMsg("/", joinRs.getMsg());
    }

    @AllArgsConstructor
    @Getter
    public static class LoginForm {
        @NotBlank
        @Size(min = 4, max = 30)
        private final String username;
        @NotBlank
        @Size(min = 4, max = 30)
        private final String password;
    }



    @PreAuthorize("isAnonymous()")
    @GetMapping("/usr/member/login")
    public String showLogin(HttpServletRequest request, Model model){

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/usr/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "usr/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/member/mypage")
    public String showMyPage(Model model){
//        Optional<Member> member = memberService.findByUsername(rq.getMember());
//        if(!member.isPresent()){
//            return rq.historyBack("회원정보가 없습니다.");
//        }
        Member member = rq.getMember();

        model.addAttribute("member", member);

        return "usr/member/mypage";
    }

}
