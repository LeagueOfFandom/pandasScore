package com.server.pandascore.service.pandaScore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.properties.Tokens;
import com.server.pandascore.service.slack.SlackNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class PandaScoreApi {

    private final Tokens tokens;
    private final SlackNotifyService slackNotifyService;

    private Long currentMillis = System.currentTimeMillis();
    private Long limitMillis = 500L;


    private void checkTime() {

        if (System.currentTimeMillis() - currentMillis > limitMillis) {
            currentMillis = System.currentTimeMillis();
        } else {
            try {
                Thread.sleep(limitMillis - (System.currentTimeMillis() - currentMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public HttpEntity<String> setHeaders() {
        checkTime();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokens.getPandascore());

        return  new HttpEntity<>(headers);
    }

    public ResponseEntity<?> getResponse(String url, Object classType) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = null;

        for(int i = 0; i < 10; i++) {
            try {
                response = restTemplate.exchange(url, HttpMethod.GET, setHeaders(), classType.getClass());
                log.info("response : " + response);
                return response;
            } catch (Exception e) {
                log.error(i + "번 " + url + e.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        slackNotifyService.sendMessage("pandascore api error"+ url);
        return null;
    }
    public ResponseEntity<MatchDto[]> getLiveMatchList(){
        String url = "https://api.pandascore.co/lol/matches/running";
        return (ResponseEntity<MatchDto[]>) getResponse(url, new MatchDto[0]);
    }
    public ResponseEntity<LeagueListDto[]> getLeagueListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/leagues?per_page=" + pageSize + "&page=" + page;
        return (ResponseEntity<LeagueListDto[]>) getResponse(url, new LeagueListDto[0]);
    }

    public ResponseEntity<ChampionDto[]> getChampionListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/champions?per_page=" + pageSize + "&page=" + page;
        return (ResponseEntity<ChampionDto[]>) getResponse(url, new ChampionDto[0]);
    }

    public ResponseEntity<MatchDto[]> getMatchListByLeagueIdAndPageSizeAndPage(Long leagueId, int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/matches?filter[league_id]=" + leagueId + "&per_page=" + pageSize + "&page=" + page;
        return (ResponseEntity<MatchDto[]>) getResponse(url, new MatchDto[0]);
    }

    public ResponseEntity<GameDto> getGameByGameId(Long gameId) {
        String url = "https://api.pandascore.co/lol/games/" + gameId;
        return (ResponseEntity<GameDto>) getResponse(url, new GameDto());
    }

    public ResponseEntity<TeamDto[]> getTeamBySeriesId(Long seriesId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams";
        return (ResponseEntity<TeamDto[]>) getResponse(url, new TeamDto[0]);
    }

    public ResponseEntity<TeamsDetailDto> getTeamDetailBySeriesAndTeamId(Long seriesId,Long teamId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams/" + teamId + "/stats";
        return (ResponseEntity<TeamsDetailDto>) getResponse(url, new TeamsDetailDto());
    }
}
