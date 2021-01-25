package model;

import java.io.Serializable;

public class Subject implements Serializable {
    private int idSubject;
    private String nameSubject;

    public Subject(int idSubject, String nameSubject) {
        this.idSubject = idSubject;
        this.nameSubject = nameSubject;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }
}