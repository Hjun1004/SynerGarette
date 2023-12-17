package com.ll.synergarette.base.initData;


import com.ll.synergarette.boundedContext.goods.entity.Goods;
import com.ll.synergarette.boundedContext.goods.service.GoodsService;
import com.ll.synergarette.boundedContext.member.entity.Member;
import com.ll.synergarette.boundedContext.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Value("${custom.security.oauth2.client.registration.kakao.devUserOauthId}")
    private String kakaoDevUserOAuthId;

    @Value("${custom.security.oauth2.client.registration.google.devUserOauthId}")
    private String googleDevUserOAuthId;

    @Value("${custom.security.oauth2.client.registration.naver.devUserOauthId}")
    private String naverDevUserOAUthId;
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            GoodsService goodsService
    ){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Member memberAdmin = memberService.join("admin", "1234").getData();
                Member memberUser1 = memberService.join("user1", "1234").getData();
                Member memberUser2 = memberService.join("user2", "1234").getData();

                Member memberByKakao = memberService.whenSocialLogin("KAKAO", "KAKAO__%s".formatted(kakaoDevUserOAuthId)).getData();
                Member memberByGoogle = memberService.whenSocialLogin("GOOGLE", "GOOGLE__%s".formatted(googleDevUserOAuthId)).getData();
                Member memberByNaver = memberService.whenSocialLogin("NAVER", "NAVER__%s".formatted(naverDevUserOAUthId)).getData();

                Goods firstGoodsItem = goodsService.createGoodsItem("첫 번째 상품", 10000L, "<h1>내용을 입력해 주세요.</h1><p>sadfsdaf</p><p>asdfasdf</p><p>asdfasdfsdafasf</p><p><br></p><p><br></p>");
                Goods secondGoodsItem = goodsService.createGoodsItem("두 번째 상품", 20000L, "<h1>두 번째 상품입니다.</h1><p>두 번째 소개할 상품은 이거에용.</p><p>asdfasdf</p><p><br></p><p><br></p>");
                Goods thirdGoodsItem = goodsService.createGoodsItem("세 번째 상품", 20000L, "<h1>세 번째 상품입니다.</h1><p>세 번째 소개할 상품은 이거에용.</p><p>asdfasdf</p><p><br></p><p><br></p>");
                Goods forthGoodsItem = goodsService.createGoodsItem("네 번째 상품", 20000L, "<h1>네 번째 상품입니다.</h1><p>세 번째 소개할 상품은 이거에용.</p><p>asdfasdf</p><p><br></p><p><br></p>");
                Goods fifthGoodsItem = goodsService.createGoodsItem("다섯 번째 상품", 20000L, "<h1>다섯 번째 상품입니다.</h1><p>세 번째 소개할 상품은 이거에용.</p><p>asdfasdf</p><p><br></p><p><br></p>");
                Goods sixthGoodsItem = goodsService.createGoodsItem("여섯 번째 상품", 20000L, "<h1>여섯 번째 상품입니다.</h1><p>세 번째 소개할 상품은 이거에용.</p><p>asdfasdf</p><p><br></p><p><br></p>");

            }
        };
    }
}
