package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Grade implements Serializable {
    private int idStudent;
    private int idActivity;
    private int valueGrade;
    private LocalDateTime dateTimeGrade;

    public Grade(int idStudent, int idActivity, int valueGrade, LocalDateTime dateTimeGrade) {
        this.idStudent = idStudent;
        this.idActivity = idActivity;
        this.valueGrade = valueGrade;
        this.dateTimeGrade = dateTimeGrade;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public int getValueGrade() {
        return valueGrade;
    }

    public LocalDateTime getDateTimeGrade() {
        return dateTimeGrade;
    }

    public Grade(int idStudent) {
        this.idStudent = idStudent;
    }
}