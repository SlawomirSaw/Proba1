package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Activity implements Serializable {
    private int idActivity;
    private int idSubject;
    private int idTeacher;
    private int idClass;
    private LocalDateTime dateHourBeginning;

    public Activity(int idActivity, int idSubject, int idTeacher, int idClass, LocalDateTime dateHourBeginning) {
        this.idActivity = idActivity;
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
        this.idClass = idClass;
        this.dateHourBeginning = dateHourBeginning;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public int getIdClass() {
        return idClass;
    }

    public LocalDateTime getDateHourBeginning() {
        return dateHourBeginning;
    }
}