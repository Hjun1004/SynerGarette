package com.ll.synergarette.boundedContext.goods.service;


import com.ll.synergarette.base.rsData.RsData;
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


    @Transactional
    public RsData deleteGoods(Long id) {
        Goods goods = goodsRepository.findById(id).orElse(null);

        if(goods == null){
            return RsData.of("F-1", "상품을 찾지 못했습니다.");
        }

        String deleteGoodsName = goods.getGoodsName();

        goodsRepository.delete(goods);

        return RsData.of("S-1", deleteGoodsName + " 제품을 삭제하였습니다.");
    }
}
