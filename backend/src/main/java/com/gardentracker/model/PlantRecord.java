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

    @Enumerated(EnumType.STRING)
    private PlantStatus status = PlantStatus.PLANNED;

    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Garden getGarden() { return garden; }
    public void setGarden(Garden garden) { this.garden = garden; }
    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }
    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }
    public PlantStatus getStatus() { return status; }
    public void setStatus(PlantStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
