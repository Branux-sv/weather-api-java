package org.branux.weather.controller;

import org.branux.weather.dto.WeatherDto;

import org.branux.weather.dto.WeatherDtoReq;
import org.branux.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class WeatherApiRestController {

  private WeatherService weatherService;

  @Autowired
  public WeatherApiRestController(WeatherService weatherService){
    this.weatherService = weatherService;
  }

  @PostMapping( value ="/weather", consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<WeatherDto> addWeather(@Valid @RequestBody WeatherDtoReq weather){
    WeatherDto newWeather = weatherService.createWeather(weather);
    return new ResponseEntity<>(newWeather, HttpStatus.CREATED);
  }

  @GetMapping( value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<WeatherDto>> getWeathers(
          @RequestParam( value = "city", defaultValue = "all") String city,
          @RequestParam( value = "date", defaultValue = "all") String date,
          @RequestParam( value = "sort", defaultValue = "all") String sort) {

    List<WeatherDto> listWeather = new ArrayList<>(weatherService.getWeathers(date, city, sort));
    return new ResponseEntity<>(listWeather,HttpStatus.OK);
  }

  @GetMapping( value = "/weather/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<WeatherDto> getWeatherById(@PathVariable("id") int id) {
    Optional<WeatherDto> weather = weatherService.getWeatherById(id);
    if (weather.isPresent()) {
      return new ResponseEntity<>(weather.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
