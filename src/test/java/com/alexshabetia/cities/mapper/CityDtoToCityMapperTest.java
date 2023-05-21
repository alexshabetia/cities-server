package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.controller.dto.CityDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityDtoToCityMapperTest {

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final String CITY_NAME = "City Name";

  private static final String PHOTO_LINK = "https://site.com/photo.jpg";

  @Mock private CityDto cityDto;

  @InjectMocks private CityDtoToCityMapper cityDtoToCityMapper;

  @Test
  public void map() {
    when(cityDto.getUuid()).thenReturn(CITY_UUID);
    when(cityDto.getName()).thenReturn(CITY_NAME);
    when(cityDto.getPhoto()).thenReturn(PHOTO_LINK);

    var result = cityDtoToCityMapper.map(cityDto);

    assertEquals(CITY_UUID, result.getCityUuid());
    assertEquals(CITY_NAME, result.getName());
    assertEquals(PHOTO_LINK, result.getPhotoLink());
  }
}
