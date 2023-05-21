package com.alexshabetia.cities.service;

import com.alexshabetia.cities.domain.CitiesPage;
import com.alexshabetia.cities.domain.City;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CityService {

  CitiesPage getAllCitiesByOptionalName(Pageable pageable, String name);

  City updateCityByUuid(UUID cityUuid, City city);
}
