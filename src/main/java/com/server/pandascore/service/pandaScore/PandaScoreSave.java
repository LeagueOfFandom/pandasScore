package com.server.pandascore.service.pandaScore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.leagueDto.LeagueListDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.matchDto.sub.Game;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.entity.*;
import com.server.pandascore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PandaScoreSave {

    private String cloudFrontUrl = "https://d654rq93y7j8z.cloudfront.net";
    private final ChampionRepository championRepository;

    private final MatchRepository matchRepository;

    private final MatchDetailRepository matchDetailRepository;

    private final TeamRepository teamRepository;

    private final LeagueRepository leagueRepository;

    public List<Long> getAllLeagueIdList(){
        return leagueRepository.findAllId();
    }

    public List<Long> getAllLatestSeriesIdList(){
        List<Long> seriesIdList = leagueRepository.findAllLatestSeriesId();
        seriesIdList.remove(null);
        return seriesIdList;
    }

    public void TeamDetailSave(TeamsDetailDto teamsDetailDto){
        Long teamId = teamsDetailDto.getId();
        TeamEntity teamEntity = teamRepository.findById(teamId).orElse(null);
        if(teamEntity != null){
            teamEntity.setStatus(teamsDetailDto.getStatus());
            teamRepository.save(teamEntity);
        }
    }

    public void MatchDetailSave(GameDto gameDto){
        Long id = gameDto.getId();
        MatchDetailEntity matchDetailEntity = matchDetailRepository.findById(id).orElse(null);
        if(matchDetailEntity == null){
            matchDetailRepository.save(gameDto.toMatchDetailEntity());
        }
    }

    public void ChampionSave(ChampionDto championDto){
        Long id = championDto.getId();
        String championImgUrl = cloudFrontUrl + "/champion/" + championDto.getName() + "_0.jpg";
        ChampionEntity championEntity = championRepository.findById(id).orElse(null);
        if(championEntity == null) {
            championEntity = new ChampionEntity(id, championImgUrl);
            championRepository.save(championEntity);
        }
    }
    public void TeamSave(TeamDto teamDto, Long seriesId){
        Long id = teamDto.getId();
        TeamEntity teamEntity = teamRepository.findByIdAndSeriesId(id, seriesId);
        if(teamEntity == null) {
            teamRepository.save(teamDto.toTeamEntity(seriesId));
        }
    }
    public void LiveMatchUpdate(MatchDto matchDto){
        Long id = matchDto.getId();
        MatchEntity matchEntity = matchRepository.findById(id).orElse(null);
        if(matchEntity == null){
            matchRepository.save(matchDto.toMatchEntity());
        }else{
            for(int i = 0; i < matchDto.getGames().size(); i++){
                Game nowGame = matchDto.getGames().get(i);
                Game beforeGame = matchEntity.getGames().get(i);

                if(beforeGame.getStatus().equals("not_started") && nowGame.getStatus().equals("running")){
                    matchRepository.delete(matchEntity);
                    matchRepository.save(matchDto.toMatchEntity());
                }
                else if(beforeGame.getStatus().equals("running") && nowGame.getStatus().equals("finished")){

                }
            }
        }
    }

    public void MatchSave(MatchDto matchDto){
        Long id = matchDto.getId();
        MatchEntity matchEntity = matchRepository.findById(id).orElse(null);
        if(matchEntity == null){
            matchRepository.save(matchDto.toMatchEntity());
            //slack 알람 추가 필요.
        }
    }

    public void LeagueSave(LeagueListDto leagueListDto){
        Long id = leagueListDto.getId();
        LeagueEntity leagueEntity = leagueRepository.findById(id).orElse(null);

        if(leagueEntity == null){
            leagueRepository.save(leagueListDto.toEntity());
        }
    }

}
