package org.branux.weather.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperDTO {
    private ModelMapper modelMapper;

    public ModelMapperDTO(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public <S,T> T mapModelToDto(S model, Class<T> dto ){
        return modelMapper.map(model, dto);
    }

    public <S,T> List<T> mapModelToDtoList(List<S> modelList, Class<T> dto){
        return modelList.stream()
                .map(element -> modelMapper.map(element, dto))
                .collect(Collectors.toList());
    }
}
