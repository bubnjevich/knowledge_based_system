package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.SoilMoisture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilMoistureRepository extends JpaRepository<SoilMoisture, Long> {

    @Query("SELECT s FROM SoilMoisture s ORDER BY s.measurementDate ASC")
    List<SoilMoisture> findAllOrderByMeasurementDateAsc();
    List<SoilMoisture> findAllByOrderByMeasurementDateAsc();
}
