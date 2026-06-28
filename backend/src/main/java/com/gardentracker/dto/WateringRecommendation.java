package com.gardentracker.dto;

public class WateringRecommendation {
    private boolean needsWatering;
    private String reason;
    private WeatherResponse weather;

    public WateringRecommendation() {}

    public WateringRecommendation(boolean needsWatering, String reason, WeatherResponse weather) {
        this.needsWatering = needsWatering;
        this.reason = reason;
        this.weather = weather;
    }

    public boolean isNeedsWatering() { return needsWatering; }
    public void setNeedsWatering(boolean needsWatering) { this.needsWatering = needsWatering; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public WeatherResponse getWeather() { return weather; }
    public void setWeather(WeatherResponse weather) { this.weather = weather; }
}
