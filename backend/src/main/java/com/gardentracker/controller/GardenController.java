package com.gardentracker.controller;

import com.gardentracker.dto.GardenRequest;
import com.gardentracker.dto.GardenResponse;
import com.gardentracker.service.GardenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gardens")
@Tag(name = "Gardens", description = "Gärten und Hochbeete verwalten")
public class GardenController {

    private final GardenService gardenService;

    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    @GetMapping
    @Operation(summary = "Alle Gärten abrufen")
    public List<GardenResponse> findAll() {
        return gardenService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Garten nach ID abrufen")
    public GardenResponse findById(@PathVariable Long id) {
        return gardenService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Neuen Garten anlegen")
    public GardenResponse create(@Valid @RequestBody GardenRequest request) {
        return gardenService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Garten aktualisieren")
    public GardenResponse update(@PathVariable Long id, @Valid @RequestBody GardenRequest request) {
        return gardenService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Garten löschen")
    public void delete(@PathVariable Long id) {
        gardenService.delete(id);
    }
}
