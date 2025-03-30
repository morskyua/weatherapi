package org.example.repositories;

import org.example.entities.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findByCityName(String cityName);
    Optional<WeatherEntity> findByZipCode(String zipCode);
}
