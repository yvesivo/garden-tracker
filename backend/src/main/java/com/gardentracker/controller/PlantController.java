package com.gardentracker.controller;

import com.gardentracker.dto.PlantRequest;
import com.gardentracker.dto.PlantResponse;
import com.gardentracker.model.PlantCategory;
import com.gardentracker.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
@Tag(name = "Plants", description = "Pflanzenkatalog verwalten")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    @Operation(summary = "Alle Pflanzen abrufen")
    public List<PlantResponse> findAll() {
        return plantService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pflanze nach ID abrufen")
    public PlantResponse findById(@PathVariable Long id) {
        return plantService.findById(id);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Pflanzen nach Kategorie filtern")
    public List<PlantResponse> findByCategory(@PathVariable PlantCategory category) {
        return plantService.findByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Neue Pflanze anlegen")
    public PlantResponse create(@Valid @RequestBody PlantRequest request) {
        return plantService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Pflanze aktualisieren")
    public PlantResponse update(@PathVariable Long id, @Valid @RequestBody PlantRequest request) {
        return plantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Pflanze löschen")
    public void delete(@PathVariable Long id) {
        plantService.delete(id);
    }
}
