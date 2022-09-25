package com.server.pandascore.service.fcm;

import com.server.pandascore.dto.fcmDto.FcmDto;
import com.server.pandascore.dto.matchDto.MatchDto;
import com.server.pandascore.entity.UserEntity;
import com.server.pandascore.properties.Tokens;
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

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "key=" + tokens.getFcm());

        return headers;
    }
    private void sendFcm(JSONObject jsonObject, MatchDto matchDto) {
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

    public void sendFcmByMatch(MatchDto matchDto, String status) {
        String homeTeamName = matchDto.getOpponents().get(0).getOpponent().getAcronym();
        String awayTeamName = matchDto.getOpponents().get(1).getOpponent().getAcronym();
        Long setNumber = matchDto.getResults().get(0).getScore() + matchDto.getResults().get(1).getScore();

        String title = homeTeamName + " vs " + awayTeamName + " " + setNumber + "세트";

        if(status.equals("running"))
            title += " 시작";
        else if (status.equals("finished"))
            title += " 종료";

        JSONObject notification = new JSONObject();
        notification.put("title", title);

        sendFcm(notification, matchDto);
        log.info("Fcm is sent");
    }

    public void sendFcmMatchStart(MatchDto matchDto) {
        String homeTeamName = matchDto.getOpponents().get(0).getOpponent().getAcronym();
        String awayTeamName = matchDto.getOpponents().get(1).getOpponent().getAcronym();
        Long setNumber = matchDto.getResults().get(0).getScore() + matchDto.getResults().get(1).getScore();

        JSONObject notification = new JSONObject();
        notification.put("title", homeTeamName + " vs " + awayTeamName + " " + setNumber + "세트 시작");

        sendFcm(notification, matchDto);
        log.info("Fcm is sent");
    }
}
