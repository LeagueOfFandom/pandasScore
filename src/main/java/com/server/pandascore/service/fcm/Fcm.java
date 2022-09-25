package com.server.pandascore.service.fcm;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.server.pandascore.dto.fcmDto.FcmDto;
import com.server.pandascore.dto.gameDto.GameDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.entity.MatchDetailEntity;
import com.server.pandascore.entity.UserEntity;
import com.server.pandascore.properties.Tokens;
import com.server.pandascore.repository.MatchDetailRepository;
import com.server.pandascore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class Fcm {
    private final Tokens tokens;

    private final UserRepository userRepository;
    private final MatchDetailRepository matchDetailRepository;

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "key=" + tokens.getFcm());

        return headers;
    }
    private void sendFcm(JSONObject jsonObject) {
        String url = "https://fcm.googleapis.com/fcm/send";
        List<UserEntity> userList = userRepository.findAll();
        userList.forEach(user -> {
            JSONObject userJson = new JSONObject();
            userJson.put("to", user.getToken());
            userJson.put("notification", jsonObject);
            HttpEntity<String> request = new HttpEntity<>(userJson.toString(), setHeaders());
            ResponseEntity<FcmDto> response = new RestTemplate().exchange(url, HttpMethod.POST, request, FcmDto.class);
        });
    }

    public void sendFcmByGame(GameDto gameDto) {
        MatchDetailEntity matchDetailEntity = matchDetailRepository.findById(gameDto.getId()).orElse(null);
        if(matchDetailEntity.getStatus().equals(gameDto.getStatus()))
            return;

        String homeTeamName = gameDto.getTeams().get(0).getTeam().getAcronym();
        String awayTeamName = gameDto.getTeams().get(1).getTeam().getAcronym();

        String title = homeTeamName + " vs " + awayTeamName + " " + gameDto.getPosition() + "번째 경기";

        if(gameDto.equals("running"))
            title += " 시작";
        else if (gameDto.equals("finished"))
            title += " 종료";

        JSONObject notification = new JSONObject();
        notification.put("title", title);

        sendFcm(notification);
        log.info("Fcm is sent");
    }
}
