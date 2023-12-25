package com.ll.synergarette.boundedContext.goods.service;


import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.repository.GoodsRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.Param;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional
    public Goods createGoodsItem(String goodsName, Long goodsPrice, String goodsDetail) {
        Goods goods = Goods.builder()
                .goodsName(goodsName)
                .goodsPrice(goodsPrice)
                .goodsDetail(goodsDetail)
                .build();

        goodsRepository.save(goods);

        return goods;
    }

    public List<Goods> findAllGoods (){
        return goodsRepository.findAll();
    }

    public Optional<Goods> findById(Long id) {
        return goodsRepository.findById(id);
    }
}
