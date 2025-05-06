package com.strange.jay.locator.locatorservice.services.locator;

import com.strange.jay.locator.locatorservice.services.camera.CameraFinder;
import com.strange.jay.locator.locatorservice.sorting.CameraComparator;
import com.strange.jay.locator.locatorservice.sorting.DistanceCalculator;
import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Finds the 'n' closest cameras to a reference camera.s
 */
@Service
class LocatorServiceImpl implements LocatorService {
    
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
    public Optional<List<Camera>> getClosestCameras(
        final int floorId,
        final int referenceCameraId,
        final int count) {

        final Collection<Camera> foundCameras = this.cameraFinder.getCamerasForFloor(floorId);
        final Optional<Camera> referenceCamera = extractReferenceCamera(foundCameras, referenceCameraId);

        return referenceCamera
             .map(cameraRef -> Optional.of(sortAndLimit(cameraRef, foundCameras, count)))
             .orElseGet(() ->{
                 log.warn("Reference camera {} not found", referenceCameraId);
                 return Optional.empty();
        });
    }

    /**
     * Finds the reference camera so that we can determine which other cameras are near this one.
     *
     * @param foundCameras All cameras found for the floor.
     * @param referenceCameraId Identifies which camera is the reference point for locating other cameras close by.
     * @return The camera to reference for finding nearby cameras.
     */
    private Optional<Camera> extractReferenceCamera(final Collection<Camera> foundCameras, final int referenceCameraId) {
        return foundCameras.stream()
            .filter(camera -> camera.id() == referenceCameraId)
            .findFirst();
    }

    /**
     * Sorts the cameras by distance to the reference camera.  Then limits the size of the return set.
     *
     * @param referenceCamera The reference location for finding nearby cameras.
     * @param cameras The other cameras located on the same floor as the reference camera.
     * @param limit How many nearby cameras (at most) to locate.
     * @return A list of cameras sorted by the distance to the reference camera.
     */
    private List<Camera> sortAndLimit(final Camera referenceCamera, final Collection<Camera> cameras, final int limit) {
        final List<Camera> sortedCameras = removeReferenceCamera(cameras, referenceCamera.id());
        sortedCameras.sort(new CameraComparator(referenceCamera, this.distanceCalculator));
        final int listSize = Math.min(sortedCameras.size(), limit);
        return sortedCameras.subList(0, listSize);
    }

    /**
     * Removes the reference camera from our list of Cameras to sort by distance.
     * Because stream() provides an immutable List, we return a new ArrayList to hold the results.
     *
     * @param foundCameras All cameras found on the floor, may include the reference camera.
     * @param referenceCameraId The camera to omit from sorting by distance.
     * @return A mutable list of cameras that excludes the reference camera.
     */
    private List<Camera> removeReferenceCamera(final Collection<Camera> foundCameras, final int referenceCameraId) {
        final List<Camera> otherCameras = foundCameras.stream()
            .filter(camera -> camera.id() != referenceCameraId)
            .toList();
        return new ArrayList<>(otherCameras);
    }

}
