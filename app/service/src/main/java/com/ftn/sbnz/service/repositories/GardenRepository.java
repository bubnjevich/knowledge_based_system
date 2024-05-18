package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.Garden;
import com.ftn.sbnz.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> {
    Optional<Garden> findById(Long id);
    long count();
}
