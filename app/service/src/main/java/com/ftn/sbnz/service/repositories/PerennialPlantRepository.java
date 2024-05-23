package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.PerennialPlant;
import com.ftn.sbnz.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerennialPlantRepository extends JpaRepository<PerennialPlant, Long> {
    Optional<PerennialPlant> findById(Long id);
    long count();
}
