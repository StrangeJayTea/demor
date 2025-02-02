package com.strange.jay.locator.locatorservice.services;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.Collection;

public interface LocatorService {

    /**
     * Find the cameras closest to the camera identified by the referenceCameraId.
     *
     * @param floorId The unique ID for the floor on which to locate the cameras.
     * @param referenceCameraId The camera for which to find the closest cameras.
     * @param count How many cameras to locate (ex: find the 3 closest cameras).
     *
     * @return A non-null collection of Cameras that are closest to the referenced camera.
     */
    Collection<Camera> getClosestCameras(
        int floorId,
        int referenceCameraId,
        int count);

}
