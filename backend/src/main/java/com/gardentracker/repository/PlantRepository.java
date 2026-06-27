package com.gardentracker.repository;

import com.gardentracker.model.Plant;
import com.gardentracker.model.PlantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByCategory(PlantCategory category);
}
