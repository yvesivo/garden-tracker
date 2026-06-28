package com.gardentracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gardentracker.dto.PlantRequest;
import com.gardentracker.dto.PlantResponse;
import com.gardentracker.exception.GlobalExceptionHandler;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.PlantCategory;
import com.gardentracker.service.PlantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantController.class)
@Import(GlobalExceptionHandler.class)
class PlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlantService plantService;

    @Test
    @DisplayName("GET /api/plants gibt 200 und Pflanzenliste zurück")
    void getAll_returns200WithPlantList() throws Exception {
        PlantResponse response = new PlantResponse(
                1L, "Tomate", "Solanum lycopersicum", PlantCategory.GEMÜSE, 70, 60, 2, "Klassisch");

        when(plantService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/plants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Tomate"))
                .andExpect(jsonPath("$[0].category").value("GEMÜSE"));
    }

    @Test
    @DisplayName("GET /api/plants/{id} gibt 404 zurück wenn nicht gefunden")
    void getById_returns404_whenNotFound() throws Exception {
        when(plantService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Pflanze nicht gefunden: 99"));

        mockMvc.perform(get("/api/plants/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Pflanze nicht gefunden: 99"));
    }

    @Test
    @DisplayName("GET /api/plants/category/GEMÜSE gibt gefilterte Liste zurück")
    void getByCategory_returns200_forValidCategory() throws Exception {
        PlantResponse response = new PlantResponse(
                1L, "Tomate", null, PlantCategory.GEMÜSE, 70, 60, 2, null);

        when(plantService.findByCategory(PlantCategory.GEMÜSE)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/plants/category/GEMÜSE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("GEMÜSE"));
    }

    @Test
    @DisplayName("POST /api/plants mit gültigem Body gibt 201 zurück")
    void create_returns201_withValidBody() throws Exception {
        PlantRequest request = new PlantRequest();
        request.setName("Gurke");
        request.setLatinName("Cucumis sativus");
        request.setCategory(PlantCategory.GEMÜSE);
        request.setDaysTillHarvest(60);

        PlantResponse response = new PlantResponse(
                2L, "Gurke", "Cucumis sativus", PlantCategory.GEMÜSE, 60, 50, 1, null);

        when(plantService.create(any(PlantRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Gurke"));
    }

    @Test
    @DisplayName("POST /api/plants mit leerem Namen gibt 400 zurück")
    void create_returns400_whenNameIsBlank() throws Exception {
        PlantRequest request = new PlantRequest();
        request.setName("");
        request.setCategory(PlantCategory.GEMÜSE);

        mockMvc.perform(post("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/plants ohne Kategorie gibt 400 zurück")
    void create_returns400_whenCategoryIsNull() throws Exception {
        PlantRequest request = new PlantRequest();
        request.setName("Pflanze");

        mockMvc.perform(post("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/plants/{id} gibt 204 zurück")
    void delete_returns204_whenDeleted() throws Exception {
        mockMvc.perform(delete("/api/plants/1"))
                .andExpect(status().isNoContent());
    }
}
