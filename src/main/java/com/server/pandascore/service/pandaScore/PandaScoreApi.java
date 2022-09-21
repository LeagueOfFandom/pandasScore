package com.server.pandascore.service.pandaScore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.properties.Tokens;
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

    public HttpEntity<String> setHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokens.getPandascore());

        return  new HttpEntity<String>(headers);
    }

    public ResponseEntity<LeagueListDto[]> getLeagueListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/leagues?per_page=" + pageSize + "&page=" + page;

        ResponseEntity<LeagueListDto[]> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), LeagueListDto[].class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return response;
    }

    public ResponseEntity<ChampionDto[]> getChampionListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/champions?per_page=" + pageSize + "&page=" + page;

        ResponseEntity<ChampionDto[]> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), ChampionDto[].class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return response;
    }

    public ResponseEntity<MatchDto[]> getMatchListByLeagueIdAndPageSizeAndPage(Long leagueId, int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/matches?filter[league_id]=" + leagueId + "&per_page=" + pageSize + "&page=" + page;

        ResponseEntity<MatchDto[]> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), MatchDto[].class);
        } catch (Exception e) {
            log.error(e.getMessage() + leagueId);
            return null;
        }
        return response;
    }

    public ResponseEntity<GameDto> getGameByMatchId(Long matchId) {
        String url = "https://api.pandascore.co/lol/games/" + matchId;

        ResponseEntity<GameDto> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), GameDto.class);
        } catch (Exception e) {
            log.error(e.getMessage() + matchId);
            return null;
        }
        return response;
    }

    public ResponseEntity<TeamDto[]> getTeamBySeriesId(Long seriesId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams";

        ResponseEntity<TeamDto[]> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), TeamDto[].class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return response;
    }

    public ResponseEntity<TeamsDetailDto> getTeamDetailBySeriesAndTeamId(Long seriesId,Long teamId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams/" + teamId + "/stats";

        ResponseEntity<TeamsDetailDto> response = null;
        try {
            response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), TeamsDetailDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return response;
    }
}
