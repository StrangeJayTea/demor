package com.strange.jay.locator.locatorservice.services.camera;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.Collection;

/**
 * Finds the requested Camera items.
 */
public interface CameraFinder {

    /**
     * Finds all cameras on the specified floor.
     *
     * @param floorId Unique identifier for the Floor to find Cameras.
     * @return The Camera items found on the specified floor.
     */
    Collection<Camera> getCamerasForFloor(final int floorId);
}
