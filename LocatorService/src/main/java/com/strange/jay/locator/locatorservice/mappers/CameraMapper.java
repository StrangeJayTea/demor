package com.strange.jay.locator.locatorservice.mappers;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Use MapStruct to auto-generate conversion between domain and persistence Entity objects. */
@Mapper
public interface CameraMapper {

    /**
     * Converts a Camera domain object to a CameraEntity persistence object.
     *
     * @param camera A Camera object.
     * @return The equivalent persistence object.
     */
    @Mapping(target="cameraId", source="camera.id")
    @Mapping(target="id", ignore=true)
    CameraEntity cameraToCameraEntity(Camera camera);

    /**
     * Convert a persistence CameraEntity item to a Camera domain object.
     * @param cameraEntity A database entry to convert to a domain object.
     * @return The equivalent domain object.
     */
    @Mapping(target ="id", source="cameraEntity.cameraId")
    Camera cameraEntityToCamera(CameraEntity cameraEntity);
}
