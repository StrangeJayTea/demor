package com.strange.jay.locator.locatorservice.bootstrap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.strange.jay.locator.locatorservice.persistence.entities.CameraEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"db", "db-jpa"})
class BootstrapDataTest {

    private static final int NUM_TEST_ITEMS =5;
    private BootstrapData bootstrapData;
    private CameraPropertiesReader cameraPropertiesReader;
    private Consumer<Collection<CameraEntity>> cameraEntityConsumer;
    private ArgumentCaptor<Collection<CameraEntity>> cameraEntityCaptor;


    @BeforeEach
    void setUp() {
        this.cameraPropertiesReader = mock(CameraPropertiesReader.class);
        this.cameraEntityConsumer = Mockito.<Consumer>mock(Consumer.class);
        this.bootstrapData = new BootstrapData(this.cameraPropertiesReader, this.cameraEntityConsumer);
        this.cameraEntityCaptor = ArgumentCaptor.forClass(Collection.class);
        setupPropertiesReader();
    }

    /**
     * Test that the startup calls save for the number of CameraEntity items found by the CameraPropertiesReader.
     * @throws Exception
     */
    @Test
    public void testStartup() throws Exception {
        this.bootstrapData.run();
        then(this.cameraEntityConsumer).should().accept(this.cameraEntityCaptor.capture());
        final Collection<CameraEntity> cameraEntitiesToSave = this.cameraEntityCaptor.getValue();
        assertNotNull(cameraEntitiesToSave);
        assertEquals(NUM_TEST_ITEMS, cameraEntitiesToSave.size());
    }


    /**
     * Make some fake CameraEntity items for the CameraPropertiesReader to provide.
     */
    private void setupPropertiesReader() {
        Collection<CameraEntity> cameras = new ArrayList<>();
        for (int i = 1; i <= NUM_TEST_ITEMS; i++) {
            CameraEntity entity = new CameraEntity();
            entity.setFloorId(1);
            entity.setCameraId(i);
            entity.setxFeet(i * 2);
            entity.setyFeet(i * 3);
            cameras.add(entity);
        }
        when(this.cameraPropertiesReader.getCameras()).thenReturn(cameras);
    }

}
