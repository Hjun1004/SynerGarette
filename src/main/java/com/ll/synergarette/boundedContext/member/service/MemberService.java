package com.ll.synergarette.boundedContext.member.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 아래 메서드들이 전부 readonly 라는 것을 명시, 나중을 위해
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;


    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


    public RsData<Member> join(String username, String password) {

        if(findByUsername(username).isPresent()) return RsData.of("F-1", "이미 존재 하는 ID입니다.");

        Member member = Member
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);

        return RsData.of("S-1", "회원가입이 완료되었습니다.");
    }
}
