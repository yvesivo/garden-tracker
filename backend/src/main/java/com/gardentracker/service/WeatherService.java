package com.gardentracker.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gardentracker.dto.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.create();

    public WeatherResponse getWeather(String location) {
        if (apiKey == null || apiKey.isBlank() || "REPLACE_WITH_YOUR_KEY".equals(apiKey)) {
            log.warn("Kein OpenWeatherMap-Key konfiguriert – liefere Demo-Wetterdaten.");
            return createMockWeather(location);
        }
        try {
            OpenWeatherCurrentResponse owm = restClient.get()
                    .uri(baseUrl + "/weather?q={loc}&appid={key}&units=metric&lang=de", location, apiKey)
                    .retrieve()
                    .body(OpenWeatherCurrentResponse.class);

            if (owm == null) {
                return createMockWeather(location);
            }

            boolean rainExpected = false;
            double rainAmount = 0.0;
            try {
                OpenWeatherForecastResponse forecast = restClient.get()
                        .uri(baseUrl + "/forecast?q={loc}&appid={key}&units=metric&lang=de&cnt=8", location, apiKey)
                        .retrieve()
                        .body(OpenWeatherForecastResponse.class);
                if (forecast != null && forecast.list() != null) {
                    for (var item : forecast.list()) {
                        if (item.rain() != null) {
                            Double amount = item.rain().get("3h");
                            if (amount != null && amount > 0) {
                                rainExpected = true;
                                rainAmount += amount;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("Vorhersage konnte nicht geladen werden: {}", e.getMessage());
            }

            WeatherResponse response = new WeatherResponse();
            response.setLocation(owm.name());
            response.setTemperature(owm.main().temp());
            response.setFeelsLike(owm.main().feels_like());
            response.setHumidity(owm.main().humidity());
            response.setWindSpeed(owm.wind() != null ? owm.wind().speed() : 0.0);
            response.setRainExpectedNext24h(rainExpected);
            response.setRainAmountMm(Math.round(rainAmount * 10.0) / 10.0);
            if (owm.weather() != null && !owm.weather().isEmpty()) {
                response.setDescription(owm.weather().get(0).description());
                response.setIcon(owm.weather().get(0).icon());
            }
            return response;
        } catch (RestClientException e) {
            log.error("Wetter-API Fehler für '{}': {}", location, e.getMessage());
            return createMockWeather(location);
        }
    }

    private WeatherResponse createMockWeather(String location) {
        WeatherResponse mock = new WeatherResponse();
        mock.setLocation(location);
        mock.setTemperature(18.5);
        mock.setFeelsLike(17.0);
        mock.setHumidity(65);
        mock.setDescription("Leicht bewölkt (Demo-Daten)");
        mock.setIcon("02d");
        mock.setWindSpeed(3.5);
        mock.setRainExpectedNext24h(false);
        mock.setRainAmountMm(0.0);
        return mock;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record OpenWeatherCurrentResponse(String name, MainData main, List<WeatherCondition> weather, WindData wind) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record MainData(double temp, double feels_like, int humidity) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record WeatherCondition(int id, String description, String icon) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record WindData(double speed) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record OpenWeatherForecastResponse(List<ForecastItem> list) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ForecastItem(MainData main, List<WeatherCondition> weather, @JsonProperty("rain") Map<String, Double> rain) {}
}
