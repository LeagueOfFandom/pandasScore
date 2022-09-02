package com.server.pandascore.service;

import com.server.pandascore.dto.videoDto.YouTubeDto;
import com.server.pandascore.entity.video.VideoEntity;
import com.server.pandascore.properties.Tokens;
import com.server.pandascore.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;


@Service
@Slf4j
@RequiredArgsConstructor
public class YouTubeService {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static YouTube youtube;

    private final Tokens tokens;

    private final VideoRepository videoRepository;

    public List<YouTubeDto> getYouTubeList() {
        log.info("getYouTubeList 시작");

        List<YouTubeDto> youTubeDtoList = new ArrayList<>();
        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-list").build();

            YouTube.Search.List search = youtube.search().list("snippet");

            String apiKey = tokens.getYoutube();
            search.setKey(apiKey);
            search.setChannelId("UCw1DsweY9b2AKGjV4kGJP1A");
            search.setOrder("date");
            search.setVideoEmbeddable("true");
            search.setType("video, playlist, channel");
            search.setMaxResults(25L);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null) {
                log.info("searchResultList.size() : " + searchResultList.size());
                log.info(searchResultList.iterator().next().toString());

                Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();

                if (!iteratorSearchResults.hasNext()) {
                    System.out.println(" There aren't any results for your query.");
                }

                while (iteratorSearchResults.hasNext()) {
                    SearchResult singleVideo = iteratorSearchResults.next();

                    if (singleVideo.getId().getKind().equals("youtube#video")) {
                        YouTubeDto youTubeDto = new YouTubeDto();
                        Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                        youTubeDto.setResourceId(singleVideo.getId().getVideoId());
                        youTubeDto.setTitle(singleVideo.getSnippet().getTitle());
                        youTubeDto.setThumbnail(thumbnail.getUrl());
                        youTubeDto.setPublishedAt(singleVideo.getSnippet().getPublishedAt().toString());
                        youTubeDto.setChannelId(singleVideo.getSnippet().getChannelId());
                        youTubeDto.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                        youTubeDto.setDescription(singleVideo.getSnippet().getDescription());

                        youTubeDtoList.add(youTubeDto);

                        //찾아보고 없으면 저장
                        VideoEntity videoEntity = videoRepository.findByVideoResourceId(singleVideo.getId().getVideoId());
                        if(videoEntity == null)
                            videoRepository.save(new VideoEntity(youTubeDto));

                    }
                }
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return youTubeDtoList;
    }
}

