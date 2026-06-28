package com.gardentracker.dto;

public class WeatherResponse {
    private String location;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String description;
    private String icon;
    private double windSpeed;
    private boolean rainExpectedNext24h;
    private double rainAmountMm;

    public WeatherResponse() {}

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public double getFeelsLike() { return feelsLike; }
    public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    public boolean isRainExpectedNext24h() { return rainExpectedNext24h; }
    public void setRainExpectedNext24h(boolean rainExpectedNext24h) { this.rainExpectedNext24h = rainExpectedNext24h; }
    public double getRainAmountMm() { return rainAmountMm; }
    public void setRainAmountMm(double rainAmountMm) { this.rainAmountMm = rainAmountMm; }
}
