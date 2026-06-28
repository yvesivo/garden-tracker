package com.gardentracker.controller;

import com.gardentracker.dto.WeatherResponse;
import com.gardentracker.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Wetterdaten abrufen")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    @Operation(summary = "Aktuelle Wetterdaten für einen Standort")
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String location) {
        WeatherResponse response = weatherService.getWeather(location);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
