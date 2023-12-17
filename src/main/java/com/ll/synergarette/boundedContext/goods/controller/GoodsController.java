package com.ll.synergarette.boundedContext.goods.controller;

import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/list")
    public String showGoodsList(Model model){
        List<Goods> goodsList = goodsService.findAllGoods();

        model.addAttribute("goodsList" ,goodsList);

        return "usr/goods/list";
    }
}
