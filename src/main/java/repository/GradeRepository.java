package repository;

import model.Grade;

import java.io.*;
import java.util.ArrayList;

public class GradeRepository {

    private String filePath;
    private ArrayList<Grade> grades;

    public GradeRepository(String filePath) {
        this.filePath = filePath;
        grades = read();
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public ArrayList<Grade> read() {
        ArrayList<Grade> gradeFileRead = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            gradeFileRead = (ArrayList<Grade>) in.readObject();
            System.out.println("Oczyt pliku OcenyUczniow.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println(ioe + "Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return gradeFileRead;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(grades);
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe + "Error!");
        }
    }
}