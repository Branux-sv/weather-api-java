package org.branux.weather.service;

import org.branux.weather.dto.WeatherDto;
import org.branux.weather.dto.WeatherDtoReq;
import org.branux.weather.model.Weather;
import org.branux.weather.repository.WeatherRepository;
import org.branux.weather.util.DateConverter;
import org.branux.weather.util.ModelMapperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class WeatherService {
    private WeatherRepository weatherRepository;
    private ModelMapperDTO modelMapperDTO;
    private DateConverter dateConverter;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        this.modelMapperDTO = new ModelMapperDTO();
    }

    public WeatherDto createWeather(WeatherDtoReq weatherDtoReq) {
        Weather weather = modelMapperDTO.mapModelToDto(weatherDtoReq, Weather.class);
        dateConverter = new DateConverter();
        try {
            weather.setDate(dateConverter.getFixedDate(weatherDtoReq.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Weather newWeather = weatherRepository.save(weather);
        WeatherDto weatherDto = modelMapperDTO.mapModelToDto(newWeather, WeatherDto.class);
        weatherDto.setDate(weatherDtoReq.getDate());
        return weatherDto;
    }

    public Optional<WeatherDto> getWeatherById(Integer id){
        Optional<Weather> weather = weatherRepository.findById(id);
        Optional<WeatherDto> weatherDto = Optional.empty();

        if (weather.isPresent()) {
            weatherDto = Optional.of(modelMapperDTO.mapModelToDto(weather.get(), WeatherDto.class));
        }
        return weatherDto;
    }

    public List<WeatherDto> getWeathers(String dateStr, String city, String sort){
        List<WeatherDto> weatherDtoList = new ArrayList<>();
        List<Weather> weatherList;
        Date dateFilter = null;
        List<String> citiesList = null;
        Sort sortByExpression = Sort.by(Sort.Direction.ASC, "id");

        //Clean filter values
        if (dateStr != null && !"all".equalsIgnoreCase(dateStr)){
            dateConverter = new DateConverter();
            try {
                dateFilter = dateConverter.getDateFromString(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (city != null && !"all".equalsIgnoreCase(city)){
            String[] cities = city.toUpperCase().split(",");
            citiesList = Arrays.asList(cities);
        }

        //sort value
        if (sort != null && !"all".equalsIgnoreCase(sort)){
            if ("date".equalsIgnoreCase(sort)){
                sortByExpression = Sort.by(Sort.Direction.ASC, "date")
                        .and(Sort.by(Sort.Direction.ASC, "id"));
            }

            if ("-date".equalsIgnoreCase(sort)){
                sortByExpression = Sort.by(Sort.Direction.DESC, "date")
                        .and(Sort.by(Sort.Direction.ASC, "id"));
            }
        }

        weatherList = weatherRepository.findAllByCitiesOrDate(citiesList,dateFilter,sortByExpression);

        if (!weatherList.isEmpty()){
            weatherDtoList = modelMapperDTO.mapModelToDtoList(weatherList, WeatherDto.class);
        }
        return weatherDtoList;
    }

}
