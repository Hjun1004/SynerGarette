package com.ll.synergarette.boundedContext.goods.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final Rq rq;

    private final GoodsService goodsService;

    @GetMapping("/list")
    public String showGoodsList(Model model){
        List<Goods> goodsList = goodsService.findAllGoods();

        model.addAttribute("goodsList" ,goodsList);

        return "usr/goods/list";
    }

    @GetMapping("/{id}")
    public String showGoodsItemInfo(@PathVariable Long id, Model model, HttpServletRequest request,
                                    HttpServletResponse response){
        Optional<Goods> opGoods = goodsService.findById(id);

        if(!opGoods.isPresent()) return rq.historyBack("찾으려는 상품이 없습니다.");

        model.addAttribute("goodsItem", opGoods.get());

        return "usr/goods/detail";
    }
}
