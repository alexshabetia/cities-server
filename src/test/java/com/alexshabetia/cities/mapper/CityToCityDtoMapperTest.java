package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.domain.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityToCityDtoMapperTest {

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final String CITY_NAME = "City Name";

  private static final String PHOTO_LINK = "https://site.com/photo.jpg";

  @Mock private City city;

  @InjectMocks private CityToCityDtoMapper cityToCityDtoMapper;

  @Test
  public void map() {
    when(city.getCityUuid()).thenReturn(CITY_UUID);
    when(city.getName()).thenReturn(CITY_NAME);
    when(city.getPhotoLink()).thenReturn(PHOTO_LINK);

    var result = cityToCityDtoMapper.map(city);

    assertEquals(CITY_UUID, result.getUuid());
    assertEquals(CITY_NAME, result.getName());
    assertEquals(PHOTO_LINK, result.getPhoto());
  }
}
