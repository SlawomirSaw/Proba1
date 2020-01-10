package model;

import java.io.Serializable;

public class Class implements Serializable {
    private int idClass;
    private String nameClass;

    public Class(int idClass, String nameClass) {
        this.idClass = idClass;
        this.nameClass = nameClass;
    }

    public Class() {
    }

    public int getIdClass() {
        return idClass;
    }

    public String getNameClass() {
        return nameClass;
    }
}