package com.ll.synergarette.boundedContext.member.entity;


import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(unique = true) // id 중복 안됨
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;


    @Column(unique = true) // email 중복 안됨
    private String email;

    @OneToMany(mappedBy = "writeUserId", cascade = CascadeType.REMOVE)
    List<Review> writeReviewList; // 멤버가 쓴 리뷰 목록

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<CartItem> cartItemList; // 장바구니 목록


    // 일반회원인지, 카카오로 가입한 회원인지, 구글로 가입한 회원인지
    private String providerTypeCode;


    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if (isAdmin()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

    public boolean isAdmin() {
        return "admin".equals(username);
    }
}
