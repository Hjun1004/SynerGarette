package com.ll.synergarette.base.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
public class OAuth2NaverUserInfo {
    private final Map<String, Object> attributes;

    public OAuth2NaverUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public String getId(){
        return (String) attributes.get("id");
    }
}
