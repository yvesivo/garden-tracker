package com.gardentracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class GardenRequest {

    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    private String description;

    @Positive(message = "Breite muss positiv sein")
    private double widthMeters;

    @Positive(message = "Höhe muss positiv sein")
    private double heightMeters;

    @NotBlank(message = "Standort darf nicht leer sein")
    private String location;

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
}
