package com.server.pandascore;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class PandaScoreCrawling implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("PandaScoreCrawling is running");
    }

    public String crawling() {
        return "success";
    }

}

