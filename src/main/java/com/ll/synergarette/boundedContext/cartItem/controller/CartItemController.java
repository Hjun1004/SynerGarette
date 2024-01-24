package com.ll.synergarette.boundedContext.cartItem.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.cartItem.entity.CartItem;
import com.ll.synergarette.boundedContext.cartItem.service.CartItemService;
import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

        List<CartItem> cartItemList = cartItemService.findCartItemsWithGoodsByMember(member.getId());

//        List<CartItem> cartItemList = member.getCartItemList();

        model.addAttribute(cartItemList);

        return "usr/cartItem/cartItemList";
    }

    @AllArgsConstructor
    @Getter
    public static class CheckItem {
        @NotBlank
        @Size(min = 3, max = 30)
        private final String username;
    }

    @PostMapping("/items/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteCartItem(@RequestParam(name = "selectCartItem", required = false) Long[] selectCartItems){
        if(selectCartItems != null){
            for(Long goodsId : selectCartItems){
                CartItem selectCartItem = cartItemService.findById(goodsId).get();

                cartItemService.deleteCartItem(selectCartItem);
            }
        }

        return rq.historyBack("삭제되었습니다.");
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
