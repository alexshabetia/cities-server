package com.alexshabetia.cities.controller;

import com.alexshabetia.cities.controller.dto.CitiesPageDto;
import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.database.entity.CityEntity;
import com.alexshabetia.cities.database.repository.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CityControllerIntegrationTest {

  private static final RequestPostProcessor EDITOR_POST_PROCESSOR = httpBasic("admin", "admin");

  private static final UUID CITY_UUID = UUID.randomUUID();

  private static final UUID CITY_UUID_B = UUID.randomUUID();

  private static final String CITY_NAME = "City Name One";

  private static final String PHOTO_LINK = "https://site.com/photo.jpg";

  private static final String CITY_NAME_B = "Updated city Name";

  private static final String PHOTO_LINK_B = "https://other-site.com/other-photo.png";

  private static final String PHOTO_LINK_NOT_VALID = "abc://other-site.com/other-photo.png";

  private static final int SIZE = 10;

  private static final int PAGE = 0;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private CityRepository cityRepository;

  @BeforeEach
  public void clearData() {
    cityRepository.deleteAll();
  }

  @Test
  public void getAllCitiesByOptionalName_noNameParam_returnsAllCities() throws Exception {
    var cityDto = CityDto.builder().uuid(CITY_UUID).name(CITY_NAME).photo(PHOTO_LINK).build();
    var cityDtoB =
        CityDto.builder().uuid(CITY_UUID_B).name(CITY_NAME_B).photo(PHOTO_LINK_B).build();
    var cityEntity =
        CityEntity.builder().uuid(CITY_UUID).name(CITY_NAME).photoLink(PHOTO_LINK).build();
    var cityEntityB =
        CityEntity.builder().uuid(CITY_UUID_B).name(CITY_NAME_B).photoLink(PHOTO_LINK_B).build();
    cityRepository.saveAll(List.of(cityEntity, cityEntityB));

    var result =
        mockMvc
            .perform(get("/cities?page={page}&size={size}", PAGE, SIZE))
            .andExpect(status().isOk())
            .andReturn();

    var actual =
        objectMapper.readValue(result.getResponse().getContentAsString(), CitiesPageDto.class);
    assertEquals(1, actual.getTotalPages());
    assertEquals(2, actual.getTotalElements());
    assertThat(actual.getCities()).hasSameElementsAs(List.of(cityDto, cityDtoB));
  }

  @ParameterizedTest
  @ValueSource(strings = {CITY_NAME, "one", " One"})
  public void getAllCitiesByOptionalName_nameParamPresent_returnsCitiesByName(String nameToSearch)
      throws Exception {
    var cityDto = CityDto.builder().uuid(CITY_UUID).name(CITY_NAME).photo(PHOTO_LINK).build();
    var cityEntity =
        CityEntity.builder().uuid(CITY_UUID).name(CITY_NAME).photoLink(PHOTO_LINK).build();
    var cityEntityB =
        CityEntity.builder().uuid(CITY_UUID_B).name(CITY_NAME_B).photoLink(PHOTO_LINK_B).build();
    cityRepository.saveAll(List.of(cityEntity, cityEntityB));
    var expectedCitiesPageDto =
        CitiesPageDto.builder().cities(List.of(cityDto)).totalElements(1L).totalPages(1).build();
    var expected = objectMapper.writeValueAsString(expectedCitiesPageDto);

    mockMvc
        .perform(get("/cities?page={page}&size={size}&name={name}", PAGE, SIZE, nameToSearch))
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  public void update_noAuth_Unauthorized() throws Exception {
    var cityDto = CityDto.builder().name(CITY_NAME).photo(PHOTO_LINK).build();
    var content = objectMapper.writeValueAsString(cityDto);
    mockMvc
        .perform(
            put("/cities/{uuid}", CITY_UUID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void update_AsEditorNoCityWithUuid_NotFound() throws Exception {
    var cityDto = CityDto.builder().name(CITY_NAME).photo(PHOTO_LINK).build();
    var content = objectMapper.writeValueAsString(cityDto);
    mockMvc
        .perform(
            put("/cities/{uuid}", CITY_UUID)
                .with(EDITOR_POST_PROCESSOR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isNotFound());
  }

  @Test
  public void update_AsEditorAndUpdateRequest_savesUpdatedCityAndReturnsCityDto() throws Exception {
    var cityDto = CityDto.builder().name(CITY_NAME_B).photo(PHOTO_LINK_B).build();
    var cityEntity =
        CityEntity.builder().uuid(CITY_UUID).name(CITY_NAME).photoLink(PHOTO_LINK).build();
    cityRepository.save(cityEntity);
    var expectedCityDto =
        CityDto.builder().uuid(CITY_UUID).name(CITY_NAME_B).photo(PHOTO_LINK_B).build();
    var expectedCityEntity =
        CityEntity.builder().uuid(CITY_UUID).name(CITY_NAME_B).photoLink(PHOTO_LINK_B).build();
    var expected = objectMapper.writeValueAsString(expectedCityDto);
    var content = objectMapper.writeValueAsString(cityDto);

    mockMvc
        .perform(
            put("/cities/{uuid}", CITY_UUID)
                .with(EDITOR_POST_PROCESSOR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
    var saved = cityRepository.findAll(Pageable.unpaged());

    assertEquals(1, saved.getTotalElements());
    assertThat(saved.getContent().get(0))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(expectedCityEntity);
  }

  @Test
  public void update_AsEditorAndInvalidPhotoUrl_badRequest() throws Exception {
    var cityDto = CityDto.builder().name(CITY_NAME_B).photo(PHOTO_LINK_NOT_VALID).build();
    var cityEntity =
        CityEntity.builder().uuid(CITY_UUID).name(CITY_NAME).photoLink(PHOTO_LINK).build();
    cityRepository.save(cityEntity);
    var content = objectMapper.writeValueAsString(cityDto);
    mockMvc
        .perform(
            put("/cities/{uuid}", CITY_UUID)
                .with(EDITOR_POST_PROCESSOR)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isBadRequest());
  }
}
