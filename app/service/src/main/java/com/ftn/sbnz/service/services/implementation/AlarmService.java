package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.Alarm;
import com.ftn.sbnz.service.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public Alarm saveAlarm(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    public Alarm getAlarmById(Long id) {
        return alarmRepository.findById(id).orElse(null);
    }

    public void deleteAlarm(Long id) {
        alarmRepository.deleteById(id);
    }
}
