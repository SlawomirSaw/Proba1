package service;

import model.Teacher;
import repository.TeacherRepistory;

import javax.swing.*;
import java.util.ArrayList;

public class TeacherService {

    private TeacherRepistory teacherRepistory;

    public TeacherService(TeacherRepistory teacherRepistory) {
        this.teacherRepistory = teacherRepistory;
    }

    public ArrayList<Teacher> getTeachers() {
        return teacherRepistory.getTeachers();
    }

    public void addTeacher(Teacher teacher) {
        teacherRepistory.getTeachers().add(teacher);
        teacherRepistory.save();
    }

    public int getIdTeacher(String fullName) {
        int idTeacher = 0;
        String nameTeacher = fullName.substring(0, fullName.indexOf(" "));
        String surnameTeacher = fullName.substring(fullName.indexOf(" ") + 1);
        for (Teacher n : teacherRepistory.getTeachers()) {
            if (n.getName().equals(nameTeacher) && n.getSurname().equals(surnameTeacher)) {
                idTeacher = n.getIdTeacher();
            }
        }
        return idTeacher;
    }

    public void inputNewTeacher() {
        Teacher newTeacher = new Teacher();
        String nameTeacher = JOptionPane.showInputDialog("Wprowadź imię nauczyciela");
        if (nameTeacher != null) {
            String surnameTeacher = JOptionPane.showInputDialog("Wprowadź nazwisko nauczyciela");
            if (surnameTeacher != null) {
                newTeacher = new Teacher(teacherRepistory.getTeachers().size() + 1, nameTeacher, surnameTeacher);
            }
        }
        if (newTeacher.getIdTeacher() > 0) {
            addTeacher(newTeacher);
        }
    }
}