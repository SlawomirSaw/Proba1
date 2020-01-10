package model;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int idSchedule;
    private int idActivity;
    private String weekDay;
    private int hour;

    public Schedule(int idSchedule, int idActivity, String weekDay, int hour) {
        this.idSchedule = idSchedule;
        this.idActivity = idActivity;
        this.weekDay = weekDay;
        this.hour = hour;
    }

    public Schedule(){
    }

    public int getIdSchedule() {
        return idSchedule;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public int getHour() {
        return hour;
    }
}