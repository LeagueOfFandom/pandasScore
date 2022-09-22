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

        return  new HttpEntity<String>(headers);
    }

    public ResponseEntity<LeagueListDto[]> getLeagueListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/leagues?per_page=" + pageSize + "&page=" + page;

        ResponseEntity<LeagueListDto[]> response = null;
        Integer count = 0;
        while(true) {
            try {
                count++;
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), LeagueListDto[].class);
                log.info("getLeagueListByPageSizeAndPage : " + (System.currentTimeMillis() - start));
            } catch (Exception e) {
                if(count > 10){
                    slackNotifyService.sendMessage("getLeagueListByPageSizeAndPage 10번 실패 : (pageSize :"+ pageSize +", page :" +page + ")" + e.getMessage());
                    log.error(e.getMessage());
                    return null;
                }
                log.error(e.getMessage());
                return null;
            }
        }
    }

    public ResponseEntity<ChampionDto[]> getChampionListByPageSizeAndPage(int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/champions?per_page=" + pageSize + "&page=" + page;

        ResponseEntity<ChampionDto[]> response = null;
        Integer count = 0;
        while(true) {
            try {
                count++;
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), ChampionDto[].class);
                log.info("getChampionListByPageSizeAndPage : " + (System.currentTimeMillis() - start));
                return response;
            } catch (Exception e) {
                if(count > 10) {
                    slackNotifyService.sendMessage("getChampionListByPageSizeAndPage 10번 실패 : (pageSize :"+ pageSize +", page :" +page + ")" + e.getMessage());
                    log.error(e.getMessage());
                    return null;
                }
                log.error(e.getMessage());
                return null;
            }
        }
    }

    public ResponseEntity<MatchDto[]> getMatchListByLeagueIdAndPageSizeAndPage(Long leagueId, int pageSize, int page) {
        String url = "https://api.pandascore.co/lol/matches?filter[league_id]=" + leagueId + "&per_page=" + pageSize + "&page=" + page;

        ResponseEntity<MatchDto[]> response = null;
        while(true) {
            try {
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), MatchDto[].class);
                log.info("getMatchListByLeagueIdAndPageSizeAndPage : " + (System.currentTimeMillis() - start));
                return response;
            } catch (Exception e) {
                log.error(e.getMessage() + leagueId);
                return null;
            }
        }
    }

    public ResponseEntity<GameDto> getGameByMatchId(Long matchId) {
        String url = "https://api.pandascore.co/lol/games/" + matchId;

        ResponseEntity<GameDto> response = null;
        Integer count = 0;
        while(true) {
            try {
                count++;
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), GameDto.class);
                log.info("getGameByMatchId : " + (System.currentTimeMillis() - start));
                return response;
            } catch (Exception e) {
                if(count > 10) {
                    slackNotifyService.sendMessage("getGameByMatchId 10번 실패 : (matchId :" + matchId + ")" + e.getMessage());
                    return null;
                }
                log.error(e.getMessage() + matchId);
                log.info("getGameByMatchId 1초 후 재시도 : " + count + "번째");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                return null;
            }
        }
    }

    public ResponseEntity<TeamDto[]> getTeamBySeriesId(Long seriesId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams";

        ResponseEntity<TeamDto[]> response = null;
        Integer count = 0;
        while(true) {
            try {
                count++;
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), TeamDto[].class);
                log.info("getTeamBySeriesId : " + (System.currentTimeMillis() - start));
            } catch (Exception e) {
                if(count > 10) {
                    slackNotifyService.sendMessage("getTeamBySeriesId 10번 실패 : (seriesId : " + seriesId + ")" + e.getMessage());
                    return null;
                }
                log.error(e.getMessage() + " seriesId : "+ seriesId);
                log.info("getTeamBySeriesId 1초 후 재시도 : " + count + "번째");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                return null;
            }
        }
    }

    public ResponseEntity<TeamsDetailDto> getTeamDetailBySeriesAndTeamId(Long seriesId,Long teamId) {
        String url = "https://api.pandascore.co/lol/series/" + seriesId + "/teams/" + teamId + "/stats";

        ResponseEntity<TeamsDetailDto> response = null;
        Integer count = 0;
        while(true) {
            try {
                count++;
                Long start = System.currentTimeMillis();
                response = new RestTemplate().exchange(url, HttpMethod.GET, setHeaders(), TeamsDetailDto.class);
                log.info("getTeamDetailBySeriesAndTeamId : " + (System.currentTimeMillis() - start));
                return response;
            } catch (Exception e) {
                if(count > 10) {
                    slackNotifyService.sendMessage("getTeamDetailBySeriesAndTeamId 10번 실패 (seriesId :" + seriesId + ", teamId : " + teamId + ")" + e.getMessage());
                    return null;
                }
                log.error(e.getMessage() +" seriesId : "+ seriesId + " teamId : "+ teamId);
                log.info("getTeamDetailBySeriesAndTeamId 1초 후 재시도 : " + count + "번째");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                return null;
            }
        }
    }
}
