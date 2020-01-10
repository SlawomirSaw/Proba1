package repository;

import model.Student;

import java.io.*;
import java.util.ArrayList;

public class StudentRepository {

    private String filePath;
    private ArrayList<Student> students;

    public StudentRepository(String filePath) {
        this.filePath = filePath;
        students = read();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Student> read() {
        ArrayList<Student> readStudentFile = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            readStudentFile = (ArrayList<Student>) in.readObject();
            System.out.println("Oczyt pliku: " + filePath);
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
        }
        return readStudentFile;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(students);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}