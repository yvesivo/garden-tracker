package com.gardentracker.dto;

import com.gardentracker.model.PlantCategory;

public class PlantResponse {
    private Long id;
    private String name;
    private String latinName;
    private PlantCategory category;
    private Integer daysTillHarvest;
    private Integer spacingCm;
    private Integer wateringIntervalDays;
    private String description;

    public PlantResponse() {}

    public PlantResponse(Long id, String name, String latinName, PlantCategory category,
                         Integer daysTillHarvest, Integer spacingCm, Integer wateringIntervalDays, String description) {
        this.id = id;
        this.name = name;
        this.latinName = latinName;
        this.category = category;
        this.daysTillHarvest = daysTillHarvest;
        this.spacingCm = spacingCm;
        this.wateringIntervalDays = wateringIntervalDays;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
