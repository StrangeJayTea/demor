package com.strange.jay.locator.locatorservice.services;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.Collection;
import java.util.Map;

/**
 * Finds the requested Camera items.
 */
public interface CameraFinder {

    /**
     * Finds the unique Camera IDs for the Camera items on the floor.
     *
     * @param floorId Unique identifier for the Floor to find Cameras.
     * @return A non-null collection of Camera IDs.
     */
    Collection<Integer> getCameraIds(final int floorId);

    /**
     * Retrieves the desired Camera items.
     *
     * @param cameraIds The unique identifier for the Camera items to find.
     * @return A Mapping of the Camera's ID to the found item.
     */
    Map<Integer, Camera> getCameras(final Collection<Integer> cameraIds);

}
