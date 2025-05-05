package com.strange.jay.locator.locatorservice.services.camera;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Version of the service that makes a REST call to the CameraService.
 */
@Service
@Profile("rest")
class CameraFinderRestImpl implements CameraFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraFinderRestImpl.class);

    /** URL format to call the FloorPlanService to get information for a specific floor plan. */
    private final String floorUrlFormat;

    /** URL format to call the CameraService to get the location of a Camera. */
    private final String cameraUrlFormat;

    /** For making REST calls to others services. */
    private final RestTemplate restTemplate;

    /**
     * Constructs a component to make a REST call for Camera items.
     *
     * @param restTemplateBuilder Builder for creating the RestTemplate to call other services.
     * @param cameraUrl The format for making the REST call to the CameraService.
     */
    CameraFinderRestImpl(
        final RestTemplateBuilder restTemplateBuilder,
        @Value("${url.floorplans}") final String floorPlanUrl,
        @Value("${url.cameras}") final String cameraUrl) {

        this.restTemplate = restTemplateBuilder.build();
        this.floorUrlFormat = floorPlanUrl;
        this.cameraUrlFormat = cameraUrl;
    }

    @Override
    public Collection<Camera> getCamerasForFloor(final int floorId) {
        final Collection<Integer> cameraIds = getCameraIds(floorId);
        return getCameras(cameraIds);
    }

    /**
     * Finds the unique Camera IDs for the Camera items on the floor.
     *
     * @param floorId Unique identifier for the Floor to find Cameras.
     * @return A non-null collection of Camera IDs.
     */
    private Collection<Integer> getCameraIds(final int floorId) {
        final String floorUrl = String.format(this.floorUrlFormat, floorId);
        LOGGER.info("Getting the cameras for floor {} using URL {}", floorId, floorUrl);
        final Collection<Integer> cameraIds = this.restTemplate.getForObject(floorUrl, Collection.class);
        if (cameraIds != null) {
            LOGGER.info("Found {} cameras", cameraIds.size());
            return cameraIds;
        } else {
            LOGGER.warn("No cameras found for floor {}", floorId);
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves the desired Camera items.
     *
     * @param cameraIds The unique identifier for the Camera items to find.
     * @return The found Camera items.
     */
    private Collection<Camera> getCameras(Collection<Integer> cameraIds) {
        final Collection<CompletableFuture<Camera>> cameraFutures = createThreads(cameraIds);
        CompletableFuture.allOf(cameraFutures.toArray(new CompletableFuture[cameraFutures.size()])).join();
        return aggregateResults(cameraFutures);
    }

    /**
     * Get each Camera's information on a separate thread to allow concurrency.
     *
     * @param cameraIds Identifies which Cameras we need to get information.
     * @return A CompletableFuture for each Camera to retrieve.
     */
    private Collection<CompletableFuture<Camera>> createThreads(final Collection<Integer> cameraIds) {
        final Collection<CompletableFuture<Camera>> futures = new ArrayList<>();
        for (final int cameraId : cameraIds) {
            futures.add(CompletableFuture
                .supplyAsync(() -> getCamera(cameraId))
                .orTimeout(1, TimeUnit.SECONDS));
        }
        return futures;
    }

    /**
     * Makes a REST call for a single Camera by its unique ID.
     *
     * @param cameraId Identifies which Camera to retrieve.
     * @return The Camera item that was found by the CameraService.
     */
    private Camera getCamera(final int cameraId) {
        final String cameraUrl = String.format(this.cameraUrlFormat, cameraId);
        LOGGER.info("Getting the cameras for cameraId {} using URL {}", cameraId, cameraUrl);

        try {
            // Just to make it fun
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final Camera result = this.restTemplate.getForObject(cameraUrl, Camera.class);
        LOGGER.info("Got a Camera: {}", result);
        return result;
    }

    /**
     * When the threads are done, aggregate the information.
     *
     * @param cameraFutures The threads that have completed.
     * @return The found Cameras.
     */
    private Collection<Camera> aggregateResults(final Collection<CompletableFuture<Camera>> cameraFutures) {
        final Collection<Camera> cameras = new ArrayList<>();
        for (final CompletableFuture<Camera> cameraFuture : cameraFutures) {
            try {
                cameras.add(cameraFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.warn("Exception getting CameraFuture", e);
            }

        }
        LOGGER.info("Extracted {} Cameras from the expected {} items.", cameras.size(), cameraFutures.size());
        return cameras;
    }
}
