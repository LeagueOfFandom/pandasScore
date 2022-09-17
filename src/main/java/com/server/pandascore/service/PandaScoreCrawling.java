package com.server.pandascore.service;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.matchDto.sub.Game;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PandaScoreCrawling implements ApplicationRunner {

    private final PandaScoreSave pandaScoreSave;
    private final PandaScoreApi pandaScoreApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("PandaScoreCrawling is running");

        //getChampionList();
        //getTeamListBySerie(4763L);
        //getMatchListByLeagueId(293L);
        getLeagueList();
        getAllTeamList();
    }

    public void getAllTeamList(){
        List<Long> seriesIdList = pandaScoreSave.getAllLatestSeriesIdList();
        for(Long seriesId : seriesIdList){
            getTeamListBySeriesId(seriesId);
        }
    }


    public void getTeamDetailBySeriesAndTeamId(Long seriesId, Long teamId) {

        ResponseEntity<TeamsDetailDto> response = null;
        try {
            response = pandaScoreApi.getTeamDetailBySeriesAndTeamId(seriesId, teamId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        pandaScoreSave.TeamDetailSave(response.getBody());
        log.info("TeamDetailList is saved");
    }
    public void getTeamListBySeriesId(Long seriesId){
        ResponseEntity<TeamDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = pandaScoreApi.getTeamBySeriesId(seriesId);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++) {
                pandaScoreSave.TeamSave(response.getBody()[i], seriesId);
                getTeamDetailBySeriesAndTeamId(seriesId, response.getBody()[i].getId());
            }
            page++;

        }while (response.getBody().length == pageSize);
        log.info("TeamList is saved");
    }
    public void getMatchDetailByMatchId(Long matchId){
        ResponseEntity<GameDto> response = null;
        try {
            response = pandaScoreApi.getGameByMatchId(matchId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        pandaScoreSave.MatchDetailSave(response.getBody());
        log.info("MatchDetail is saved");
    }

    public void getMatchListByLeagueId(Long leagueId) {

        ResponseEntity<MatchDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = pandaScoreApi.getMatchListByLeagueIdAndPageSizeAndPage(leagueId, pageSize, page);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++) {
                pandaScoreSave.MatchSave(response.getBody()[i]);
                for(Game game : response.getBody()[i].getGames()){
                    Long gameId = game.getId();
                    getMatchDetailByMatchId(gameId);
                }
            }
            page++;

        }while (response.getBody().length == pageSize);
        log.info("MatchList is saved");
    }

    public void getChampionList(){

        ResponseEntity<ChampionDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = pandaScoreApi.getChampionListByPageSizeAndPage(pageSize, page);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++)
                pandaScoreSave.ChampionSave(response.getBody()[i]);

            page++;

        }while (response.getBody().length == pageSize);
        log.info("ChampionList is saved");
    }

    public void getLeagueList(){

        ResponseEntity<LeagueListDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = pandaScoreApi.getLeagueListByPageSizeAndPage(100, page);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
            for (int i = 0; i < response.getBody().length; i++)
                pandaScoreSave.LeagueSave(response.getBody()[i]);
            page++;
        }while (response.getBody().length == pageSize);
        log.info("LeagueList is saved");
    }

}
