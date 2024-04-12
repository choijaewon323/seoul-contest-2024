package com.jaewon.seoul.controller;

import com.jaewon.seoul.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WeatherApiController {
    private final WeatherService weatherService;

    public WeatherApiController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/v1/weather/{lat}/{lng}")
    public ResponseEntity<Integer> getWeather(@PathVariable Double lat, @PathVariable Double lng) throws Exception {
        final int nx = lat.intValue();
        final int ny = lng.intValue();

        final int temperature = weatherService.getWeather(nx, ny);

        return ResponseEntity.ok(temperature);
    }


}
