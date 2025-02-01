package com.strange.jay.locator.locatorservice.sorting;

import com.strange.jay.locator.locatorservice.domain.Camera;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {

    /**
     * Calculates the distance between two Cameras on the same Floor.
     *
     * @param camera1 A Camera object with an x,y location.
     * @param camera2 A second Camera object, also with an x,y location.
     * @return The distance between camera1 to camera2.
     */
    public double calculateDistance(final Camera camera1, final Camera camera2) {
        final double xDelta = camera2.xFeet() - camera1.xFeet();
        final double xDeltaSquare = Math.pow(xDelta, 2);

        final double yDelta = camera2.yFeet() - camera1.yFeet();
        final double yDeltaSquare = Math.pow(yDelta, 2);

        return Math.sqrt(xDeltaSquare + yDeltaSquare);
    }

}
