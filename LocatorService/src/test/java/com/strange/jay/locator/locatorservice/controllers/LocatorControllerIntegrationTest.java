package com.strange.jay.locator.locatorservice.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import com.strange.jay.locator.locatorservice.persistence.repository.CameraRepository;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("db")
@SpringBootTest
class LocatorControllerIntegrationTest {

    @Autowired
    private LocatorController locatorController;

    @Autowired
    private CameraRepository cameraRepository;


    @Test
    void getAllCameras() {
        final List<CameraEntity> camerasOnFloor = this.cameraRepository.getCameraEntitiesByFloorId(1);

        final Collection<Camera> foundCameras = this.locatorController.getClosestCameras(1, camerasOnFloor.getFirst().getCameraId(), 10);
        assertEquals(camerasOnFloor.size() - 1, foundCameras.size());
    }

}
