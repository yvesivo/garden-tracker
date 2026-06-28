package com.gardentracker.controller;

import com.gardentracker.dto.PlantRequest;
import com.gardentracker.dto.PlantResponse;
import com.gardentracker.model.PlantCategory;
import com.gardentracker.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public List<PlantResponse> findAll() {
        return plantService.findAll();
    }

    @GetMapping("/{id}")
    public PlantResponse findById(@PathVariable Long id) {
        return plantService.findById(id);
    }

    @GetMapping("/category/{category}")
    public List<PlantResponse> findByCategory(@PathVariable PlantCategory category) {
        return plantService.findByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantResponse create(@Valid @RequestBody PlantRequest request) {
        return plantService.create(request);
    }

    @PutMapping("/{id}")
    public PlantResponse update(@PathVariable Long id, @Valid @RequestBody PlantRequest request) {
        return plantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        plantService.delete(id);
    }
}
