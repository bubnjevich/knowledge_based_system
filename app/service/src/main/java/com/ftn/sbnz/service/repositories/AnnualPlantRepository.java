package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.AnnualPlant;
import com.ftn.sbnz.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnualPlantRepository extends JpaRepository<AnnualPlant, Long> {
    Optional<AnnualPlant> findById(Long id);
    long count();
}
