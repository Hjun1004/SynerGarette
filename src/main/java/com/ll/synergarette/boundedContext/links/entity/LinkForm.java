package com.ll.synergarette.boundedContext.links.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LinkForm {
    @NotBlank(message = "링크의 이름은 필수 입니다.")
    private String linksName;

    @NotBlank(message = "URL은 필수 입니다.")
    private String urlLinks;

    private String imageUrl;

    public void set(Links links){
        this.linksName = links.getLinksName();
        this.urlLinks = links.getUrlLinks();
    }
}
