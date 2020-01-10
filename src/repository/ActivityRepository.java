package repository;

import model.Activity;

import java.io.*;
import java.util.ArrayList;

public class ActivityRepository {

    private String filePath;
    private ArrayList<Activity> activities;

    public ActivityRepository(String filePath) {
        this.filePath = filePath;
        activities = read();
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public ArrayList<Activity> read() {
        ArrayList<Activity> activityFileRead = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            activityFileRead = (ArrayList<Activity>) in.readObject();
            System.out.println("Oczyt pliku Zajecia.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return activityFileRead;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(activities);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}