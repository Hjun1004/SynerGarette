package com.ll.synergarette.boundedContext.cartItem.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.service.CartItemService;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final Rq rq;
    private final GoodsService goodsService;

    private final CartItemService cartItemService;

    @GetMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public String showCartItem(Model model){
        Member member = rq.getMember();

        List<CartItem> cartItemList = member.getCartItemList();

        model.addAttribute(cartItemList);

        return "usr/cartItem/cartItemList";
    }

    @GetMapping("/addCartItem/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addCartItem(@PathVariable Long id, Model model){
        Member member = rq.getMember();

        Optional<Goods> goods = goodsService.findById(id);
        Goods goodsItem = null;

        if(goods.isPresent()){
            goodsItem = goods.get();
        }

        RsData<CartItem> cartItemRsData = cartItemService.addCartItem(member, goodsItem);

        if(cartItemRsData.isSuccess()) model.addAttribute(cartItemRsData.getData());


        return rq.historyBack(cartItemRsData.getMsg());
    }


}
