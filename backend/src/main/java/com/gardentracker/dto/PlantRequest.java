package com.gardentracker.dto;

import com.gardentracker.model.PlantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PlantRequest {

    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    private String latinName;

    @NotNull(message = "Kategorie muss angegeben werden")
    private PlantCategory category;

    @Positive
    private Integer daysTillHarvest;

    @Positive
    private Integer spacingCm;

    @Positive
    private Integer wateringIntervalDays;

    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLatinName() { return latinName; }
    public void setLatinName(String latinName) { this.latinName = latinName; }
    public PlantCategory getCategory() { return category; }
    public void setCategory(PlantCategory category) { this.category = category; }
    public Integer getDaysTillHarvest() { return daysTillHarvest; }
    public void setDaysTillHarvest(Integer daysTillHarvest) { this.daysTillHarvest = daysTillHarvest; }
    public Integer getSpacingCm() { return spacingCm; }
    public void setSpacingCm(Integer spacingCm) { this.spacingCm = spacingCm; }
    public Integer getWateringIntervalDays() { return wateringIntervalDays; }
    public void setWateringIntervalDays(Integer wateringIntervalDays) { this.wateringIntervalDays = wateringIntervalDays; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
