package com.gardentracker.dto;

import com.gardentracker.model.PlantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlantRequest {

    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    private String latinName;

    @NotNull(message = "Kategorie muss angegeben werden")
    private PlantCategory category;

    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLatinName() { return latinName; }
    public void setLatinName(String latinName) { this.latinName = latinName; }
    public PlantCategory getCategory() { return category; }
    public void setCategory(PlantCategory category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
