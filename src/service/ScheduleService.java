package service;

import model.Schedule;
import repository.ScheduleRepository;

import java.util.ArrayList;

public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ArrayList<Schedule> getSchedules() {
        return scheduleRepository.getSchedules();
    }

    public void save() {
        scheduleRepository.save();
    }
}