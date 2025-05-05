package com.strange.jay.locator.locatorservice.controllers;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.services.locator.LocatorService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides camera location information.
 */
@RestController
public class LocatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocatorController.class);

    /** Service to locate those Cameras that are closest to the referenced Camera. */
    private final LocatorService locatorService;

    /**
     * Constructs the REST controller for accessing camera location information.
     *
     * @param locatorService Provides the camera and floor information we need to fulfill the REST call.
     */
    LocatorController(final LocatorService locatorService) {
        this.locatorService = locatorService;
    }


    @GetMapping("/locator/{floor}/cameras")
    Collection<Camera> getClosestCameras(
        @PathVariable("floor") int floorId,
        @RequestParam("referenceCameraId") int referenceCameraId,
        @RequestParam("count") int count) {
        LOGGER.info("Finding the {} closest camera to camera {} on floor {}.", count, referenceCameraId, floorId);
        return this.locatorService.getClosestCameras(floorId, referenceCameraId, count);
    }
}
