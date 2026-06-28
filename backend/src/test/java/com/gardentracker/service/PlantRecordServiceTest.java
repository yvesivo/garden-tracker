package com.gardentracker.service;

import com.gardentracker.dto.PlantRecordRequest;
import com.gardentracker.dto.PlantRecordResponse;
import com.gardentracker.dto.WateringRecommendation;
import com.gardentracker.dto.WeatherResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.*;
import com.gardentracker.repository.GardenRepository;
import com.gardentracker.repository.PlantRecordRepository;
import com.gardentracker.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantRecordServiceTest {

    @Mock
    private PlantRecordRepository plantRecordRepository;
    @Mock
    private GardenRepository gardenRepository;
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private PlantRecordService plantRecordService;

    private Garden sampleGarden;
    private Plant samplePlant;
    private PlantRecord sampleRecord;

    @BeforeEach
    void setUp() {
        sampleGarden = new Garden();
        sampleGarden.setId(1L);
        sampleGarden.setName("Hauptbeet");
        sampleGarden.setLocation("Berlin");
        sampleGarden.setWidthMeters(3.0);
        sampleGarden.setHeightMeters(1.5);
        sampleGarden.setCreatedAt(LocalDateTime.now());

        samplePlant = new Plant();
        samplePlant.setId(1L);
        samplePlant.setName("Tomate");
        samplePlant.setCategory(PlantCategory.GEMÜSE);

        sampleRecord = new PlantRecord();
        sampleRecord.setId(1L);
        sampleRecord.setGarden(sampleGarden);
        sampleRecord.setPlant(samplePlant);
        sampleRecord.setPlantingDate(LocalDate.of(2026, 4, 15));
        sampleRecord.setExpectedHarvestDate(LocalDate.of(2026, 6, 24));
        sampleRecord.setStatus(PlantStatus.GROWING);
    }

    @Test
    @DisplayName("findAll gibt alle Einträge zurück")
    void findAll_returnsAllRecords() {
        when(plantRecordRepository.findAll()).thenReturn(List.of(sampleRecord));

        List<PlantRecordResponse> result = plantRecordService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPlantName()).isEqualTo("Tomate");
        assertThat(result.get(0).getGardenName()).isEqualTo("Hauptbeet");
    }

    @Test
    @DisplayName("findByGardenId gibt Einträge des korrekten Gartens zurück")
    void findByGardenId_returnsRecordsForGarden() {
        when(plantRecordRepository.findByGardenId(1L)).thenReturn(List.of(sampleRecord));

        List<PlantRecordResponse> result = plantRecordService.findByGardenId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGardenId()).isEqualTo(1L);
        verify(plantRecordRepository, times(1)).findByGardenId(1L);
    }

    @Test
    @DisplayName("create sucht Garten und Pflanze und speichert Eintrag")
    void create_looksUpGardenAndPlantAndSavesRecord() {
        PlantRecordRequest request = new PlantRecordRequest();
        request.setGardenId(1L);
        request.setPlantId(1L);
        request.setPlantingDate(LocalDate.of(2026, 5, 1));
        request.setStatus(PlantStatus.PLANNED);

        PlantRecord savedRecord = new PlantRecord();
        savedRecord.setId(2L);
        savedRecord.setGarden(sampleGarden);
        savedRecord.setPlant(samplePlant);
        savedRecord.setStatus(PlantStatus.PLANNED);

        when(gardenRepository.findById(1L)).thenReturn(Optional.of(sampleGarden));
        when(plantRepository.findById(1L)).thenReturn(Optional.of(samplePlant));
        when(plantRecordRepository.save(any(PlantRecord.class))).thenReturn(savedRecord);

        PlantRecordResponse result = plantRecordService.create(request);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getGardenId()).isEqualTo(1L);
        assertThat(result.getPlantId()).isEqualTo(1L);
        verify(plantRecordRepository, times(1)).save(any(PlantRecord.class));
    }

    @Test
    @DisplayName("create wirft ResourceNotFoundException wenn Garten nicht gefunden")
    void create_throwsResourceNotFoundException_whenGardenNotFound() {
        PlantRecordRequest request = new PlantRecordRequest();
        request.setGardenId(99L);
        request.setPlantId(1L);

        when(gardenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantRecordService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("getWateringRecommendation: kein Gießen wenn Regen erwartet")
    void getWateringRecommendation_returnsNoWatering_whenRainExpected() {
        WeatherResponse weather = new WeatherResponse();
        weather.setLocation("Berlin");
        weather.setRainExpectedNext24h(true);
        weather.setRainAmountMm(5.0);

        when(gardenRepository.findById(1L)).thenReturn(Optional.of(sampleGarden));
        when(weatherService.getWeather("Berlin")).thenReturn(weather);

        WateringRecommendation result = plantRecordService.getWateringRecommendation(1L);

        assertThat(result.isNeedsWatering()).isFalse();
        assertThat(result.getReason()).contains("Regen");
    }

    @Test
    @DisplayName("getWateringRecommendation: Gießen empfohlen bei hoher Temperatur")
    void getWateringRecommendation_returnsWatering_whenHighTemperature() {
        WeatherResponse weather = new WeatherResponse();
        weather.setLocation("Berlin");
        weather.setTemperature(32.0);
        weather.setHumidity(40);
        weather.setRainExpectedNext24h(false);

        when(gardenRepository.findById(1L)).thenReturn(Optional.of(sampleGarden));
        when(weatherService.getWeather("Berlin")).thenReturn(weather);

        WateringRecommendation result = plantRecordService.getWateringRecommendation(1L);

        assertThat(result.isNeedsWatering()).isTrue();
        assertThat(result.getReason()).contains("Temperatur");
    }

    @Test
    @DisplayName("getWateringRecommendation wirft ResourceNotFoundException wenn Garten nicht gefunden")
    void getWateringRecommendation_throwsException_whenGardenNotFound() {
        when(gardenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantRecordService.getWateringRecommendation(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("getCalendarEvents erzeugt Events aus Pflanz- und Erntedaten")
    void getCalendarEvents_buildsEventsFromDates() {
        when(plantRecordRepository.findAll()).thenReturn(List.of(sampleRecord));

        var events = plantRecordService.getCalendarEvents();

        // Pflanzdatum + erwartetes Erntedatum = 2 Events
        assertThat(events).hasSize(2);
        assertThat(events).anyMatch(e -> e.getEventType().equals("PLANTING"));
        assertThat(events).anyMatch(e -> e.getEventType().equals("EXPECTED_HARVEST"));
    }
}
