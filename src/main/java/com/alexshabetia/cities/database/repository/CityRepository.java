package com.alexshabetia.cities.database.repository;

import com.alexshabetia.cities.database.entity.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

  Optional<CityEntity> findByUuid(UUID uuid);

  Page<CityEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
