package com.ll.synergarette.boundedContext.order.entity;

import com.ll.synergarette.boundedContext.goods.entity.Goods;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private LocalDateTime payDate;

    private Long goodsPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

    public OrderItem(Goods goodsItem) {
        this.goods = goodsItem;
        this.goodsPrice = goodsItem.getGoodsPrice();
    }

}
