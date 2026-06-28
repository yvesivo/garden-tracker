package com.gardentracker.dto;

import jakarta.validation.constraints.NotBlank;

public class GardenRequest {

    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    private String description;

    @NotBlank(message = "Standort darf nicht leer sein")
    private String location;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
