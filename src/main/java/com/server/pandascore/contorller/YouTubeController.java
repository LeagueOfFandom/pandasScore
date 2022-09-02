package com.server.pandascore.contorller;

import com.server.pandascore.dto.videoDto.YouTubeDto;
import com.server.pandascore.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class YouTubeController {
    private final YouTubeService youTubeService;

    @GetMapping("youtube")
    public List<YouTubeDto> getYouTube(){
        log.info("getYouTube 종료");
        return youTubeService.getYouTubeList();
    }

}
