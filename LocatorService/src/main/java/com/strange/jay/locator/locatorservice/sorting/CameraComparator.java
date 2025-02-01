package com.strange.jay.locator.locatorservice.sorting;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.Comparator;

/** Sorts Cameras by their distance to a reference Camera. */
public class CameraComparator implements Comparator<Camera> {

    private final Camera referenceCamera;
    private final DistanceCalculator distanceCalculator;

    public CameraComparator (final Camera referenceCamera, final DistanceCalculator distanceCalculator) {
        this.referenceCamera = referenceCamera;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public int compare (final Camera c1, final Camera c2) {
        final double distanceToCamera1 = this.distanceCalculator.calculateDistance(referenceCamera, c1);
        final double distanceToCamera2 = this.distanceCalculator.calculateDistance(referenceCamera, c2);
        return Double.compare(distanceToCamera1, distanceToCamera2);
    }

}
