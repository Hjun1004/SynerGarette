package com.ll.synergarette.boundedContext.links.controller;

import com.ll.synergarette.base.rq.Rq;
import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.links.entity.Links;
import com.ll.synergarette.boundedContext.links.service.LinksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/links")
@PreAuthorize("hasAuthority('admin')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
public class LinksController {

    private final LinksService linksService;
    private final Rq rq;

    @GetMapping("/registration")
    public String registerLink(){


        return "adm/links/registerPage";
    }
    @AllArgsConstructor
    @Getter
    public class RegisterLinkForm{
        @NotBlank
        private String linksName;

        @NotBlank
        private String urlLinks;

        public void set(Links links){
            this.linksName = links.getLinksName();
            this.urlLinks = links.getUrlLinks();
        }
    }

    @PostMapping("/registration")
    public String registerLink(@Valid RegisterLinkForm registerLinkForm, BindingResult bindingResult, Model model, Principal principal){
        if(bindingResult.hasErrors()){
            return "adm/links/registerPage";
        }

        RsData<Links> linksRsData = linksService.registrationLinks(registerLinkForm.linksName, registerLinkForm.urlLinks);

        if(!linksRsData.isSuccess()){
            return rq.historyBack("등록에 실패했습니다.");
        }

//        List<Links> linksList = linksService.findAll();
//        if(!linksList.isEmpty()){
//            model.addAttribute("linksList", linksList);
//        }

        return rq.redirectWithMsg("/", linksRsData.getMsg());
    }

    @GetMapping("delete/{id}")
    public String deleteLink(@PathVariable Long id){

        RsData<Links> checkLinks = linksService.findById(id);

        if(checkLinks.isFail()){
            return rq.historyBack(checkLinks.getMsg());
        }

        RsData deleteLinks = linksService.deleteLinks(checkLinks.getData());


        return rq.redirectWithMsg("/", deleteLinks.getMsg());
    }

    @GetMapping("/modify/{id}")
    public String modifyLink(@PathVariable Long id, RegisterLinkForm registerLinkForm){ //registerLinkForm 는 model.addAttribute없이도 view에 전송이 된다.
        RsData<Links> linksRsData = linksService.findById(id);

        if(linksRsData.isFail()) return rq.historyBack(linksRsData.getMsg());

        registerLinkForm.set(linksRsData.getData());

        return "adm/links/registerPage";
    }

    @PostMapping("/modify/{id}")
    public String modifyLink(@PathVariable Long id, @Valid RegisterLinkForm registerLinkForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "adm/links/registerPage";
        }

        RsData<Links> linksRsData = linksService.findById(id);

        RsData canModifyRsData = linksService.canModify(linksRsData.getData());

        if(canModifyRsData.isFail()) return rq.historyBack(canModifyRsData.getMsg());

        RsData<Links> modifiedLinks = linksService.modifyLinks(linksRsData.getData(), registerLinkForm.urlLinks, registerLinkForm.linksName);

        return rq.redirectWithMsg("/", modifiedLinks.getMsg());
    }



}
