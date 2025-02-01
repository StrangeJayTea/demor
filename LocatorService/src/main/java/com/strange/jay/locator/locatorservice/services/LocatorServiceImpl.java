package com.strange.jay.locator.locatorservice.services;

import com.strange.jay.locator.locatorservice.sorting.CameraComparator;
import com.strange.jay.locator.locatorservice.sorting.DistanceCalculator;
import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Version of the service that does not use a database.
 */
@Service
public class LocatorServiceImpl implements LocatorService {
    
    private static final Logger log = LoggerFactory.getLogger(LocatorServiceImpl.class);

    /** Use this to find the desired Camera items. */
    private final CameraFinder cameraFinder;

    /** Finds the distance between two Cameras on the same floor. */
    private final DistanceCalculator distanceCalculator;

    /**
     * Constructs a mechanism for finding the cameras closest to resource.
     *
     * @param cameraFinder Finds the Cameras.
     * @param distanceCalculator Determines the distance between Cameras for the same floor.
     */
    LocatorServiceImpl(
        final CameraFinder cameraFinder,
        final DistanceCalculator distanceCalculator) {

        this.cameraFinder = cameraFinder;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Collection<Camera> getClosestCameras(int floorId, int referenceCameraId, int count) {
        final Collection<Integer> cameraIds = this.cameraFinder.getCameraIds(floorId);
        final Map<Integer, Camera> foundCameras = this.cameraFinder.getCameras(cameraIds);

         final Optional<Camera> referenceCamera = Optional.ofNullable(foundCameras.remove(referenceCameraId));
         return referenceCamera
             .map(cameraRef -> sortAndLimit(cameraRef, foundCameras.values(), count))
             .orElseGet(() ->{
                 log.warn("Reference camera {} not found", referenceCamera);
                 return Collections.emptyList();
        });
    }

    private List<Camera> sortAndLimit(final Camera referenceCamera, final Collection<Camera> cameras, final int limit) {
        List<Camera> sortedCameras = new ArrayList<>(cameras);
        sortedCameras.sort(new CameraComparator(referenceCamera, this.distanceCalculator));
        final int listSize = Math.min(sortedCameras.size(), limit);
        return sortedCameras.subList(0, listSize);
    }



}
