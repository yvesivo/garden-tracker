package com.gardentracker.dto;

import com.gardentracker.model.PlantCategory;
import com.gardentracker.model.PlantStatus;
import java.time.LocalDate;

public class PlantRecordResponse {
    private Long id;
    private Long gardenId;
    private String gardenName;
    private Long plantId;
    private String plantName;
    private PlantCategory plantCategory;
    private LocalDate plantingDate;
    private PlantStatus status;
    private String notes;

    public PlantRecordResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getGardenId() { return gardenId; }
    public void setGardenId(Long gardenId) { this.gardenId = gardenId; }
    public String getGardenName() { return gardenName; }
    public void setGardenName(String gardenName) { this.gardenName = gardenName; }
    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }
    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }
    public PlantCategory getPlantCategory() { return plantCategory; }
    public void setPlantCategory(PlantCategory plantCategory) { this.plantCategory = plantCategory; }
    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }
    public PlantStatus getStatus() { return status; }
    public void setStatus(PlantStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
