package com.ll.synergarette.boundedContext.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    @GetMapping("/list")
    public String showGoodsList(){
        return "usr/goods/list";
    }
}
