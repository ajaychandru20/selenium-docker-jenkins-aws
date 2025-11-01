package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ResourceLoader {

    private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    public static InputStream getResourceFileToStream(String url) {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(url);

        if (Objects.nonNull(stream)) {
            logger.debug("Resource found on classpath: {}", url);
            return stream;
        }
        try {
            stream = Files.newInputStream(Path.of(url));
            logger.debug("Resource not found on classpath, loaded from file system: {}", url);
            return stream;
        } catch (java.io.IOException e) {
            logger.error("Failed to load resource: {} from both classpath and file system.", url);
            throw new RuntimeException("Resource not found at: " + url, e);
        }


    }


}
