package com.strange.jay.camera.controllers;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.strange.jay.camera.domain.Camera;
import com.strange.jay.camera.services.CameraService;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CameraController.class)
public class CameraControllerTest {

    private static final String CAMERA_URL = "/cameras/%1$d";

    static Stream<Camera> fakeCameras() {
        return Stream.of(
            new Camera(1, 3, 5),
            new Camera(100, 999.9, 999.99),
            new Camera(999, 0.01, 0.02)
        );
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private CameraService cameraService;
    
    @ParameterizedTest
    @MethodSource("fakeCameras")
    void testGetById(final Camera fakeCamera) throws Exception {
        given(this.cameraService.getById(fakeCamera.id())).willReturn(fakeCamera);
        final String uri = String.format(CAMERA_URL, fakeCamera.id());
        this.mockMvc.perform(get(uri)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fakeCamera.id()))
            .andExpect(jsonPath("$.xFeet").value(fakeCamera.xFeet()))
            .andExpect(jsonPath("$.yFeet").value(fakeCamera.yFeet()));
    }

    @Test
    void testGetByIdSetToZero() throws Exception {
        final Camera fakeCamera = new Camera(1, 2, 3);
        given(this.cameraService.getById(anyInt())).willReturn(fakeCamera);
        final String invalidURI = String.format(CAMERA_URL, 0);
        this.mockMvc.perform(get(invalidURI)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByIdExceedsLimit() throws Exception {
        final Camera fakeCamera = new Camera(1, 2, 3);
        given(this.cameraService.getById(anyInt())).willReturn(fakeCamera);
        final String invalidURI = String.format(CAMERA_URL, 99999);
        this.mockMvc.perform(get(invalidURI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAll() throws Exception {
        final Collection<Camera> fakeCameras = fakeCameras().toList();
        given(this.cameraService.getAll()).willReturn(fakeCameras);

        this.mockMvc.perform(get("/cameras")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(fakeCameras.size()));
    }
}
