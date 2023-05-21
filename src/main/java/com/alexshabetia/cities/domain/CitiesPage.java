package com.alexshabetia.cities.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CitiesPage {

  private List<City> cities;

  private int totalPages;

  private long totalElements;
}
