package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.domain.City;
import org.springframework.stereotype.Component;

@Component
public class CityToCityDtoMapper {

  public CityDto map(City city) {
    return CityDto.builder()
        .uuid(city.getCityUuid())
        .name(city.getName())
        .photo(city.getPhotoLink())
        .build();
  }
}
