package com.server.pandascore.controller;

import com.server.pandascore.service.pandaScore.PandaScoreCrawling;
import com.server.pandascore.service.slack.SlackNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PandaScoreController {

    private final SlackNotifyService slackNotifyService;
    private final PandaScoreCrawling pandaScoreCrawling;

    @PostMapping("/setUpcomingMatch")
    public void setAfterMatch(){
        slackNotifyService.sendMessage("AfterMatch is started");
        pandaScoreCrawling.getUpcomingMatchList();
    }

    @PostMapping("/setAllLeague")
    public void setAllLeague(){
        slackNotifyService.sendMessage("setAllLeague start");
        pandaScoreCrawling.getLeagueList();
    }
    @PostMapping("/setMatch")
    public void setMatch(@RequestParam Long leagueId){
        slackNotifyService.sendMessage("setMatch start");
        pandaScoreCrawling.getCurrentMatchListByLeagueId(leagueId);
    }

    @PostMapping("/setAllMatch")
    public void setAllMatch(){
        slackNotifyService.sendMessage("setAllMatch start");
        pandaScoreCrawling.getAllMatchList();
    }

    @PostMapping("/setAllTeam")
    public void setAllTeam(){
        slackNotifyService.sendMessage("setAllTeam start");
        pandaScoreCrawling.getAllTeamList();
    }
    @PostMapping("/setAllChampion")
    public void setAllChampion(){
        slackNotifyService.sendMessage("setAllChampion start");
        pandaScoreCrawling.getChampionList();
    }

    @PostMapping("/setAllMatchByLeagueId")
    public void setAllMatchByLeagueId(@RequestParam(value = "leagueId") Long leagueId){
        slackNotifyService.sendMessage("setAllMatchByLeagueId start");
        pandaScoreCrawling.getMatchListByLeagueId(leagueId);
    }

    @PostMapping("/setLiveMatch")
    public void setLiveMatch(){
        log.info("setLiveMatch start");
        pandaScoreCrawling.getLiveMatchList();
    }
}

