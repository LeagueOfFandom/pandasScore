package com.server.pandascore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PandaScoreCrawlingTest {

    @Test
    void crawling() {
        PandaScoreCrawling pandaScoreCrawling = new PandaScoreCrawling();
        String result = pandaScoreCrawling.crawling();
        assertEquals("success", result);
    }
}