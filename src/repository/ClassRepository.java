package repository;

import model.Class;

import java.io.*;
import java.util.ArrayList;

import static configuration.SchoolConfiguration.NAZWY_KLAS;

public class ClassRepository {

    private String filePath;
    private ArrayList<Class> classes = new ArrayList<>();

    public ClassRepository (String filePath) {
        this.filePath = filePath;
        for (int i = 1; i <= NAZWY_KLAS.length; i++) {
            classes.add(new Class(i, NAZWY_KLAS[i-1]));
        }
        //classes = read();
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public ArrayList<Class> read() {
        ArrayList<Class> readClassFile = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            readClassFile = (ArrayList<Class>) in.readObject();
            System.out.println("Oczyt pliku Klasy.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return readClassFile;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(classes);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}
