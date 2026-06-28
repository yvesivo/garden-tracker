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
    private LocalDate expectedHarvestDate;
    private LocalDate actualHarvestDate;
    private Double yieldKg;
    private PlantStatus status;
    private Integer gridX;
    private Integer gridY;
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
