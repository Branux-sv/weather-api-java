package org.branux.weather.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WeatherDtoReq {
    private Integer id;
    private Date date;

    private Float lat;
    private Float lon;
    private String city;
    private String state;
    private List<Double> temperatures;
}
