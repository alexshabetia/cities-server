package com.alexshabetia.cities.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class City {

  private UUID cityUuid;

  private String name;

  private String photoLink;
}
