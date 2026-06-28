package com.gardentracker.service;

import com.gardentracker.dto.GardenRequest;
import com.gardentracker.dto.GardenResponse;
import com.gardentracker.exception.ResourceNotFoundException;
import com.gardentracker.model.Garden;
import com.gardentracker.repository.GardenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GardenServiceTest {

    @Mock
    private GardenRepository gardenRepository;

    @InjectMocks
    private GardenService gardenService;

    private Garden sampleGarden;

    @BeforeEach
    void setUp() {
        sampleGarden = new Garden();
        sampleGarden.setId(1L);
        sampleGarden.setName("Testgarten");
        sampleGarden.setDescription("Ein Testgarten");
        sampleGarden.setWidthMeters(3.0);
        sampleGarden.setHeightMeters(1.5);
        sampleGarden.setLocation("Berlin");
        sampleGarden.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("findAll gibt leere Liste zurück wenn keine Gärten vorhanden")
    void findAll_returnsEmptyList_whenNoGardens() {
        when(gardenRepository.findAll()).thenReturn(Collections.emptyList());

        List<GardenResponse> result = gardenService.findAll();

        assertThat(result).isEmpty();
        verify(gardenRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll gibt alle Gärten zurück")
    void findAll_returnsAllGardens() {
        when(gardenRepository.findAll()).thenReturn(List.of(sampleGarden));

        List<GardenResponse> result = gardenService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Testgarten");
        assertThat(result.get(0).getLocation()).isEqualTo("Berlin");
    }

    @Test
    @DisplayName("findById wirft ResourceNotFoundException für unbekannte ID")
    void findById_throwsResourceNotFoundException_forUnknownId() {
        when(gardenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gardenService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("findById gibt korrekten Garten zurück")
    void findById_returnsGarden_whenFound() {
        when(gardenRepository.findById(1L)).thenReturn(Optional.of(sampleGarden));

        GardenResponse result = gardenService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Testgarten");
    }

    @Test
    @DisplayName("create speichert Garten und gibt Response zurück")
    void create_savesGardenAndReturnsResponse() {
        GardenRequest request = new GardenRequest();
        request.setName("Neuer Garten");
        request.setDescription("Beschreibung");
        request.setWidthMeters(2.0);
        request.setHeightMeters(1.0);
        request.setLocation("München");

        Garden savedGarden = new Garden();
        savedGarden.setId(2L);
        savedGarden.setName("Neuer Garten");
        savedGarden.setWidthMeters(2.0);
        savedGarden.setHeightMeters(1.0);
        savedGarden.setLocation("München");
        savedGarden.setCreatedAt(LocalDateTime.now());

        when(gardenRepository.save(any(Garden.class))).thenReturn(savedGarden);

        GardenResponse result = gardenService.create(request);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Neuer Garten");
        assertThat(result.getLocation()).isEqualTo("München");
        verify(gardenRepository, times(1)).save(any(Garden.class));
    }

    @Test
    @DisplayName("update wirft ResourceNotFoundException für unbekannte ID")
    void update_throwsResourceNotFoundException_forUnknownId() {
        GardenRequest request = new GardenRequest();
        request.setName("Test");
        request.setLocation("Berlin");
        request.setWidthMeters(1.0);
        request.setHeightMeters(1.0);

        when(gardenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gardenService.update(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("delete löscht Garten erfolgreich")
    void delete_deletesGarden_whenFound() {
        when(gardenRepository.existsById(1L)).thenReturn(true);

        gardenService.delete(1L);

        verify(gardenRepository, times(1)).existsById(1L);
        verify(gardenRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete wirft ResourceNotFoundException für unbekannte ID")
    void delete_throwsResourceNotFoundException_forUnknownId() {
        when(gardenRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> gardenService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(gardenRepository, never()).deleteById(any());
    }
}
