package com.server.pandascore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.matchDto.sub.Game;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.properties.Tokens;
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
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("PandaScoreCrawling is running");

        //getChampionList();
        //getTeamListBySerie(4763L);
        //getMatchListByLeagueId(293L);

        getLeagueList();
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

            page++;
        }while (response.getBody().length == pageSize);
        log.info("ChampionList is saved");
    }

}

