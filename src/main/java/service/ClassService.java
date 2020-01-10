package service;

import model.Class;
import repository.ClassRepository;

import javax.swing.*;
import java.util.ArrayList;

public class ClassService {

    private ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void addClass(Class clas) {
        classRepository.getClasses().add(clas);
        classRepository.save();
    }

    public ArrayList<Class> getClasses() {
        return classRepository.getClasses();
    }

    public int getIdClass(String nameClass) {
        int idClass = 0;
        for (Class k : classRepository.getClasses()) {
            if (nameClass.equals(k.getNameClass())) {
                idClass = k.getIdClass();
            }
        }
        return idClass;
    }

    public String selectClassForActivityOfTeacherId(String imieINazwisko) {
        String newClassForActivity = "";
        JOptionPane inputnowaKlasaNowegoZajecia = new JOptionPane();
        inputnowaKlasaNowegoZajecia.setWantsInput(true);
        String[] possibleValues = new String[classRepository.getClasses().size()];
        for (int i = 0; i < classRepository.getClasses().size(); i++) {
            possibleValues[i] = classRepository.getClasses().get(i).getNameClass();
        }
        Object selectedClass = JOptionPane.showInputDialog(null,
                "Wybierz klasę dla nauczyciela " + imieINazwisko, "Lista klas szkoły",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedClass != null) {
            newClassForActivity = selectedClass.toString();
        }
        return newClassForActivity;
    }
}