package org.example.controllers;

import org.example.dto.WeatherDTO;
import org.example.services.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherDTO> getWeather(
            @RequestParam(value = "cityName", required = false) String cityName,
            @RequestParam(value = "zipCode", required = false) String zipCode) {
        if (cityName == null && zipCode == null) {
            return ResponseEntity.badRequest().build();
        }
        WeatherDTO weather = weatherService.getWeather(cityName, zipCode);
        return ResponseEntity.ok(weather);
    }
}
