package com.server.pandascore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.matchDto.sub.Game;
import com.server.pandascore.dto.statsDto.StatDto;
import com.server.pandascore.dto.statsDto.sub.Total;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.entity.TeamRankingEntity;
import com.server.pandascore.properties.Tokens;
import com.server.pandascore.repository.LeagueRepository;
import com.server.pandascore.repository.TeamRankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PandaScoreCrawling implements ApplicationRunner {

    private final Save save;
    private final LeagueRepository leagueRepository;
    private final TeamRankingRepository teamRankingRepository;
    private final PandaScoreApi pandaScoreApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("PandaScoreCrawling is running");

        //getChampionList();
        //getTeamListBySerie(4763L);
        //getMatchListByLeagueId(293L);

        getTeamRankingList("lpl", "Summer", "2022");
        getLeagueList();
        getAllTeamList();
    }

    public void getAllTeamList(){
        List<Long> seriesIdList = save.getAllLatestSeriesIdList();
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
        save.TeamDetailSave(response.getBody());
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
                save.TeamSave(response.getBody()[i], seriesId);
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
        save.MatchDetailSave(response.getBody());
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
                save.MatchSave(response.getBody()[i]);
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
                save.ChampionSave(response.getBody()[i]);

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
                save.LeagueSave(response.getBody()[i]);
            page++;
        }while (response.getBody().length == pageSize);
        log.info("LeagueList is saved");
    }

    public void getTeamRankingList(String league, String season, String year){

        //Season 첫 글자는 대문자
        String fullName = season + " " + year.toString();
        String newStr = "$["+leagueRepository.findSeries(league, fullName).replaceAll("[^0-9]", "")+"].id";
        Long seriesId = leagueRepository.findSeriesId(league, newStr);
        log.info(seriesId.toString());

        String url = "https://api.pandascore.co/lol/series/"+seriesId.toString()+"/teams/stats";
        log.info(url);

        ResponseEntity<StatDto[]> response = null;
        try {
            response = new RestTemplate().exchange(url , HttpMethod.GET, setHeaders(), StatDto[].class);
            log.info(response.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        for (int i = 0; i < response.getBody().length; i++) {
            log.info(response.getBody()[i].getAcronym());
            Total totalStat = response.getBody()[i].getStats().getTotals();
            Double matchesWinRate = (double)totalStat.getMatches_won()/(double)totalStat.getMatches_played()*100;
            String matchesWinRateStr = Long.toString(Math.round(matchesWinRate)) + "%";
            Double gamesWinRate = (double)totalStat.getGames_won()/(double)totalStat.getGames_played()*100;
            String gamesWinRateStr = Long.toString(Math.round(gamesWinRate)) + "%";
            Long point = totalStat.getGames_won() - totalStat.getGames_lost();

            teamRankingRepository.save(new TeamRankingEntity(year, season, league, seriesId, response.getBody()[i].getAcronym(), totalStat, matchesWinRateStr, gamesWinRateStr, point));
        }

        log.info("getTeamRanking is saved");
    }

}

