package com.server.pandascore.controller;


import com.server.pandascore.service.pandaScore.PandaScoreCrawling;
import com.server.pandascore.service.slack.SlackNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PandaScoreController {

    private final SlackNotifyService slackNotifyService;
    private final PandaScoreCrawling pandaScoreCrawling;

    @PostMapping("/setAllLeague")
    public ResponseEntity<?> setAllLeague(){
        slackNotifyService.sendMessage("setAllLeague start");
        pandaScoreCrawling.getLeagueList();
        slackNotifyService.sendMessage("setAllLeague end");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setAllMatch")
    public ResponseEntity<?> setAllMatch(){
        slackNotifyService.sendMessage("setAllMatch start");
        pandaScoreCrawling.getAllMatchList();
        slackNotifyService.sendMessage("setAllMatch end");
        return ResponseEntity.ok().build();
    }
    @PostMapping("/setAllTeam")
    public ResponseEntity<?> setAllTeam(){
        slackNotifyService.sendMessage("setAllTeam start");
        pandaScoreCrawling.getAllTeamList();
        slackNotifyService.sendMessage("setAllTeam end");
        return ResponseEntity.ok().build();
    }
    @PostMapping("/setAllChampion")
    public ResponseEntity<?> setAllChampion(){
        slackNotifyService.sendMessage("setAllChampion start");
        pandaScoreCrawling.getChampionList();
        slackNotifyService.sendMessage("setAllChampion end");
        return ResponseEntity.ok().build();
    }
}
