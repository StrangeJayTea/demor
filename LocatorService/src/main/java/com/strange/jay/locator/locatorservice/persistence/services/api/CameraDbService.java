package com.strange.jay.locator.locatorservice.persistence.services.api;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.Collection;
import java.util.Optional;

public interface CameraDbService {

    /**
     * Finds a Camera by its table ID.
     *
     * @param id The table ID for the Camera item to locate.
     * @return The Camera data found in the database.
     */
    Optional<CameraEntity> findById(Long id);

    /**
     * Finds a Camera by its system-wide Camera ID.
     *
     * @param cameraId The Camera item's universal ID.
     * @return The Camera data found in the database.
     */
    Optional<CameraEntity> findByCameraId(int cameraId);

    /** Finds all Camera items on a floor.
     *
     * @param floorPlanId The system-wide ID for identifying the floor plan.
     * @return All Camera items found on the floor.
     */
    Collection<CameraEntity> findAllForFloor(final int floorPlanId);
}
