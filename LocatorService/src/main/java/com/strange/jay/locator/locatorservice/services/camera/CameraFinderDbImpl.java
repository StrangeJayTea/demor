package com.strange.jay.locator.locatorservice.services.camera;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.mappers.CameraMapper;
import com.strange.jay.locator.locatorservice.persistence.api.CameraDbService;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Version of the CameraFinder to look-up Cameras in the database instead of calling the other services.
 */
@Service
@Profile({"db", "db-jpa"})
class CameraFinderDbImpl implements CameraFinder {

    /** Provides access to Camera items saved in the database. */
    private final CameraDbService cameraDbService;

    /** Creates a new Camera domain object from an Entity object. */
    private final CameraMapper cameraMapper;

    public CameraFinderDbImpl(final CameraDbService cameraDbService, final CameraMapper cameraMapper) {
        this.cameraDbService = cameraDbService;
        this.cameraMapper = cameraMapper;
    }

    @Override
    public Collection<Camera> getCamerasForFloor(final int floorId) {
        final List<CameraEntity> cameraEntities = this.cameraDbService.getCameraEntitiesByFloorId(floorId);
        return cameraEntities.stream()
            .map(this.cameraMapper::cameraEntityToCamera)
            .toList();
    }
}
