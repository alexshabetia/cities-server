package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.domain.City;
import org.springframework.stereotype.Component;

@Component
public class CityDtoToCityMapper {

  public City map(CityDto cityDto) {
    return City.builder()
        .cityUuid(cityDto.getUuid())
        .name(cityDto.getName())
        .photoLink(cityDto.getPhoto())
        .build();
  }
}
