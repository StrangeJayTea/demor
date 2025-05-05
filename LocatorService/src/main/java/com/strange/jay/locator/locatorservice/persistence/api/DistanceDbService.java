package com.strange.jay.locator.locatorservice.persistence.api;

public interface DistanceDbService {

    /** Finds the distance between two cameras on the same floor. */
    double getDistance(final int camera1Id, final int camera2Id);
}
