package com.alexshabetia.cities.controller;

import com.alexshabetia.cities.controller.dto.CitiesPageDto;
import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.domain.CitiesPage;
import com.alexshabetia.cities.domain.City;
import com.alexshabetia.cities.mapper.CitiesPageToCitiesPageDtoMapper;
import com.alexshabetia.cities.mapper.CityDtoToCityMapper;
import com.alexshabetia.cities.mapper.CityToCityDtoMapper;
import com.alexshabetia.cities.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final String CITY_NAME = "City Name";

  private static final int SIZE = 10;

  private static final int PAGE = 0;
  @InjectMocks CityController cityController;
  @Mock private City cityA;
  @Mock private CityDto cityDtoA;
  @Mock private Pageable pageable;
  @Mock private CitiesPage citiesPage;
  @Mock private CitiesPageDto citiesPageDto;
  @Mock private CityService cityService;
  @Mock private CityDtoToCityMapper cityDtoToCityMapper;
  @Mock private CityToCityDtoMapper cityToCityDtoMapper;
  @Mock private CitiesPageToCitiesPageDtoMapper citiesPageToCitiesPageDtoMapper;

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {CITY_NAME})
  public void getAllCitiesByOptionalName_returnsCityDtoList(String cityName) {
    when(cityService.getAllCitiesByOptionalName(pageable, cityName)).thenReturn(citiesPage);
    when(citiesPageToCitiesPageDtoMapper.map(citiesPage)).thenReturn(citiesPageDto);

    var response = cityController.getAllCitiesByOptionalName(cityName, pageable);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(citiesPageDto, response.getBody());
  }

  @Test
  public void updateCity_updatesCityAndReturnsCityDto() {
    when(cityDtoToCityMapper.map(cityDtoA)).thenReturn(cityA);
    when(cityToCityDtoMapper.map(cityA)).thenReturn(cityDtoA);
    when(cityService.updateCityByUuid(CITY_UUID, cityA)).thenReturn(cityA);

    var response = cityController.updateCity(CITY_UUID, cityDtoA);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(cityDtoA, response.getBody());
  }
}
