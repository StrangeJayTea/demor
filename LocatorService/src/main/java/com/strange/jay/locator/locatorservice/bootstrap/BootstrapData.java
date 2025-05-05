package com.strange.jay.locator.locatorservice.bootstrap;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import com.strange.jay.locator.locatorservice.persistence.repository.CameraRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("db")
public class BootstrapData implements CommandLineRunner {

    private final CameraRepository cameraRepository;
    private final CameraPropertiesReader cameraPropertiesReader;

    public BootstrapData(final CameraRepository cameraRepository, final CameraPropertiesReader cameraPropertiesReader) {
        this.cameraRepository = cameraRepository;
        this.cameraPropertiesReader = cameraPropertiesReader;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("IN the 'db' profile!");
        this.cameraRepository.saveAll(this.cameraPropertiesReader.getCameras());
    }

    /**
     * Static inner class to read fake data from application.properties using the "app.fake" prefix.
     */
    @Configuration
    @PropertySource("classpath:camera.properties")
    @ConfigurationProperties(prefix = "app.fake")
    public static class CameraPropertiesReader {

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
            LOGGER.info("Read {} cameras", cameras.size());
        }
    }
}
