package com.ll.synergarette.boundedContext.home.controller;

import com.ll.synergarette.boundedContext.links.entity.Links;
import com.ll.synergarette.boundedContext.links.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LinksService linksService;
    @GetMapping("/")
    public String showMain(Model model){

        List<Links> linksList = linksService.findAll();

        if(!linksList.isEmpty()){
            model.addAttribute("linksList", linksList);
        }

        return "usr/hello/hello";
    }


}
