package com.strange.jay.locator.locatorservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.strange.jay.locator.locatorservice.domain.Camera;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.strange.jay.locator.locatorservice.services.locator.LocatorService;

/** Try out Spring Boot test splice to test the controller. */
@WebMvcTest(LocatorController.class)
public class LocationControllerTest {

    /** Takes 3 parameters: the floor number, the reference cameraId, and the number of results to return.*/
    private static final String URL = "/locator/%1$d/cameras?referenceCameraId=%2$d&count=%3$d";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocatorService locatorService;

    @Test
    void testGetClosestCameras() throws Exception {
        final List<Camera> results = List.of(
            new Camera(3, 6, 7),
            new Camera(4, 8, 9));
        given(this.locatorService.getClosestCameras(5, 2, 3))
            .willReturn(Optional.of(results));

        final String uri = String.format(URL, 5, 2, 3);
        this.mockMvc.perform(get(uri)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(results.size()));
    }

    @Test
    void testReferenceCameraNotFound() throws Exception {
        given(this.locatorService.getClosestCameras(anyInt(), anyInt(), anyInt()))
            .willReturn(Optional.empty());

        final String uri = String.format(URL, 5, 2, 3);
        this.mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testInternalError() throws Exception {
        given(this.locatorService.getClosestCameras(anyInt(), anyInt(), anyInt()))
            .willThrow(IllegalAccessError.class);
        final String uri = String.format(URL, 12, 77, 3);
        this.mockMvc.perform(get(uri)).andExpect(status().isInternalServerError());
    }
}
