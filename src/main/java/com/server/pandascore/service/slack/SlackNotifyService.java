package com.server.pandascore.service.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackNotifyService {

    @Value("${slack.token}")
    String token;
    @Value("${spring.profiles.active}")
    String springProfile;

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshedEvent(ContextRefreshedEvent event) {
        String text;
        if (springProfile.equals("server")) {
            text = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"))
                    + "pandascore 서버가 시작되었습니다.";
        }
        else
            return;

        sendMessage(text);
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent event) {
        String text;
        if (springProfile.equals("server")) {
            text = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"))
                    + "pandascore 서버가 종료되었습니다.";
        }
        else
            return;

        sendMessage(text);
    }

    public void sendMessage(String text) {
        String notifyChannelId = initChannelId("notification");
        if (notifyChannelId == null) {
            log.error("[SlackAPI] error: channelId is null");
            return;
        }

        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage( requestConfig ->
                    requestConfig
                            .token(token)
                            .channel(notifyChannelId)
                            .text(text)
            );
            log.info("[SlackAPI] result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("[SlackAPI] error: {}", e.getMessage(), e);
        }
    }

    private String initChannelId(String notifyChannelName) {
        log.info("[SlackAPI] channelName: {}", notifyChannelName);
        var client = Slack.getInstance().methods();
        String notifyChannelId = null;
        try {
            var result = client.conversationsList( requestConfig ->
                    requestConfig.token(token)
            );
            for (Conversation channel : result.getChannels()) {
                if (channel.getName().equals(notifyChannelName)) {
                    notifyChannelId = channel.getId();
                    log.info("[SlackAPI] Channel ID: {}", notifyChannelId);
                    break;
                }
            }
        } catch (IOException | SlackApiException | NullPointerException e) {
            log.error("[SlackAPI] error: {}", e.getMessage(), e);
        }
        return notifyChannelId;
    }
}