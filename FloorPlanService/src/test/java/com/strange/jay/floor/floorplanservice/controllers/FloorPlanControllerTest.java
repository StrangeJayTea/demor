package com.strange.jay.floor.floorplanservice.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.strange.jay.floor.floorplanservice.domain.FloorPlan;
import com.strange.jay.floor.floorplanservice.services.FloorPlanService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FloorPlanController.class)
public class FloorPlanControllerTest {

    private static final String FLOOR_PLAN_URL = "/floorplans/%1$d";

    static Stream<FloorPlan> fakeFloorPlans() {
        return Stream.of(
            new FloorPlan(1, List.of(3, 5)),
            new FloorPlan(100, List.of()),
            new FloorPlan(234, List.of(9, 10, 11, 12, 13))
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FloorPlanService floorPlanService;

    @ParameterizedTest
    @MethodSource("fakeFloorPlans")
    void testGetById(final FloorPlan fakePlan) throws Exception {
        given(this.floorPlanService.getById(fakePlan.id())).willReturn(fakePlan);
        given(this.floorPlanService.getById(1)).willReturn(fakePlan);
        final String uri = String.format(FLOOR_PLAN_URL, fakePlan.id());
        this.mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(fakePlan.cameraIds().size()));
    }

    @Test
    void testGetByFloorIdZero() throws Exception {
        final FloorPlan fakeFloorPlan = new FloorPlan(1, List.of());
        given(this.floorPlanService.getById(anyInt())).willReturn(fakeFloorPlan);
        final String invalidURI = String.format(FLOOR_PLAN_URL, 0);
        this.mockMvc.perform(get(invalidURI)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetByFloorIdExceedsLimit() throws Exception {
        final FloorPlan fakeFloorPlan = new FloorPlan(1, List.of());
        given(this.floorPlanService.getById(anyInt())).willReturn(fakeFloorPlan);
        final String invalidURI = String.format(FLOOR_PLAN_URL, 9999);
        this.mockMvc.perform(get(invalidURI)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetAll() throws Exception {
        final Collection<FloorPlan> fakePlans = fakeFloorPlans().toList();
        given(this.floorPlanService.getAll()).willReturn(fakePlans);

        this.mockMvc.perform(get("/floorplans")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(fakePlans.size()));
    }
}
