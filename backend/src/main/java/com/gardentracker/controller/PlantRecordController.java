package com.gardentracker.controller;

import com.gardentracker.dto.CalendarEventResponse;
import com.gardentracker.dto.PlantRecordRequest;
import com.gardentracker.dto.PlantRecordResponse;
import com.gardentracker.dto.WateringRecommendation;
import com.gardentracker.model.PlantStatus;
import com.gardentracker.service.PlantRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/records")
public class PlantRecordController {

    private final PlantRecordService plantRecordService;

    public PlantRecordController(PlantRecordService plantRecordService) {
        this.plantRecordService = plantRecordService;
    }

    @GetMapping
    public List<PlantRecordResponse> findAll() {
        return plantRecordService.findAll();
    }

    @GetMapping("/{id}")
    public PlantRecordResponse findById(@PathVariable Long id) {
        return plantRecordService.findById(id);
    }

    @GetMapping("/garden/{gardenId}")
    public List<PlantRecordResponse> findByGarden(@PathVariable Long gardenId) {
        return plantRecordService.findByGardenId(gardenId);
    }

    @GetMapping("/status/{status}")
    public List<PlantRecordResponse> findByStatus(@PathVariable PlantStatus status) {
        return plantRecordService.findByStatus(status);
    }

    @GetMapping("/calendar")
    public List<CalendarEventResponse> getCalendarEvents() {
        return plantRecordService.getCalendarEvents();
    }

    @GetMapping("/garden/{gardenId}/watering")
    public WateringRecommendation getWateringRecommendation(@PathVariable Long gardenId) {
        return plantRecordService.getWateringRecommendation(gardenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantRecordResponse create(@Valid @RequestBody PlantRecordRequest request) {
        return plantRecordService.create(request);
    }

    @PutMapping("/{id}")
    public PlantRecordResponse update(@PathVariable Long id, @Valid @RequestBody PlantRecordRequest request) {
        return plantRecordService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        plantRecordService.delete(id);
    }
}
