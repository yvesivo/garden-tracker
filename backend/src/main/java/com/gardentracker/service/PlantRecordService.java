package com.gardentracker.service;

import com.gardentracker.dto.CalendarEventResponse;
import com.gardentracker.dto.PlantRecordRequest;
import com.gardentracker.dto.PlantRecordResponse;
import com.gardentracker.dto.WateringRecommendation;
import com.gardentracker.dto.WeatherResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Garden;
import com.gardentracker.model.Plant;
import com.gardentracker.model.PlantRecord;
import com.gardentracker.model.PlantStatus;
import com.gardentracker.repository.GardenRepository;
import com.gardentracker.repository.PlantRecordRepository;
import com.gardentracker.repository.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlantRecordService {

    private final PlantRecordRepository plantRecordRepository;
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final WeatherService weatherService;

    public PlantRecordService(PlantRecordRepository plantRecordRepository,
                              GardenRepository gardenRepository,
                              PlantRepository plantRepository,
                              WeatherService weatherService) {
        this.plantRecordRepository = plantRecordRepository;
        this.gardenRepository = gardenRepository;
        this.plantRepository = plantRepository;
        this.weatherService = weatherService;
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

    public List<PlantRecordResponse> findByStatus(PlantStatus status) {
        return plantRecordRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CalendarEventResponse> getCalendarEvents() {
        List<CalendarEventResponse> events = new ArrayList<>();
        for (PlantRecord record : plantRecordRepository.findAll()) {
            String plantName = record.getPlant().getName();
            String gardenName = record.getGarden().getName();
            if (record.getPlantingDate() != null) {
                events.add(new CalendarEventResponse(record.getId(), plantName, gardenName,
                        "PLANTING", record.getPlantingDate(), "#4caf50"));
            }
            if (record.getExpectedHarvestDate() != null) {
                events.add(new CalendarEventResponse(record.getId(), plantName, gardenName,
                        "EXPECTED_HARVEST", record.getExpectedHarvestDate(), "#ff9800"));
            }
            if (record.getActualHarvestDate() != null) {
                events.add(new CalendarEventResponse(record.getId(), plantName, gardenName,
                        "ACTUAL_HARVEST", record.getActualHarvestDate(), "#f44336"));
            }
        }
        return events;
    }

    public WateringRecommendation getWateringRecommendation(Long gardenId) {
        Garden garden = gardenRepository.findById(gardenId)
                .orElseThrow(() -> new ResourceNotFoundException("Garten nicht gefunden: " + gardenId));
        WeatherResponse weather = weatherService.getWeather(garden.getLocation());
        if (weather == null) {
            return new WateringRecommendation(true, "Wetterdaten nicht verfügbar – sicherheitshalber gießen", null);
        }
        if (weather.isRainExpectedNext24h()) {
            return new WateringRecommendation(false,
                    "Kein Gießen nötig – Regen in den nächsten 24 Stunden erwartet (" + weather.getRainAmountMm() + " mm)", weather);
        }
        if (weather.getHumidity() > 80) {
            return new WateringRecommendation(false,
                    "Kein Gießen nötig – Luftfeuchtigkeit sehr hoch (" + weather.getHumidity() + "%)", weather);
        }
        if (weather.getTemperature() > 28) {
            return new WateringRecommendation(true,
                    "Gießen empfohlen – hohe Temperatur (" + weather.getTemperature() + "°C) und kein Regen erwartet", weather);
        }
        return new WateringRecommendation(true, "Gießen empfohlen – kein Regen erwartet", weather);
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
        record.setExpectedHarvestDate(request.getExpectedHarvestDate());
        record.setActualHarvestDate(request.getActualHarvestDate());
        record.setYieldKg(request.getYieldKg());
        if (request.getStatus() != null) {
            record.setStatus(request.getStatus());
        }
        record.setGridX(request.getGridX());
        record.setGridY(request.getGridY());
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
        response.setExpectedHarvestDate(record.getExpectedHarvestDate());
        response.setActualHarvestDate(record.getActualHarvestDate());
        response.setYieldKg(record.getYieldKg());
        response.setStatus(record.getStatus());
        response.setGridX(record.getGridX());
        response.setGridY(record.getGridY());
        response.setNotes(record.getNotes());
        return response;
    }
}
