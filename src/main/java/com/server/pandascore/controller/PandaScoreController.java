package com.server.pandascore.controller;


import com.server.pandascore.service.pandaScore.PandaScoreCrawling;
import com.server.pandascore.service.slack.SlackNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PandaScoreController {

    private final SlackNotifyService slackNotifyService;
    private final PandaScoreCrawling pandaScoreCrawling;

    @PostMapping("/setAllLeague")
    public void setAllLeague(){
        slackNotifyService.sendMessage("setAllLeague start");
        pandaScoreCrawling.getLeagueList();
        slackNotifyService.sendMessage("setAllLeague end");
    }

    @PostMapping("/setAllMatch")
    public void setAllMatch(){
        slackNotifyService.sendMessage("setAllMatch start");
        pandaScoreCrawling.getAllMatchList();
        slackNotifyService.sendMessage("setAllMatch end");
    }
    @PostMapping("/setAllTeam")
    public void setAllTeam(){
        slackNotifyService.sendMessage("setAllTeam start");
        pandaScoreCrawling.getAllTeamList();
        slackNotifyService.sendMessage("setAllTeam end");
    }
    @PostMapping("/setAllChampion")
    public void setAllChampion(){
        slackNotifyService.sendMessage("setAllChampion start");
        pandaScoreCrawling.getChampionList();
        slackNotifyService.sendMessage("setAllChampion end");
    }

    @PostMapping("/setAllMatchByLeagueId")
    public void setAllMatchByLeagueId(@RequestParam(value = "leagueId") Long leagueId){
        slackNotifyService.sendMessage("setAllMatchByLeagueId start");
        pandaScoreCrawling.getMatchListByLeagueId(leagueId);
        slackNotifyService.sendMessage("setAllMatchByLeagueId end");
    }
}
