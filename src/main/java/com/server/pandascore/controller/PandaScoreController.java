package com.server.pandascore.controller;


import com.server.pandascore.service.pandaScore.PandaScoreCrawling;
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

    @PostMapping("/setAllMatch")
    public ResponseEntity<?> setAllMatch(){
        pandaScoreCrawling.getAllMatchList();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/setAllTeam")
    public ResponseEntity<?> setAllTeam(){
        pandaScoreCrawling.getAllTeamList();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/setAllChampion")
    public ResponseEntity<?> setAllChampion(){
        pandaScoreCrawling.getChampionList();
        return ResponseEntity.ok().build();
    }
}
