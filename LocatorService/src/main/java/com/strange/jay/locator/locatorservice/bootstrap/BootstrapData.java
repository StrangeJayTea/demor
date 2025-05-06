package com.strange.jay.locator.locatorservice.bootstrap;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import com.strange.jay.locator.locatorservice.persistence.jpa.CameraJpaServiceImpl;
import com.strange.jay.locator.locatorservice.persistence.repository.CameraRepository;
import java.util.Collection;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Called when we use the database to save an initial set of fake Camera data.
 */
@Component
@Profile({"db", "db-jpa"})
public class BootstrapData implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapData.class);

    /** Component that reads fake Camera data from a config file. */
    private final CameraPropertiesReader cameraPropertiesReader;

    /** Method to call for saving the Camera data to the database. */
    private final Consumer<Collection<CameraEntity>> cameraEntityConsumer;

    /**
     * Saves fake Camera data to the database at application start-up.
     *
     * @param cameraPropertiesReader Provides the fake data read from the config file.
     * @param cameraEntityConsumer The method to call for saving data to the database.
     */
    public BootstrapData(
        final CameraPropertiesReader cameraPropertiesReader,
        final Consumer<Collection<CameraEntity>> cameraEntityConsumer) {

        this.cameraPropertiesReader = cameraPropertiesReader;
        this.cameraEntityConsumer = cameraEntityConsumer;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Saving data...");
        this.cameraEntityConsumer.accept(this.cameraPropertiesReader.getCameras());
    }


    /** Configuration class to determine which consumer to call to save the data. */
    @Configuration
    static class CameraConsumer {
        private final ApplicationContext applicationContext;

        CameraConsumer(final ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        /**
         * Creates a consumer for saving data using Spring Data.
         *
         * @return The method to call for saving data via Spring Data.
         */
        @Bean
        @Profile("db")
        Consumer<Collection<CameraEntity>> cameraSpingDataConsumer() {
            final var springDataRepository = this.applicationContext.getBean(CameraRepository.class);
            return springDataRepository::saveAll;
        }

        /**
         * Creates a consumer for saving data using traditional JPA.
         *
         * @return The method to call to save data to the database using JPA.
         */
        @Bean
        @Profile("db-jpa")
        Consumer<Collection<CameraEntity>> cameraJpaConsumer() {
            final var jpaService = this.applicationContext.getBean(CameraJpaServiceImpl.class);
            return jpaService::saveAll;
        }
    }
}
