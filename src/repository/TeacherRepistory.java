package repository;

import model.Teacher;

import java.io.*;
import java.util.ArrayList;

public class TeacherRepistory {

    private String filePath;
    private ArrayList<Teacher> teachers;

    public TeacherRepistory(String filePath) {
        this.filePath = filePath;
        teachers = read();
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Teacher> read() {
        ArrayList<Teacher> readTeacherFile = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            readTeacherFile = (ArrayList<Teacher>) in.readObject();
            System.out.println("Oczyt pliku Nauczyciele.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error read! " + filePath);
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return readTeacherFile;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(teachers);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error! save " + filePath);
        }
    }
}