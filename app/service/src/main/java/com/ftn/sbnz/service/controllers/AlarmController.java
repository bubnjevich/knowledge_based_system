package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.Alarm;
import com.ftn.sbnz.service.services.implementation.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping
    public ResponseEntity<Alarm> createAlarm(@RequestBody Alarm alarm) {
        Alarm savedAlarm = alarmService.saveAlarm(alarm);
        return ResponseEntity.ok(savedAlarm);
    }

    @GetMapping
    public ResponseEntity<List<Alarm>> getAllAlarms() {
        List<Alarm> alarms = alarmService.getAllAlarms();
        return ResponseEntity.ok(alarms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alarm> getAlarmById(@PathVariable Long id) {
        Alarm alarm = alarmService.getAlarmById(id);
        if (alarm != null) {
            return ResponseEntity.ok(alarm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long id) {
        alarmService.deleteAlarm(id);
        return ResponseEntity.noContent().build();
    }
}
