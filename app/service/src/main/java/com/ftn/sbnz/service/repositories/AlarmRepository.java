package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
