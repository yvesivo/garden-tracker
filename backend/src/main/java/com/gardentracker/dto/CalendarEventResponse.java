package com.gardentracker.dto;

import java.time.LocalDate;

public class CalendarEventResponse {
    private Long recordId;
    private String plantName;
    private String gardenName;
    private String eventType;
    private LocalDate date;
    private String color;

    public CalendarEventResponse() {}

    public CalendarEventResponse(Long recordId, String plantName, String gardenName,
                                 String eventType, LocalDate date, String color) {
        this.recordId = recordId;
        this.plantName = plantName;
        this.gardenName = gardenName;
        this.eventType = eventType;
        this.date = date;
        this.color = color;
    }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }
    public String getGardenName() { return gardenName; }
    public void setGardenName(String gardenName) { this.gardenName = gardenName; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
