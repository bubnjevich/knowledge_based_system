package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.Plant;
import com.ftn.sbnz.model.Soil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoilRepository extends JpaRepository<Soil, Long> {
    Optional<Soil> findById(Long id);
    long count();
}
