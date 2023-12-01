package com.ll.synergarette.boundedContext.goods.repository;

import com.ll.synergarette.boundedContext.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

}
