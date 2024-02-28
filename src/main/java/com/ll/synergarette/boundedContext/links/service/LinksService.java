package com.ll.synergarette.boundedContext.links.service;

import com.ll.synergarette.base.rsData.RsData;
import com.ll.synergarette.boundedContext.links.entity.Links;
import com.ll.synergarette.boundedContext.links.repository.LinksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 아래 메서드들이 전부 readonly 라는 것을 명시, 나중을 위해
public class LinksService {

    private final LinksRepository linksRepository;



    enum Brand{
        youtube,
        melon,
        flo,
        spotify,
        appleMusic,
        genie,
        youtubeMusic,
        vibe,
        instagram,
        bugs
    }

    @Transactional
    public RsData<Links> registrationLinks(String linksName, String urlLinks) {

        String youtubeVideoCheck = youtubeVideoCheck(urlLinks);

        // 일단 이미지 url이 없으면 무조건 유튜브 썸네일로 표기하기로
        if(youtubeVideoCheck != null){
            return registrationLinks(linksName, urlLinks, youtubeVideoCheck);
        }

        return registrationLinks(linksName, urlLinks, null);
    }

    @Transactional
    public RsData<Links> registrationLinks(String linksName, String urlLinks, String imageUrl){

        String brandCheck = brandCheck(urlLinks);


        Links links = Links
                .builder()
                .linksName(linksName)
                .urlLinks(urlLinks)
                .imageUrl(imageUrl)
                .brand(brandCheck)
                .build();

        linksRepository.save(links);

        return RsData.of("S-1", "url저장 완료", links);
    }

    public List<Links> findAll() {
        return linksRepository.findAll();
    }

    private String youtubeVideoCheck(String urlLinks){

        if(urlLinks.contains("youtube.com")){
            if(urlLinks.contains("watch?v=")){
                String urls[]  = urlLinks.split("v=");

                if(urls[1] != null){

                    String youtubeThumbnail = "https://img.youtube.com/vi/" + urls[1] + "/0.jpg";

                    return youtubeThumbnail;
                }

            }
        }
        return null;
    }


    private String brandCheck(String urlLinks) {

        if(urlLinks.contains("youtube.com")){
            return "youtube";
        }else if(urlLinks.contains("instagram.com")){
            return "instagram";
        }else if(urlLinks.contains("melon.com")){
            return "melon";
        }else if(urlLinks.contains("music.apple.com")){
            return "appleMusic";
        }else if(urlLinks.contains("genie.co.kr")){
            return "genie";
        }else if(urlLinks.contains("spotify.com")){
            return "spotify";
        }else if(urlLinks.contains("music.youtube")){
            return "youtubeMusic";
        }else if(urlLinks.contains("music-flo")){
            return "flo";
        }else if(urlLinks.contains("vibe.naver.com")){
            return "vibe";
        }else if(urlLinks.contains("music.bugs")){
            return "bugs";
        }

        return null;
    }
}
