package com.strange.jay.camera.controllers;

import com.strange.jay.camera.domain.Camera;
import com.strange.jay.camera.services.CameraService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint for finding one or more Cameras.
 */
@RestController
public class CameraController {

    private static final Logger log = LoggerFactory.getLogger(CameraController.class);

    /** Locates the Camera items to report. */
    private final CameraService cameraService;

    CameraController(final CameraService cameraService) {
        this.cameraService = cameraService;
        log.info("CameraController");
    }

    /**
     * Provides an unfiltered view of all Camera items.
     *
     * @return All cameras in the system.
     */
    @GetMapping("/cameras")
    Collection<Camera> getAll() {
        log.info("In getAll");
        return this.cameraService.getAll();
    }

    /**
     * Provides only the camera identified via its unique ID.
     *
     * @param id An integer ID for the camera to locate.
     * @return The found Camera item.
     */
    @GetMapping("/cameras/{id}")
    Camera getById(@Positive @Max(value=5000, message="Camera ID must be > 0 and < 5000")
                   @PathVariable final int id) {
        log.info("In getById {}", id);
        return this.cameraService.getById(id);
    }
}
