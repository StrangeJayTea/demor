package com.strange.jay.floor.floorplanservice.services;

import com.strange.jay.floor.floorplanservice.domain.FloorPlan;
import java.util.Collection;

/**
 * Provides @{@link FloorPlan} information to encapsulate the cameras on each floor.
 */
public interface FloorPlanService {

    /**
     * Provides all known floor plans.
     *
     * @return A non-null collection of {@link FloorPlan} items.
     */
    Collection<FloorPlan> getAll();

    /**
     * Provides the floor plan specified by its unique identifier.
     *
     * @param floorPlanId A unique ID for the floor plan to retrieve.
     * @return The found FloorPlan item, may return null.
     */
    FloorPlan getById(int floorPlanId);
}
