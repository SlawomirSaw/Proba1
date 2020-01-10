package model;

import java.io.Serializable;

public class Teacher extends Person implements Serializable {
    private int idTeacher;

    public Teacher(int idTeacher, String name, String surname) {
        super(name, surname);
        this.idTeacher = idTeacher;
    }

    public Teacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public Teacher() {
    }
}