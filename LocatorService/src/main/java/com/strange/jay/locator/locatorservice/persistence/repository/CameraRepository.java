package com.strange.jay.locator.locatorservice.persistence.repository;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.persistence.api.CameraDbService;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository to save Camera items to database.
 */
public interface CameraRepository extends JpaRepository<CameraEntity, Long>, CameraDbService {

}
