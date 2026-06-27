package com.gardentracker.service;

import com.gardentracker.dto.PlantRequest;
import com.gardentracker.dto.PlantResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Plant;
import com.gardentracker.model.PlantCategory;
import com.gardentracker.repository.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<PlantResponse> findAll() {
        return plantRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PlantResponse findById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pflanze nicht gefunden: " + id));
        return toResponse(plant);
    }

    public List<PlantResponse> findByCategory(PlantCategory category) {
        return plantRepository.findByCategory(category).stream()
                .map(this::toResponse)
                .toList();
    }

    public PlantResponse create(PlantRequest request) {
        Plant plant = new Plant();
        updateFromRequest(plant, request);
        return toResponse(plantRepository.save(plant));
    }

    public PlantResponse update(Long id, PlantRequest request) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pflanze nicht gefunden: " + id));
        updateFromRequest(plant, request);
        return toResponse(plantRepository.save(plant));
    }

    public void delete(Long id) {
        if (!plantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pflanze nicht gefunden: " + id);
        }
        plantRepository.deleteById(id);
    }

    private void updateFromRequest(Plant plant, PlantRequest request) {
        plant.setName(request.getName());
        plant.setLatinName(request.getLatinName());
        plant.setCategory(request.getCategory());
        plant.setDescription(request.getDescription());
    }

    private PlantResponse toResponse(Plant plant) {
        return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getLatinName(),
                plant.getCategory(),
                plant.getDescription()
        );
    }
}
