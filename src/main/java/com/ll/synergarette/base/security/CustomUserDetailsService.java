package com.ll.synergarette.base.security;

import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CustomUserDetailsService implements UserDetailsService{
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username(%s) not found".formatted(username)));

        return new User(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());
    }
}
