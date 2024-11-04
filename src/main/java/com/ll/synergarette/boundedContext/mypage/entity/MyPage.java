package com.ll.synergarette.boundedContext.mypage.entity;

import com.ll.synergarette.boundedContext.delivery.entity.DeliveryAddress;
import com.ll.synergarette.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class MyPage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private DeliveryAddress deliveryAddress;

    public boolean isEmpty(){
        if(deliveryAddress == null) return true;

        return false;
    }


}
