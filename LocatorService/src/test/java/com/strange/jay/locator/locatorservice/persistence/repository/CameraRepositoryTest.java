package com.strange.jay.locator.locatorservice.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.strange.jay.locator.locatorservice.domain.Camera;
import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("db")
class CameraRepositoryTest {

    @Autowired
    private CameraRepository cameraRepository;

    @Test
    void testSaveCamera() {
        CameraEntity camera = new CameraEntity();
        camera.setCameraId(123);
        camera.setFloorId(1);
        camera.setxFeet(50);
        camera.setyFeet(50);
        this.cameraRepository.save(camera);

        assertThat(camera.getId()).isNotNull();
    }

    @Test
    void testFindAll() {
        final List<CameraEntity> cameras = this.cameraRepository.findAll();
    }

}
