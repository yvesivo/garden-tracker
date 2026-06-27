package com.gardentracker.dto;

import java.time.LocalDateTime;

public class GardenResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime createdAt;
    private int plantRecordCount;

    public GardenResponse() {}

    public GardenResponse(Long id, String name, String description, String location,
                          LocalDateTime createdAt, int plantRecordCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
        this.plantRecordCount = plantRecordCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public int getPlantRecordCount() { return plantRecordCount; }
    public void setPlantRecordCount(int plantRecordCount) { this.plantRecordCount = plantRecordCount; }
}
