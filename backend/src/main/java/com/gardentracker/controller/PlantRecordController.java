package com.gardentracker.controller;

import com.gardentracker.dto.CalendarEventResponse;
import com.gardentracker.dto.PlantRecordRequest;
import com.gardentracker.dto.PlantRecordResponse;
import com.gardentracker.dto.WateringRecommendation;
import com.gardentracker.model.PlantStatus;
import com.gardentracker.service.PlantRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@Tag(name = "PlantRecords", description = "Pflanzeinträge verwalten")
public class PlantRecordController {

    private final PlantRecordService plantRecordService;

    public PlantRecordController(PlantRecordService plantRecordService) {
        this.plantRecordService = plantRecordService;
    }

    @GetMapping
    @Operation(summary = "Alle Einträge abrufen")
    public List<PlantRecordResponse> findAll() {
        return plantRecordService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Eintrag nach ID abrufen")
    public PlantRecordResponse findById(@PathVariable Long id) {
        return plantRecordService.findById(id);
    }

    @GetMapping("/garden/{gardenId}")
    @Operation(summary = "Einträge eines Gartens abrufen")
    public List<PlantRecordResponse> findByGarden(@PathVariable Long gardenId) {
        return plantRecordService.findByGardenId(gardenId);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Einträge nach Status filtern")
    public List<PlantRecordResponse> findByStatus(@PathVariable PlantStatus status) {
        return plantRecordService.findByStatus(status);
    }

    @GetMapping("/calendar")
    @Operation(summary = "Kalender-Events abrufen (Pflanz- und Erntedaten)")
    public List<CalendarEventResponse> getCalendarEvents() {
        return plantRecordService.getCalendarEvents();
    }

    @GetMapping("/garden/{gardenId}/watering")
    @Operation(summary = "Gießempfehlung für einen Garten")
    public WateringRecommendation getWateringRecommendation(@PathVariable Long gardenId) {
        return plantRecordService.getWateringRecommendation(gardenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Neuen Eintrag anlegen")
    public PlantRecordResponse create(@Valid @RequestBody PlantRecordRequest request) {
        return plantRecordService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Eintrag aktualisieren")
    public PlantRecordResponse update(@PathVariable Long id, @Valid @RequestBody PlantRecordRequest request) {
        return plantRecordService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eintrag löschen")
    public void delete(@PathVariable Long id) {
        plantRecordService.delete(id);
    }
}
