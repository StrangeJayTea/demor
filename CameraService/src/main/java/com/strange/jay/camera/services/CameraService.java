package com.strange.jay.camera.services;

import com.strange.jay.camera.domain.Camera;
import java.util.Collection;

/**
 * Provides known {@link Camera} information.
 */
public interface CameraService {

    /**
     * Provide an unsorted collection of all Cameras.
     *
     * @return A non-null collection of all Cameras.
     */
    Collection<Camera> getAll();

    /**
     * Provides the camera with the provided ID value.
     *
     * @param id The int ID value to look up the Camera.
     * @return The matching Camera item. May return null if not found.
     */
    Camera getById(int id);
}
