package com.gardentracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gardentracker.dto.GardenRequest;
import com.gardentracker.dto.GardenResponse;
import com.gardentracker.exception.GlobalExceptionHandler;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.service.GardenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GardenController.class)
@Import(GlobalExceptionHandler.class)
class GardenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GardenService gardenService;

    @Test
    @DisplayName("GET /api/gardens gibt 200 und Liste zurück")
    void getAll_returns200WithList() throws Exception {
        GardenResponse response = new GardenResponse(
                1L, "Hauptbeet", "Beschreibung", 3.0, 1.5, "Berlin", LocalDateTime.now(), 2);

        when(gardenService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/gardens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hauptbeet"))
                .andExpect(jsonPath("$[0].location").value("Berlin"));
    }

    @Test
    @DisplayName("GET /api/gardens/{id} gibt 200 mit Garten zurück")
    void getById_returns200_whenFound() throws Exception {
        GardenResponse response = new GardenResponse(
                1L, "Hauptbeet", "Beschreibung", 3.0, 1.5, "Berlin", LocalDateTime.now(), 0);

        when(gardenService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/gardens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hauptbeet"));
    }

    @Test
    @DisplayName("GET /api/gardens/{id} gibt 404 zurück wenn nicht gefunden")
    void getById_returns404_whenNotFound() throws Exception {
        when(gardenService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Garten nicht gefunden: 99"));

        mockMvc.perform(get("/api/gardens/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Garten nicht gefunden: 99"));
    }

    @Test
    @DisplayName("POST /api/gardens mit gültigem Body gibt 201 zurück")
    void create_returns201_withValidBody() throws Exception {
        GardenRequest request = new GardenRequest();
        request.setName("Neuer Garten");
        request.setDescription("Beschreibung");
        request.setWidthMeters(2.0);
        request.setHeightMeters(1.0);
        request.setLocation("München");

        GardenResponse response = new GardenResponse(
                2L, "Neuer Garten", "Beschreibung", 2.0, 1.0, "München", LocalDateTime.now(), 0);

        when(gardenService.create(any(GardenRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/gardens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Neuer Garten"));
    }

    @Test
    @DisplayName("POST /api/gardens mit leerem Namen gibt 400 zurück")
    void create_returns400_whenNameIsBlank() throws Exception {
        GardenRequest request = new GardenRequest();
        request.setName("");
        request.setLocation("Berlin");
        request.setWidthMeters(2.0);
        request.setHeightMeters(1.0);

        mockMvc.perform(post("/api/gardens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/gardens/{id} gibt 200 zurück bei Aktualisierung")
    void update_returns200_whenUpdated() throws Exception {
        GardenRequest request = new GardenRequest();
        request.setName("Aktualisiert");
        request.setLocation("Hamburg");
        request.setWidthMeters(4.0);
        request.setHeightMeters(2.0);

        GardenResponse response = new GardenResponse(
                1L, "Aktualisiert", null, 4.0, 2.0, "Hamburg", LocalDateTime.now(), 0);

        when(gardenService.update(eq(1L), any(GardenRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/gardens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aktualisiert"));
    }

    @Test
    @DisplayName("DELETE /api/gardens/{id} gibt 204 zurück")
    void delete_returns204_whenDeleted() throws Exception {
        mockMvc.perform(delete("/api/gardens/1"))
                .andExpect(status().isNoContent());
    }
}
