package com.ll.synergarette.boundedContext.member.service;

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



}
