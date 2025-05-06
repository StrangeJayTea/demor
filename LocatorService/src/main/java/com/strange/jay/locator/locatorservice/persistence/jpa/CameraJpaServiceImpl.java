package com.strange.jay.locator.locatorservice.persistence.jpa;


import com.strange.jay.locator.locatorservice.persistence.api.CameraDbService;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db-jpa")
public class CameraJpaServiceImpl implements CameraDbService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraJpaServiceImpl.class);

    /** An EntityManager to use for communicating with the database. */
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<CameraEntity> getCameraEntitiesByFloorId(final int floorId) {
        return this.em.createQuery("FROM CameraEntity e WHERE e.floorId = :floorId", CameraEntity.class)
            .setParameter("floorId", floorId)
            .getResultList();
    }


    /**
     * Bulk save of the CameraEntity items to the database.
     *
     * @param cameras Collection of CameraEntity items to persist.
     */
    @Transactional
    public void saveAll(final Collection<CameraEntity> cameras) {
        LOGGER.info("Saving {} cameras", cameras.size());
        for (CameraEntity camera : cameras) {
            this.em.persist(camera);
        }
    }
}


