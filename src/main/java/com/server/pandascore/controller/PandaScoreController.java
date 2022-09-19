package com.server.pandascore.controller;


import com.server.pandascore.service.PandaScoreCrawling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PandaScoreController {

    private final PandaScoreCrawling pandaScoreCrawling;

    @PostMapping("/setAllLeague")
    public ResponseEntity<?> setAllLeague(){
        pandaScoreCrawling.getLeagueList();
        return ResponseEntity.ok().build();
    }
}
