package com.alexshabetia.cities.service.impl;

import com.alexshabetia.cities.database.entity.CityEntity;
import com.alexshabetia.cities.database.repository.CityRepository;
import com.alexshabetia.cities.domain.CitiesPage;
import com.alexshabetia.cities.domain.City;
import com.alexshabetia.cities.exception.NotFoundException;
import com.alexshabetia.cities.mapper.CityEntityToCityMapper;
import com.alexshabetia.cities.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

  private final CityRepository cityRepository;

  private final CityEntityToCityMapper cityEntityToCityMapper;

  @Override
  public CitiesPage getAllCitiesByOptionalName(Pageable pageable, String cityName) {
    Page<CityEntity> cityEntityPage;
    if (null != cityName) {
      cityEntityPage = cityRepository.findAllByNameContainsIgnoreCase(cityName, pageable);
    } else {
      cityEntityPage = cityRepository.findAll(pageable);
    }
    return CitiesPage.builder()
        .totalPages(cityEntityPage.getTotalPages())
        .totalElements(cityEntityPage.getTotalElements())
        .cities(cityEntityPage.getContent().stream().map(cityEntityToCityMapper::map).toList())
        .build();
  }

  @Override
  public City updateCityByUuid(UUID uuid, City city) {
    var cityEntity = cityRepository.findByUuid(uuid).orElseThrow(NotFoundException::new);
    cityEntity.setName(city.getName());
    cityEntity.setPhotoLink(city.getPhotoLink());
    return cityEntityToCityMapper.map(cityRepository.save(cityEntity));
  }
}
