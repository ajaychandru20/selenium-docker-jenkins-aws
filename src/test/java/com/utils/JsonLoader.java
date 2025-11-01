package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class JsonLoader {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T getTestData(String url, Class<T> type) {
        try (InputStream stream = ResourceLoader.getResourceFileToStream(url)) {
            logger.debug("Successfully read input stream for URL: {}", url);
            return mapper.readValue(stream, type);
        } catch (Exception e) {
            logger.error("Unable to read the testdata from URL: {}", url, e);
            return null;
        }

    }

}
