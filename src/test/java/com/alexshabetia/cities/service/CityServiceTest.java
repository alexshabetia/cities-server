package com.alexshabetia.cities.service;

import com.alexshabetia.cities.database.entity.CityEntity;
import com.alexshabetia.cities.database.repository.CityRepository;
import com.alexshabetia.cities.domain.City;
import com.alexshabetia.cities.exception.NotFoundException;
import com.alexshabetia.cities.mapper.CityEntityToCityMapper;
import com.alexshabetia.cities.service.impl.CityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final String CITY_NAME = "City Name";

  private static final String PHOTO_LINK = "https://site.com/photo.jpg";

  @InjectMocks CityServiceImpl cityService;
  @Mock private Pageable pageable;
  @Mock private CityEntity cityEntityA;
  @Mock private CityEntity cityEntityB;
  @Mock private City cityA;
  @Mock private City cityB;
  @Mock private CityEntityToCityMapper cityEntityToCityMapper;
  @Mock private CityRepository cityRepository;

  @Test
  public void getAllCitiesByOptionalName_nameParameterPresent_returnsCitiesByName() {
    when(cityEntityToCityMapper.map(cityEntityA)).thenReturn(cityA);
    when(cityEntityToCityMapper.map(cityEntityB)).thenReturn(cityB);

    when(cityRepository.findAllByNameContainsIgnoreCase(CITY_NAME, pageable))
        .thenReturn(new PageImpl<>(List.of(cityEntityA, cityEntityB)));

    var result = cityService.getAllCitiesByOptionalName(pageable, CITY_NAME);

    assertEquals(2, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertThat(result.getCities()).hasSameElementsAs(List.of(cityA, cityB));
  }

  @Test
  public void getAllCitiesByOptionalName_nameParameterNull_returnsAllCities() {
    when(cityEntityToCityMapper.map(cityEntityA)).thenReturn(cityA);
    when(cityEntityToCityMapper.map(cityEntityB)).thenReturn(cityB);

    when(cityRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(List.of(cityEntityA, cityEntityB)));

    var result = cityService.getAllCitiesByOptionalName(pageable, null);

    assertEquals(2, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertThat(result.getCities()).hasSameElementsAs(List.of(cityA, cityB));
  }

  @Test
  public void updateCityByUuid_savesCityEntity() {
    when(cityEntityToCityMapper.map(cityEntityA)).thenReturn(cityA);
    when(cityRepository.save(cityEntityA)).thenReturn(cityEntityA);
    when(cityRepository.findByUuid(CITY_UUID)).thenReturn(Optional.of(cityEntityA));
    when(cityA.getName()).thenReturn(CITY_NAME);
    when(cityA.getPhotoLink()).thenReturn(PHOTO_LINK);

    var result = cityService.updateCityByUuid(CITY_UUID, cityA);

    assertEquals(cityA, result);
    verify(cityRepository, times(1)).save(cityEntityA);
  }

  @Test
  public void updateCityByUuid_cityNotFoundByUuid_throwsCityNotFoundException() {
    when(cityRepository.findByUuid(CITY_UUID)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> cityService.updateCityByUuid(CITY_UUID, cityA));
  }
}
