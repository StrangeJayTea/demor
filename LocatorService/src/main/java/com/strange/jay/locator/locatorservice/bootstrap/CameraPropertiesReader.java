package com.strange.jay.locator.locatorservice.bootstrap;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Reads fake camera data from application.properties using the "app.fake" prefix.
 */
@Configuration
@PropertySource("classpath:camera.properties")
@ConfigurationProperties(prefix = "app.fake")
public class CameraPropertiesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraPropertiesReader.class);

    private Collection<CameraEntity> cameras;

    CameraPropertiesReader() {
        this.cameras = new ArrayList<>();
    }

    /**
     * Used by Spring to set the values read from the applications.properties file.
     *
     * @param cameras The Camera items read from the properties file.
     */
    void setCameras(final Collection<CameraEntity> cameras) {
        this.cameras = cameras;
    }

    /**
     * Provides the Camera items read from the properties file.
     *
     * @return A non-null Collection of Camera items to use.
     */
    Collection<CameraEntity> getCameras() {
        return this.cameras;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Read {} cameras", this.cameras.size());
    }
}
