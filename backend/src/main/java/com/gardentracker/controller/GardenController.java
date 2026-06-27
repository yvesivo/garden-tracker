package com.gardentracker.controller;

import com.gardentracker.dto.GardenRequest;
import com.gardentracker.dto.GardenResponse;
import com.gardentracker.service.GardenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gardens")
public class GardenController {

    private final GardenService gardenService;

    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    @GetMapping
    public List<GardenResponse> findAll() {
        return gardenService.findAll();
    }

    @GetMapping("/{id}")
    public GardenResponse findById(@PathVariable Long id) {
        return gardenService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GardenResponse create(@RequestBody GardenRequest request) {
        return gardenService.create(request);
    }

    @PutMapping("/{id}")
    public GardenResponse update(@PathVariable Long id, @RequestBody GardenRequest request) {
        return gardenService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        gardenService.delete(id);
    }
}
