package com.strange.jay.locator.locatorservice.persistence.repository;

import com.strange.jay.locator.locatorservice.persistence.api.CameraDbService;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository to save Camera items to database.
 */
@Profile("db")
public interface CameraRepository extends JpaRepository<CameraEntity, Long>, CameraDbService {

}
