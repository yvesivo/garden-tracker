package com.gardentracker.dto;

import com.gardentracker.model.PlantStatus;
import java.time.LocalDate;

public class PlantRecordRequest {

    private Long gardenId;
    private Long plantId;
    private LocalDate plantingDate;
    private PlantStatus status;
    private String notes;

    public Long getGardenId() { return gardenId; }
    public void setGardenId(Long gardenId) { this.gardenId = gardenId; }
    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }
    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }
    public PlantStatus getStatus() { return status; }
    public void setStatus(PlantStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
