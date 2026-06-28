package com.gardentracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plant_records")
public class PlantRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private LocalDate plantingDate;
    private LocalDate expectedHarvestDate;
    private LocalDate actualHarvestDate;
    private Double yieldKg;

    @Enumerated(EnumType.STRING)
    private PlantStatus status = PlantStatus.PLANNED;

    private Integer gridX;
    private Integer gridY;

    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Garden getGarden() { return garden; }
    public void setGarden(Garden garden) { this.garden = garden; }
    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }
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
