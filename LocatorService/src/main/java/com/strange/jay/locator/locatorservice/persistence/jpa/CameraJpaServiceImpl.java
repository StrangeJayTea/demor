package com.strange.jay.locator.locatorservice.persistence.jpa;


import com.strange.jay.locator.locatorservice.persistence.api.CameraDbService;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;

//@Component
public class CameraJpaServiceImpl implements CameraDbService  {

    /** An EntityManager to use for communicating with the database. */
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<CameraEntity> getCameraEntitiesByFloorId(int floorPlanId) {
        return List.of();
    }
}


