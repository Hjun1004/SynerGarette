package com.ll.synergarette.boundedContext.goods.entity;

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
public class Goods {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(unique = true) // id 중복 안됨
    private String goodsName;

    private Long goodsPrice;

    private String goodsDetail;

    @OneToMany(mappedBy = "goodsItem", cascade = CascadeType.REMOVE)
    private List<Review> writtenReviewList; // 해당 상품에 써진 리뷰 리스트

}
