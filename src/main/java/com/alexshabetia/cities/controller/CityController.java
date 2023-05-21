package com.alexshabetia.cities.controller;

import com.alexshabetia.cities.controller.dto.CitiesPageDto;
import com.alexshabetia.cities.controller.dto.CityDto;
import com.alexshabetia.cities.mapper.CitiesPageToCitiesPageDtoMapper;
import com.alexshabetia.cities.mapper.CityDtoToCityMapper;
import com.alexshabetia.cities.mapper.CityToCityDtoMapper;
import com.alexshabetia.cities.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CityController implements CityApi {

  private final CityService cityService;

  private final CityDtoToCityMapper cityDtoToCityMapper;

  private final CityToCityDtoMapper cityToCityDtoMapper;

  private final CitiesPageToCitiesPageDtoMapper citiesPageToPageDtoMapper;

  @GetMapping("/cities")
  public ResponseEntity<CitiesPageDto> getAllCitiesByOptionalName(
      @RequestParam(required = false) String name, Pageable pageable) {
    return ResponseEntity.ok(
        citiesPageToPageDtoMapper.map(cityService.getAllCitiesByOptionalName(pageable, name)));
  }

  @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
  @PutMapping("/cities/{uuid}")
  public ResponseEntity<CityDto> updateCity(@PathVariable UUID uuid, @RequestBody CityDto cityDto) {
    return ResponseEntity.ok(
        cityToCityDtoMapper.map(
            cityService.updateCityByUuid(uuid, cityDtoToCityMapper.map(cityDto))));
  }
}
