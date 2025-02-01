package com.strange.jay.floor.floorplanservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.strange.jay.floor.floorplanservice.domain.FloorPlan;

public class FloorPlanServiceImplTest {

    /** When adding a new floor plan, currently it adds the plan with 2 cameras. */
    private static final int EXPECTED_NUM_CAMERAS = 2;

    private FloorPlanService floorPlanService;

    @BeforeEach
    public void setup() {
        this.floorPlanService = new FloorPlanServiceImpl();
    }

    @Test
    public void test_floorPlanPresent() {
        FloorPlan floorPlan1 = this.floorPlanService.getById(1);
        assertNotNull(floorPlan1);
        assertEquals(1, floorPlan1.id());
    }

    /**
     * Ensure that:
     * 1. Retrieval does not return null (creates a new floor plan)
     * 2. Retrieval is idempotent
     */
    @Test
    public void test_floorPlanNotPresent() {
        FloorPlan newPlan = this.floorPlanService.getById(99);
        assertNewPlan(newPlan, 99);

        FloorPlan secondRetrieval = this.floorPlanService.getById(99);
        assertNewPlan(secondRetrieval, 99);

        assertEquals(newPlan, secondRetrieval);
    }

    /**
     * Verify that adding 2 new floor plans each have unique camera numbers.
     */
    @Test
    public void test_floorPlanNotPresent_Unique() {
        final int expectedNumCameras = 2;
        FloorPlan newPlan = this.floorPlanService.getById(99);
        FloorPlan evenNewerPlan = this.floorPlanService.getById(101);

        assertNewPlan(newPlan, 99);
        assertNewPlan(evenNewerPlan, 101);
        assertNotEquals(newPlan.cameraIds(), evenNewerPlan.cameraIds());

    }

    /**
     * Sanity check when adding a new floor plan.
     *
     * @param newPlan The new plan to sanity check.
     * @param planId The expected identifier for the new plan.
     */
    private void assertNewPlan(FloorPlan newPlan, int planId) {
        assertNotNull(newPlan);
        assertEquals(planId, newPlan.id());
        assertEquals(EXPECTED_NUM_CAMERAS, newPlan.cameraIds().size());
    }
}
