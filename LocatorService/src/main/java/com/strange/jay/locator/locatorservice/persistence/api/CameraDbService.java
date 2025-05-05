package com.strange.jay.locator.locatorservice.persistence.api;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CameraDbService {

    /** Finds all Camera items on a floor.
     *
     * @param floorPlanId The system-wide ID for identifying the floor plan.
     * @return All Camera items found on the floor.
     */
    List<CameraEntity> getCameraEntitiesByFloorId(final int floorPlanId);
}
