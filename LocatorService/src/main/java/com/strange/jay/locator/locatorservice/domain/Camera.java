package com.strange.jay.locator.locatorservice.domain;

/**
 * Encapsulates a camera's information including its identification and location.
 *
 * @param id A unique identifier for the camera.
 * @param xFeet The x-distance offset in feet from the southwest corner of the floor.
 * @param yFeet The y-distance offset in feet from the southwest corner of the floor.
 */
public record Camera(int id, double xFeet, double yFeet) {
}
