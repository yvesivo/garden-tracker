package com.gardentracker.service;

import com.gardentracker.dto.GardenRequest;
import com.gardentracker.dto.GardenResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Garden;
import com.gardentracker.repository.GardenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GardenService {

    private final GardenRepository gardenRepository;

    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    public List<GardenResponse> findAll() {
        return gardenRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public GardenResponse findById(Long id) {
        Garden garden = gardenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garten nicht gefunden: " + id));
        return toResponse(garden);
    }

    public GardenResponse create(GardenRequest request) {
        Garden garden = new Garden();
        garden.setCreatedAt(LocalDateTime.now());
        updateFromRequest(garden, request);
        return toResponse(gardenRepository.save(garden));
    }

    public GardenResponse update(Long id, GardenRequest request) {
        Garden garden = gardenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garten nicht gefunden: " + id));
        updateFromRequest(garden, request);
        return toResponse(gardenRepository.save(garden));
    }

    public void delete(Long id) {
        if (!gardenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Garten nicht gefunden: " + id);
        }
        gardenRepository.deleteById(id);
    }

    private void updateFromRequest(Garden garden, GardenRequest request) {
        garden.setName(request.getName());
        garden.setDescription(request.getDescription());
        garden.setLocation(request.getLocation());
    }

    private GardenResponse toResponse(Garden garden) {
        return new GardenResponse(
                garden.getId(),
                garden.getName(),
                garden.getDescription(),
                garden.getLocation(),
                garden.getCreatedAt(),
                garden.getPlantRecords().size()
        );
    }
}
