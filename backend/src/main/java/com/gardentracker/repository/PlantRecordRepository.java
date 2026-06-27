package com.gardentracker.repository;

import com.gardentracker.model.PlantRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlantRecordRepository extends JpaRepository<PlantRecord, Long> {
    List<PlantRecord> findByGardenId(Long gardenId);
}
