package com.strange.jay.floor.floorplanservice.domain;

import java.util.Collection;

/**
 * Encapsulates the data for a single floor plan.
 *
 * @param id The unique ID for the floor plan.
 * @param cameraIds The unique IDs for the cameras housed within this floor plan.
 */
public record FloorPlan(int id, Collection<Integer> cameraIds) {
}
