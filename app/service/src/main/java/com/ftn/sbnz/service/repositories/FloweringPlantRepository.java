package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.FloweringPlant;
import com.ftn.sbnz.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FloweringPlantRepository extends JpaRepository<FloweringPlant, Long> {
    Optional<FloweringPlant> findById(Long id);
    long count();

}
