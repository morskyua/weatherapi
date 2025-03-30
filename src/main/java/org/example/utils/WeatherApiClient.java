package org.example.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiClient {

    @Value("${api.weather.url}")
    private String apiUrl;

    @Value("${api.weather.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherDTO getWeatherByCityName(String cityName) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);
        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
            System.out.println("API Response: " + response.getBody()); // Debugging
            return mapToWeatherDTO(response.getBody());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error fetching weather data: " + e.getMessage());
        }
    }

    public WeatherDTO getWeatherByZipCode(String zipCode) {
        try {
            String url = String.format("%s?zip=%s&appid=%s", apiUrl, zipCode, apiKey);
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
            return mapToWeatherDTO(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Error fetching weather data: " + e.getResponseBodyAsString());
        }
    }

    private WeatherDTO mapToWeatherDTO(JsonNode jsonNode) {
        String cityName = jsonNode.path("name").asText(null);

        String weatherDescription = null;
        if (jsonNode.has("weather") && jsonNode.get("weather").isArray() && jsonNode.get("weather").size() > 0) {
            weatherDescription = jsonNode.get("weather").get(0).path("description").asText(null);
        }

        Double temperature = jsonNode.path("main").path("temp").asDouble(0.0);

        return new WeatherDTO(
                cityName,
                null,
                temperature,
                weatherDescription
        );
    }
}
