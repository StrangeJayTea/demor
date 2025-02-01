package com.strange.jay.floor.floorplanservice.controllers;

import com.strange.jay.floor.floorplanservice.domain.FloorPlan;
import com.strange.jay.floor.floorplanservice.services.FloorPlanService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloorPlanController {

    private static final Logger log = LoggerFactory.getLogger(FloorPlanController.class);
    
    /** Provides the FloorPlan information. */
    private FloorPlanService floorPlanService;

    private FloorPlanController(final FloorPlanService floorPlanService) {
        this.floorPlanService = floorPlanService;
    }

    @GetMapping("/floorplans")
    Collection<FloorPlan> getAll() {
        return this.floorPlanService.getAll();
    }

    @GetMapping("/floorplans/{id}")
    Collection<Integer> getById(@PathVariable int id) {
        log.info("In getById {}", id);
        return this.floorPlanService.getById(id).cameraIds();
    }
}
