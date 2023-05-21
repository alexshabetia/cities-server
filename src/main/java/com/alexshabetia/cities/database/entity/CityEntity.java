package com.alexshabetia.cities.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "cities")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @Setter
  @Column(name = "name")
  private String name;

  @Setter
  @Column(name = "photo_link")
  private String photoLink;
}
