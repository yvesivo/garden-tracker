package com.gardentracker.service;

import com.gardentracker.dto.PlantRecordRequest;
import com.gardentracker.dto.PlantRecordResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Garden;
import com.gardentracker.model.Plant;
import com.gardentracker.model.PlantRecord;
import com.gardentracker.repository.GardenRepository;
import com.gardentracker.repository.PlantRecordRepository;
import com.gardentracker.repository.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PlantRecordService {

    private final PlantRecordRepository plantRecordRepository;
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;

    public PlantRecordService(PlantRecordRepository plantRecordRepository,
                              GardenRepository gardenRepository,
                              PlantRepository plantRepository) {
        this.plantRecordRepository = plantRecordRepository;
        this.gardenRepository = gardenRepository;
        this.plantRepository = plantRepository;
    }

    public List<PlantRecordResponse> findAll() {
        return plantRecordRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PlantRecordResponse findById(Long id) {
        PlantRecord record = plantRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eintrag nicht gefunden: " + id));
        return toResponse(record);
    }

    public List<PlantRecordResponse> findByGardenId(Long gardenId) {
        return plantRecordRepository.findByGardenId(gardenId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PlantRecordResponse create(PlantRecordRequest request) {
        PlantRecord record = new PlantRecord();
        updateFromRequest(record, request);
        return toResponse(plantRecordRepository.save(record));
    }

    public PlantRecordResponse update(Long id, PlantRecordRequest request) {
        PlantRecord record = plantRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eintrag nicht gefunden: " + id));
        updateFromRequest(record, request);
        return toResponse(plantRecordRepository.save(record));
    }

    public void delete(Long id) {
        if (!plantRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Eintrag nicht gefunden: " + id);
        }
        plantRecordRepository.deleteById(id);
    }

    private void updateFromRequest(PlantRecord record, PlantRecordRequest request) {
        Garden garden = gardenRepository.findById(request.getGardenId())
                .orElseThrow(() -> new ResourceNotFoundException("Garten nicht gefunden: " + request.getGardenId()));
        Plant plant = plantRepository.findById(request.getPlantId())
                .orElseThrow(() -> new ResourceNotFoundException("Pflanze nicht gefunden: " + request.getPlantId()));
        record.setGarden(garden);
        record.setPlant(plant);
        record.setPlantingDate(request.getPlantingDate());
        if (request.getStatus() != null) {
            record.setStatus(request.getStatus());
        }
        record.setNotes(request.getNotes());
    }

    private PlantRecordResponse toResponse(PlantRecord record) {
        PlantRecordResponse response = new PlantRecordResponse();
        response.setId(record.getId());
        response.setGardenId(record.getGarden().getId());
        response.setGardenName(record.getGarden().getName());
        response.setPlantId(record.getPlant().getId());
        response.setPlantName(record.getPlant().getName());
        response.setPlantCategory(record.getPlant().getCategory());
        response.setPlantingDate(record.getPlantingDate());
        response.setStatus(record.getStatus());
        response.setNotes(record.getNotes());
        return response;
    }
}
