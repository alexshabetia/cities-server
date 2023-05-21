package com.alexshabetia.cities.mapper;

import com.alexshabetia.cities.database.entity.CityEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityEntityToCityMapperTest {

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final String CITY_NAME = "City Name";

  private static final String PHOTO_LINK = "https://site.com/photo.jpg";

  @Mock private CityEntity cityEntity;

  @InjectMocks private CityEntityToCityMapper cityEntityToCityMapper;

  @Test
  public void map() {
    when(cityEntity.getUuid()).thenReturn(CITY_UUID);
    when(cityEntity.getName()).thenReturn(CITY_NAME);
    when(cityEntity.getPhotoLink()).thenReturn(PHOTO_LINK);

    var result = cityEntityToCityMapper.map(cityEntity);

    assertEquals(CITY_UUID, result.getCityUuid());
    assertEquals(CITY_NAME, result.getName());
    assertEquals(PHOTO_LINK, result.getPhotoLink());
  }
}
