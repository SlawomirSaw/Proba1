package model;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentsOfClass implements Serializable {
    private int idClass;
    private ArrayList<Integer> idStudentsOfClass;

    public StudentsOfClass(int idClass, ArrayList<Integer> idStudentsOfClass) {
        this.idClass = idClass;
        this.idStudentsOfClass = idStudentsOfClass;
    }

    public StudentsOfClass() {
    }

    public int getIdClass() {
        return idClass;
    }

    public ArrayList<Integer> getIdStudentsOfClass() {
        return idStudentsOfClass;
    }
}