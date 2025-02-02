package com.strange.jay.locator.locatorservice.services;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Version of the service that makes a REST call to the CameraService.
 */
@Service
public class CameraFinderImpl implements CameraFinder{

    private static final Logger log = LoggerFactory.getLogger(CameraFinderImpl.class);

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
    CameraFinderImpl(
        final RestTemplateBuilder restTemplateBuilder,
        @Value("${url.floorplans}") final String floorPlanUrl,
        @Value("${url.cameras}") final String cameraUrl) {

        this.restTemplate = restTemplateBuilder.build();
        this.floorUrlFormat = floorPlanUrl;
        this.cameraUrlFormat = cameraUrl;
    }

    @Override
    public Collection<Integer> getCameraIds(final int floorId) {
        final String floorUrl = String.format(this.floorUrlFormat, floorId);
        log.info("Getting the cameras for floor {} using URL {}", floorId, floorUrl);
        final Collection<Integer> cameraIds = this.restTemplate.getForObject(floorUrl, Collection.class);
        if (cameraIds != null) {
            log.info("Found {} cameras", cameraIds.size());
            return cameraIds;
        } else {
            log.warn("No cameras found for floor {}", floorId);
            return Collections.emptyList();
        }
    }

    @Override
    public Map<Integer, Camera> getCameras(Collection<Integer> cameraIds) {
        Collection<CompletableFuture<Camera>> cameraFutures = createThreads(cameraIds);
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
        log.info("Getting the cameras for cameraId {} using URL {}", cameraId, cameraUrl);

        try {
            // Just to make it fun
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Camera result = this.restTemplate.getForObject(cameraUrl, Camera.class);
        log.info("Got a Camera: {}", result);
        return result;
    }

    /**
     * When the threads are done, aggregate the information.
     *
     * @param cameraFutures The threads that have completed.
     * @return A Map of Camera ID to the Camera that was found.
     */
    private Map<Integer, Camera> aggregateResults(Collection<CompletableFuture<Camera>> cameraFutures) {
        final Collection<Camera> cameras = new ArrayList<>();
        for (final CompletableFuture<Camera> cameraFuture : cameraFutures) {
            try {
                cameras.add(cameraFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                log.warn("Exception getting CameraFuture", e);
            }

        }
        log.info("Extracted {} Cameras from the expected {} items.", cameras.size(), cameraFutures.size());
        return cameras.stream().collect(Collectors.toMap(Camera::id, camera -> camera));
    }
}
