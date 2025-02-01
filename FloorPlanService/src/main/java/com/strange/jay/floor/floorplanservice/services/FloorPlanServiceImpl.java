package com.strange.jay.floor.floorplanservice.services;

import com.strange.jay.floor.floorplanservice.domain.FloorPlan;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

/**
 * A version of the FloorPlanService that provides hard-coded values.
 */
@Service
public class FloorPlanServiceImpl implements FloorPlanService {

    /** Some made up values. */
    private final Map<Integer, FloorPlan> floorPlanMap;

    /** Use this in case we need to reference some new camera instances. */
    private int cameraIndex;

    /**
     * A quick-n-dirty service with hard-coded values.
     */
    public FloorPlanServiceImpl() {
        this.floorPlanMap = new ConcurrentHashMap<>();
        populateMap();
    }

    @Override
    public Collection<FloorPlan> getAll() {
        return this.floorPlanMap.values();
    }

    @Override
    public FloorPlan getById(int floorPlanId) {
        return this.floorPlanMap.computeIfAbsent(floorPlanId,
            k -> new FloorPlan(floorPlanId, List.of(cameraIndex++, cameraIndex++)));
    }

    /**
     * Populate the map with some initial values.
     */
    private void populateMap() {
        this.floorPlanMap.put(1, new FloorPlan(1, List.of(1, 2, 3, 4)));
        this.floorPlanMap.put(2, new FloorPlan(2, List.of(5, 6, 7)));
        this.floorPlanMap.put(3, new FloorPlan(3, List.of(8, 9, 10, 11, 12)));
        cameraIndex = 13;
    }
}
