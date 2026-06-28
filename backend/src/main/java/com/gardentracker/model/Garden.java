package com.gardentracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gardens")
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double widthMeters;
    private double heightMeters;
    private String location;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "garden", cascade = CascadeType.ALL)
    private List<PlantRecord> plantRecords = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getWidthMeters() { return widthMeters; }
    public void setWidthMeters(double widthMeters) { this.widthMeters = widthMeters; }
    public double getHeightMeters() { return heightMeters; }
    public void setHeightMeters(double heightMeters) { this.heightMeters = heightMeters; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<PlantRecord> getPlantRecords() { return plantRecords; }
    public void setPlantRecords(List<PlantRecord> plantRecords) { this.plantRecords = plantRecords; }
}
