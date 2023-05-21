package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.domain.CitiesPage;
import com.alexshabetia.cities.domain.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitiesPageToCitiesPageDtoMapperTest {

  private static final int TOTAL_PAGES = 1;

  private static final long TOTAL_ELEMENTS = 1;

  @Mock private City city;

  @Mock private CityDto cityDto;

  @Mock private CitiesPage citiesPage;

  @Mock private CityToCityDtoMapper cityToCityDtoMapper;

  @InjectMocks private CitiesPageToCitiesPageDtoMapper citiesPageToCitiesPageDtoMapper;

  @Test
  public void map() {
    when(citiesPage.getTotalPages()).thenReturn(TOTAL_PAGES);
    when(citiesPage.getTotalElements()).thenReturn(TOTAL_ELEMENTS);
    when(citiesPage.getCities()).thenReturn(List.of(city));
    when(cityToCityDtoMapper.map(city)).thenReturn(cityDto);

    var result = citiesPageToCitiesPageDtoMapper.map(citiesPage);

    assertEquals(TOTAL_PAGES, result.getTotalPages());
    assertEquals(TOTAL_ELEMENTS, result.getTotalElements());
    assertEquals(List.of(cityDto), result.getCities());
  }
}
