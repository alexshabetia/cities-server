package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.database.entity.CityEntity;
import com.alexshabetia.cities.domain.City;
import org.springframework.stereotype.Component;

@Component
public class CityEntityToCityMapper {

  public City map(CityEntity cityEntity) {
    return City.builder()
        .cityUuid(cityEntity.getUuid())
        .name(cityEntity.getName())
        .photoLink(cityEntity.getPhotoLink())
        .build();
  }
}
