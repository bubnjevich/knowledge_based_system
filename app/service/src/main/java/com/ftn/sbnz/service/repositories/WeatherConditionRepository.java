package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long> {
    long count();


}
