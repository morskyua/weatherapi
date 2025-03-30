package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "weather_description", nullable = false)
    private String weatherDescription;

    public WeatherEntity() {
    }

    public WeatherEntity(Long id, String cityName, String zipCode, Double temperature, String weatherDescription) {
        this.id = id;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
