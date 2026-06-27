package com.gardentracker.dto;

import com.gardentracker.model.PlantCategory;

public class PlantResponse {
    private Long id;
    private String name;
    private String latinName;
    private PlantCategory category;
    private String description;

    public PlantResponse() {}

    public PlantResponse(Long id, String name, String latinName, PlantCategory category, String description) {
        this.id = id;
        this.name = name;
        this.latinName = latinName;
        this.category = category;
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
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
