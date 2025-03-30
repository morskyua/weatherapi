package org.example.services;

import org.example.dto.WeatherDTO;
import org.example.entities.WeatherEntity;
import org.example.repositories.WeatherRepository;
import org.example.utils.WeatherApiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherRepository weatherRepository, WeatherApiClient weatherApiClient) {
        this.weatherRepository = weatherRepository;
        this.weatherApiClient = weatherApiClient;
    }

    public WeatherDTO getWeather(String cityName, String zipCode) {
        Optional<WeatherEntity> weatherEntity = (cityName != null) ?
                weatherRepository.findByCityName(cityName) :
                weatherRepository.findByZipCode(zipCode);

        if (weatherEntity.isPresent()) {
            WeatherEntity entity = weatherEntity.get();
            return new WeatherDTO(entity.getCityName(), entity.getZipCode(), entity.getTemperature(), entity.getWeatherDescription());
        }

        WeatherDTO weather;
        if (cityName != null) {
            weather = weatherApiClient.getWeatherByCityName(cityName);
        } else {
            weather = weatherApiClient.getWeatherByZipCode(zipCode);
        }

        saveWeather(weather);
        return weather;
    }

    @Transactional
    public void saveWeather(WeatherDTO weatherDTO) {
        WeatherEntity entity = new WeatherEntity(null, weatherDTO.getCityName(), weatherDTO.getZipCode(), weatherDTO.getTemperature(), weatherDTO.getWeatherDescription());
        weatherRepository.save(entity);
    }

    @Scheduled(fixedRateString = "${scheduler.refresh.interval}")
    public void refreshWeatherData() {
        List<WeatherEntity> entities = weatherRepository.findAll();
        for (WeatherEntity entity : entities) {
            WeatherDTO refreshedData = weatherApiClient.getWeatherByCityName(entity.getCityName());
            entity.setTemperature(refreshedData.getTemperature());
            entity.setWeatherDescription(refreshedData.getWeatherDescription());
            weatherRepository.save(entity);
        }
    }
}
