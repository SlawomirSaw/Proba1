package repository;

import model.Subject;

import java.io.*;
import java.util.ArrayList;

public class SubjectRepository {

    private String filePath;
    private ArrayList<Subject> subjects;

    public SubjectRepository(String filePath) {
        this.filePath = filePath;
        subjects = read();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Subject> read() {
        ArrayList<Subject> readSubjectsFile = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            readSubjectsFile = (ArrayList<Subject>) in.readObject();
            System.out.println("Oczyt pliku PrzedmiotyNauczania.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return readSubjectsFile;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(subjects);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}