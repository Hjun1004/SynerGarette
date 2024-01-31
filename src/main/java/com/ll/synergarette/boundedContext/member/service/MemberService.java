package com.ll.synergarette.boundedContext.member.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.repository.MemberRepository;
import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import com.ll.synergarette.boundedContext.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.datatransfer.Clipboard;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 아래 메서드들이 전부 readonly 라는 것을 명시, 나중을 위해
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final MyPageRepository myPageRepository;


    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


    @Transactional
    public RsData<Member> join(String providetTypeCode, String username, String password){
        if(findByUsername(username).isPresent()) return RsData.of("F-1", "이미 존재 하는 ID입니다.");

        // 소셜 로그인을 통한 회원가입에서는 비번이 없다.
        if (StringUtils.hasText(password)) {
            password = passwordEncoder.encode(password);
        }

        Member member = Member
                .builder()
                .username(username)
                .password(password)
                .providerTypeCode(providetTypeCode)
                .build();

        MyPage myPage = MyPage
                .builder()
                .member(member)
                .build();


        memberRepository.save(member);

        member.setMyPage(myPage);

        myPageRepository.save(myPage);

//        System.out.println(myPage.isMemberEmpty());

        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }


    @Transactional
    public RsData<Member> join(String username, String password) {

        return join("common",username, password);
    }

    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username) {
        Optional<Member> opMember = findByUsername(username);

        if(opMember.isPresent()){
            return RsData.of("S-1", "로그인 되었습니다.", opMember.get());
        }

        return join(providerTypeCode,username, "");
    }
}
