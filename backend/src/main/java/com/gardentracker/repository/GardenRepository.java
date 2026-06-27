package com.gardentracker.repository;

import com.gardentracker.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> {
}
