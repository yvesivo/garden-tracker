package com.gardentracker.service;

import com.gardentracker.dto.PlantRequest;
import com.gardentracker.dto.PlantResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Plant;
import com.gardentracker.model.PlantCategory;
import com.gardentracker.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @InjectMocks
    private PlantService plantService;

    private Plant samplePlant;

    @BeforeEach
    void setUp() {
        samplePlant = new Plant();
        samplePlant.setId(1L);
        samplePlant.setName("Tomate");
        samplePlant.setLatinName("Solanum lycopersicum");
        samplePlant.setCategory(PlantCategory.GEMÜSE);
        samplePlant.setDaysTillHarvest(70);
        samplePlant.setSpacingCm(60);
        samplePlant.setWateringIntervalDays(2);
        samplePlant.setDescription("Klassische Gartenpflanze");
    }

    @Test
    @DisplayName("findAll gibt alle Pflanzen zurück")
    void findAll_returnsAllPlants() {
        when(plantRepository.findAll()).thenReturn(List.of(samplePlant));

        List<PlantResponse> result = plantService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Tomate");
        assertThat(result.get(0).getCategory()).isEqualTo(PlantCategory.GEMÜSE);
    }

    @Test
    @DisplayName("findById gibt korrekte Pflanze zurück")
    void findById_returnsPlant_whenFound() {
        when(plantRepository.findById(1L)).thenReturn(Optional.of(samplePlant));

        PlantResponse result = plantService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Tomate");
        assertThat(result.getLatinName()).isEqualTo("Solanum lycopersicum");
    }

    @Test
    @DisplayName("findById wirft ResourceNotFoundException für unbekannte ID")
    void findById_throwsResourceNotFoundException_forUnknownId() {
        when(plantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("findByCategory filtert Pflanzen nach Kategorie")
    void findByCategory_returnsPlantsByCategory() {
        when(plantRepository.findByCategory(PlantCategory.GEMÜSE)).thenReturn(List.of(samplePlant));

        List<PlantResponse> result = plantService.findByCategory(PlantCategory.GEMÜSE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(PlantCategory.GEMÜSE);
        verify(plantRepository, times(1)).findByCategory(PlantCategory.GEMÜSE);
    }

    @Test
    @DisplayName("findByCategory gibt leere Liste für Kategorie ohne Pflanzen zurück")
    void findByCategory_returnsEmptyList_whenNoPlantsInCategory() {
        when(plantRepository.findByCategory(PlantCategory.OBST)).thenReturn(Collections.emptyList());

        List<PlantResponse> result = plantService.findByCategory(PlantCategory.OBST);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("create speichert Pflanze und gibt Response zurück")
    void create_savesPlantAndReturnsResponse() {
        PlantRequest request = new PlantRequest();
        request.setName("Gurke");
        request.setLatinName("Cucumis sativus");
        request.setCategory(PlantCategory.GEMÜSE);
        request.setDaysTillHarvest(60);
        request.setSpacingCm(50);
        request.setWateringIntervalDays(1);
        request.setDescription("Liebt Wärme");

        Plant savedPlant = new Plant();
        savedPlant.setId(2L);
        savedPlant.setName("Gurke");
        savedPlant.setLatinName("Cucumis sativus");
        savedPlant.setCategory(PlantCategory.GEMÜSE);
        savedPlant.setDaysTillHarvest(60);

        when(plantRepository.save(any(Plant.class))).thenReturn(savedPlant);

        PlantResponse result = plantService.create(request);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Gurke");
        assertThat(result.getDaysTillHarvest()).isEqualTo(60);
        verify(plantRepository, times(1)).save(any(Plant.class));
    }

    @Test
    @DisplayName("update wirft ResourceNotFoundException für unbekannte ID")
    void update_throwsResourceNotFoundException_forUnknownId() {
        PlantRequest request = new PlantRequest();
        request.setName("Test");
        request.setCategory(PlantCategory.GEMÜSE);

        when(plantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantService.update(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("delete löscht Pflanze erfolgreich")
    void delete_deletesPlant_whenFound() {
        when(plantRepository.existsById(1L)).thenReturn(true);

        plantService.delete(1L);

        verify(plantRepository, times(1)).existsById(1L);
        verify(plantRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete wirft ResourceNotFoundException für unbekannte ID")
    void delete_throwsResourceNotFoundException_forUnknownId() {
        when(plantRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> plantService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(plantRepository, never()).deleteById(any());
    }
}
