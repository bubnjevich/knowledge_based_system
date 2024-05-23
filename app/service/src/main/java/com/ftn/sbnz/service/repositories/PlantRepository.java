package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;


@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Optional<Plant> findById(Long id);
    long count();

}
