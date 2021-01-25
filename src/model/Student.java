package model;

import java.io.Serializable;

public class Student extends Person implements Serializable {
    int idStudent;

    public Student(int idStudent, String imie, String nazwisko) {
        super(imie, nazwisko);
        this.idStudent = idStudent;
    }

    public int getidStudent() {
        return idStudent;
    }

    public Student() {
    }
}