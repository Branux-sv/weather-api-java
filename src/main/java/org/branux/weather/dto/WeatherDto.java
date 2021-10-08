package org.branux.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WeatherDto {
    private Integer id;
    private Date date;

    private Float lat;
    //@JsonProperty("longitude")
    private Float lon;
    private String city;
    private String state;
    private List<Double> temperatures;
    @JsonIgnore
    private String internal;
}
