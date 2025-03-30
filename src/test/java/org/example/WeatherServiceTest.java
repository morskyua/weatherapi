package org.example;

import org.example.dto.WeatherDTO;
import org.example.repositories.WeatherRepository;
import org.example.services.WeatherService;
import org.example.utils.WeatherApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WeatherServiceTest {

    @MockBean
    private WeatherRepository weatherRepository;

    @MockBean
    private WeatherApiClient weatherApiClient;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testGetWeatherByCityName() {
        WeatherDTO mockWeather = new WeatherDTO("London", null, 20.5, "Clear");
        when(weatherApiClient.getWeatherByCityName("London")).thenReturn(mockWeather);

        WeatherDTO returned = weatherService.getWeather("London", null);
        assertEquals("London", returned.getCityName());
        assertEquals(20.5, returned.getTemperature());
    }
}
