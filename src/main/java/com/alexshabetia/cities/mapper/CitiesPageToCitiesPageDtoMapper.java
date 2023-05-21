package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.controller.dto.CitiesPageDto;
import com.alexshabetia.cities.domain.CitiesPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CitiesPageToCitiesPageDtoMapper {

  private final CityToCityDtoMapper cityToCityDtoMapper;

  public CitiesPageDto map(CitiesPage citiesPage) {
    return CitiesPageDto.builder()
        .totalElements(citiesPage.getTotalElements())
        .totalPages(citiesPage.getTotalPages())
        .cities(
            citiesPage.getCities().stream()
                .map(cityToCityDtoMapper::map)
                .collect(Collectors.toList()))
        .build();
  }
}
