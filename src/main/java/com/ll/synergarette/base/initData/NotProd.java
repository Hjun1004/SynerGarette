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
                Goods seventhGoodsItem = goodsService.createGoodsItem("일곱 번째 상품", 20000L, "<h1>헤더 1</h1><p>이건 그냥 텍스트</p><p><br></p><p><strong>이건 볼드 텍스트</strong></p><p><del>이건 줄그어진거</del></p>");
                Goods eighthGoodsItem = goodsService.createGoodsItem("여덟 번째 상품", 23500L, "<p>내용을 입력해 주세요.</p><table><thead><tr><th><p>1</p></th><th><p>2</p></th><th><p>3</p></th><th><p>4</p></th><th><p>5</p></th><th><p>6</p></th></tr></thead><tbody><tr><td><p>ㅂ</p></td><td><p>ㄷㅈ</p></td><td><p>ㄱ</p></td><td><p>ㅅ</p></td><td><p>ㅛ</p></td><td><p>ㅕ</p></td></tr><tr><td><p>ㅁ</p></td><td><p>ㅇ</p></td><td><p>ㅗ</p></td><td><p>ㅛㅗ</p></td><td><p>ㅈ쇼</p></td><td><p>ㅎㅇㄻ</p></td></tr><tr><td><p>ㅇ</p></td><td><p>ㅗ</p></td><td><p>ㄴ</p></td><td><p>ㅇ</p></td><td><p>ㅀ</p></td><td><p>ㅇㄹ</p></td></tr><tr><td><p>ㅋ</p></td><td><p>ㅁㅎ</p></td><td><p>ㅁ</p></td><td><p>ㅗ</p></td><td><p>로</p></td><td><p>ㅁ</p></td></tr></tbody></table><p>ㅇㄻㄴㄹ</p><p>ㅁㄴㅇ라ㅣㅓㅁ나ㅣ;;ㅇ러</p><p>ㅁㄴㅇ라ㅓ</p><p><br></p><h3>동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라만세 </h3><h3>무궁화 삼천리 화려강산</h3><h3>대한사람 대한으로 길이보전하세</h3><p><br></p><p><br></p><p><strong>남산위에 저 소나무 철갑을 두른 듯 바람서리 불변함은 우리기상일세</strong></p><p><strong>무궁화 삼천리 화려강산 </strong></p><p><strong>대한사람 대한으로 길이보전하세</strong></p><p><br></p><p>이 기상과 이 맘으로 충성을 다하여 </p><p><br></p><p><br></p>");

            }
        };
    }
}
