package com.strange.jay.locator.locatorservice.persistence.services.api;

public interface DistanceDbService {

    /** Finds the distance between two cameras on the same floor. */
    double getDistance(final int camer1Id, final int camer2Id);
}
