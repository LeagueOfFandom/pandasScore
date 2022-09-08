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

@Service
@Slf4j
@RequiredArgsConstructor
public class PandaScoreCrawling implements ApplicationRunner {
    private final Tokens tokens;
    private final Save save;
    private final LeagueRepository leagueRepository;
    private final TeamRankingRepository teamRankingRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("PandaScoreCrawling is running");

        //getChampionList();
        //getTeamListBySerie(4763L);
        //getMatchListByLeagueId(293L);

        //getLeagueList();
        getTeamRankingList("lpl", "Summer", "2022");
    }

    public HttpEntity<String> setHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokens.getPandascore());
        log.info(tokens.getPandascore());
        //ResponseEntity<String> response = restTemplate.exchange("https://api.pandascore.co/lol/leagues", HttpMethod.GET,requestEntity, String.class);

        return  new HttpEntity<String>(headers);
    }
    public void getTeamDetailBySerieAndTeamId(Long serieId, Long teamId) {
        String url = "https://api.pandascore.co/lol/series/" + serieId + "/teams/" + teamId + "/stats";

        ResponseEntity<TeamsDetailDto> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), TeamsDetailDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        save.TeamDetailSave(response.getBody());
        log.info("TeamDetailList is saved");
    }
    public void getTeamListBySerie(Long serieId){
        String url = "https://api.pandascore.co/lol/series/"+ serieId +"/teams";

        ResponseEntity<TeamDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {

                response = new RestTemplate().exchange(url + "?page[size]=" + pageSize + "&page[number]=" + page, HttpMethod.GET, setHeaders(), TeamDto[].class);

            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++) {
                save.TeamSave(response.getBody()[i]);
                getTeamDetailBySerieAndTeamId(serieId, response.getBody()[i].getId());
            }
            page++;

        }while (response.getBody().length == pageSize);
        log.info("TeamList is saved");
    }
    public void getMatchDetailByGameId(Long gameId){
        String url = "https://api.pandascore.co/lol/games/" + gameId;

        ResponseEntity<GameDto> response = null;

        try {

            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), GameDto.class);

        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        save.MatchDetailSave(response.getBody());
        log.info("MatchDetail is saved");
    }

    public void getMatchListByLeagueId(Long leagueId) {
        String url = "https://api.pandascore.co/lol/matches?filter[league_id]=" + leagueId;

        ResponseEntity<MatchDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {

                response = new RestTemplate().exchange(url + "&page[size]=" + pageSize + "&page[number]=" + page, HttpMethod.GET, setHeaders(), MatchDto[].class);

            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++) {
                save.MatchSave(response.getBody()[i]);
                for(Game game : response.getBody()[i].getGames()){
                    Long gameId = game.getId();
                    getMatchDetailByGameId(gameId);
                }
            }
            page++;

        }while (response.getBody().length == pageSize);
        log.info("MatchList is saved");
    }

    public void getChampionList(){
        String url = "https://api.pandascore.co/lol/champions";

        ResponseEntity<ChampionDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = new RestTemplate().exchange(url + "?page[size]=" + pageSize + "&page[number]=" + page, HttpMethod.GET, setHeaders(), ChampionDto[].class);
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
        String url = "https://api.pandascore.co/lol/leagues";
        ResponseEntity<LeagueListDto[]> response = null;

        final int pageSize = 100;
        int page = 1;
        do {
            try {
                response = new RestTemplate().exchange(url + "?page[size]=" + pageSize + "&page[number]=" + page, HttpMethod.GET, setHeaders(), LeagueListDto[].class);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

            for (int i = 0; i < response.getBody().length; i++)
                save.LeagueSave(response.getBody()[i]);
            page++;
        }while (response.getBody().length == pageSize);
        log.info("ChampionList is saved");
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

