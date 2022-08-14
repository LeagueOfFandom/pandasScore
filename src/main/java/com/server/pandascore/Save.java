package com.server.pandascore;

import com.server.pandascore.dto.championDto.ChampionDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.dto.matchDto.sub.Game;
import com.server.pandascore.dto.teamDto.TeamDto;
import com.server.pandascore.dto.teamsDetailDto.TeamsDetailDto;
import com.server.pandascore.entity.ChampionEntity;
import com.server.pandascore.entity.MatchDetailEntity;
import com.server.pandascore.entity.MatchEntity;
import com.server.pandascore.entity.TeamEntity;
import com.server.pandascore.repository.ChampionRepository;
import com.server.pandascore.repository.MatchDetailRepository;
import com.server.pandascore.repository.MatchRepository;
import com.server.pandascore.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Save {

    private String cloudFrontUrl = "https://d654rq93y7j8z.cloudfront.net";
    private final ChampionRepository championRepository;

    private final MatchRepository matchRepository;

    private final MatchDetailRepository matchDetailRepository;

    private final TeamRepository teamRepository;


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
            //slack 알람 추가 필요.
        }
    }

    public void ChampionSave(ChampionDto championDto){
        Long id = championDto.getId();
        String championImgUrl = cloudFrontUrl + "/champion/" + championDto.getName() + "_0.jpg";
        ChampionEntity championEntity = championRepository.findById(id).orElse(null);
        if(championEntity == null) {
            championEntity = new ChampionEntity(id, championImgUrl);
            championRepository.save(championEntity);
            //slack 알람 추가 필요.
        }
    }
    public void TeamSave(TeamDto teamDto){
        Long id = teamDto.getId();
        TeamEntity teamEntity = teamRepository.findById(id).orElse(null);
        if(teamEntity == null){
            teamRepository.save(teamDto.toTeamEntity());
            //slack 알람 추가 필요.
        }
    }

    public void MatchSave(MatchDto matchDto){
        Long id = matchDto.getId();
        MatchEntity matchEntity = matchRepository.findById(id).orElse(null);
        if(matchEntity == null){

            matchRepository.save(matchDto.toEntity());
            //slack 알람 추가 필요.
        }
    }
}
