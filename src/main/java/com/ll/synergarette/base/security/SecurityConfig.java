package com.ll.synergarette.base.security;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final Rq rq;
    private RsData rsData;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .headers((header) -> header.addHeaderWriter(
                        new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                        )
                ))
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/usr/member/login")
//                                .loginProcessingUrl("/usr/member/login")
                                .successHandler(authenticationSuccessHandler) // 꼭 추가해주세요!
                )
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/usr/member/login")
                                .successHandler(authenticationSuccessHandler)
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/usr/member/logout")
                                .logoutSuccessUrl("/")
                )
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/tui-editor/image-upload"))
                        .disable()
                )
                .cors(AbstractHttpConfigurer::disable)

        ;

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
