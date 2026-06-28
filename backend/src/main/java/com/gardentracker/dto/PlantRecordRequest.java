package com.gardentracker.dto;

import com.gardentracker.model.PlantStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PlantRecordRequest {

    @NotNull(message = "Garten muss angegeben werden")
    private Long gardenId;

    @NotNull(message = "Pflanze muss angegeben werden")
    private Long plantId;
    private LocalDate plantingDate;
    private LocalDate expectedHarvestDate;
    private LocalDate actualHarvestDate;
    private Double yieldKg;
    private PlantStatus status;
    private Integer gridX;
    private Integer gridY;
    private String notes;

    public Long getGardenId() { return gardenId; }
    public void setGardenId(Long gardenId) { this.gardenId = gardenId; }
    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }
    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }
    public LocalDate getExpectedHarvestDate() { return expectedHarvestDate; }
    public void setExpectedHarvestDate(LocalDate expectedHarvestDate) { this.expectedHarvestDate = expectedHarvestDate; }
    public LocalDate getActualHarvestDate() { return actualHarvestDate; }
    public void setActualHarvestDate(LocalDate actualHarvestDate) { this.actualHarvestDate = actualHarvestDate; }
    public Double getYieldKg() { return yieldKg; }
    public void setYieldKg(Double yieldKg) { this.yieldKg = yieldKg; }
    public PlantStatus getStatus() { return status; }
    public void setStatus(PlantStatus status) { this.status = status; }
    public Integer getGridX() { return gridX; }
    public void setGridX(Integer gridX) { this.gridX = gridX; }
    public Integer getGridY() { return gridY; }
    public void setGridY(Integer gridY) { this.gridY = gridY; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
