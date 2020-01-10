package repository;

import model.StudentsOfClass;

import java.io.*;
import java.util.ArrayList;

public class StudentsOfClassRepository {

    private String filePath;
    private ArrayList<StudentsOfClass> studentsOfClasses;

    public StudentsOfClassRepository(String filePath) {
        this.filePath = filePath;
        studentsOfClasses = read();
    }

    public ArrayList<StudentsOfClass> getStudentsOfClasses() {
        return studentsOfClasses;
    }

    public ArrayList<StudentsOfClass> read() {
        ArrayList<StudentsOfClass> readStudentsOfClasses = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            readStudentsOfClasses = (ArrayList<StudentsOfClass>) in.readObject();
            System.out.println("Oczyt pliku UczniowieSzkolyWgKlas.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return readStudentsOfClasses;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(studentsOfClasses);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}