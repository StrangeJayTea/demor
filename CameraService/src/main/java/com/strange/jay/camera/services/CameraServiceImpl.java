package com.strange.jay.camera.services;

import com.strange.jay.camera.domain.Camera;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * A CameraService implementation that returns fake data.
 */
@Service
public class CameraServiceImpl implements CameraService {

    private final CameraPropertiesReader cameraPropertiesReader;

    /** Mapping of the camera's ID to its instance. */
    private final Map<Integer, Camera> cameraMap;

    /** For use when adding new made-up cameras. */
    private int counter = 0;

    CameraServiceImpl(final CameraPropertiesReader cameraPropertiesReader) {
        this.cameraPropertiesReader = cameraPropertiesReader;
        this.cameraMap = new ConcurrentHashMap<Integer, Camera>();
        populateMap();
    }

    @Override
    public Collection<Camera> getAll() {
        return this.cameraMap.values();
    }

    @Override
    public Camera getById(int id) {
        return this.cameraMap.computeIfAbsent(id, k-> createNewCamera(id));
    }

    /**
     * Make up a fake camera for now. Allows us to always have a camera for the given ID.
     * @param cameraId Identifies a camera to create and add to our cache.
     * @return The new camera item.
     */
    private Camera createNewCamera(final int cameraId) {
        if (++counter > 5) {
            counter = 0;
        }
        return new Camera(cameraId, 2 * counter, 3 * counter);
    }

    private void populateMap() {
        this.cameraPropertiesReader.getCameras().forEach(camera -> this.cameraMap.put(camera.id(), camera));
    }

    /**
     * Static inner class to read fake data from application.properties using the "app.fake" prefix.
     */
    @Configuration
    @ConfigurationProperties(prefix = "app.fake")
    public static class CameraPropertiesReader {

        private static final Logger log = LoggerFactory.getLogger(CameraPropertiesReader.class);

        private Collection<Camera> cameras;

        CameraPropertiesReader() {
            this.cameras = new ArrayList<>();
        }

        /**
         * Used by Spring to set the values read from the applications.properties file.
         *
         * @param cameras The Camera items read from the properties file.
         */
        void setCameras(Collection<Camera> cameras) {
            this.cameras = cameras;
        }

        /**
         * Provides the Camera items read from the properties file.
         *
         * @return A non-null Collection of Camera items to use.
         */
        Collection<Camera> getCameras() {
            return this.cameras;
        }

        @PostConstruct
        public void init() {
            log.info("Read {} cameras", cameras.size());
        }
    }
}
