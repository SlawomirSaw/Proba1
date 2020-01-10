package repository;

import model.Schedule;

import java.io.*;
import java.util.ArrayList;

public class ScheduleRepository {

    private String filePath;
    private ArrayList<Schedule> schedules;

    public ScheduleRepository(String filePath) {
        this.filePath = filePath;
        schedules = read();
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public ArrayList<Schedule> read() {
        ArrayList<Schedule> scheduleFileRead = new ArrayList<Schedule>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            scheduleFileRead = (ArrayList<Schedule>) in.readObject();
            System.out.println("Oczyt pliku PlanyZajec.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            //System.exit(1);
        }
        return scheduleFileRead;
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(schedules);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }
}