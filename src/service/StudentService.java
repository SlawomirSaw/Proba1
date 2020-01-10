package service;

import model.Student;
import repository.StudentRepository;

import java.util.ArrayList;

public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student) {
        studentRepository.getStudents().add(student);
        studentRepository.save();
    }

    public int getStudentSize() {
        return studentRepository.getStudents().size();
    }

    public Student getStudent(int index) {
        return studentRepository.getStudents().get(index);
    }

    public ArrayList<Student> getStudents() {
        return studentRepository.getStudents();
    }

    public ArrayList<Student> alphabetStudentSchoolList() {
        ArrayList<Student> students = studentRepository.getStudents();
        ArrayList<Student> alphabetStudentSchoolList = new ArrayList<Student>(students);
        ArrayList<String> alphabetNamesStudentSchoolList = new ArrayList<String>();
        ArrayList<String> surnameStudentSchoolList = new ArrayList<String>();
        for (Student u : students) {
            surnameStudentSchoolList.add(u.getSurname() + " " + u.getName());
        }
        surnameStudentSchoolList.sort(null);
        for (int i = 0; i < surnameStudentSchoolList.size(); i++) {
            String nameStudent = surnameStudentSchoolList.get(i).substring(surnameStudentSchoolList.get(i).indexOf(" ") + 1);
            String surnameStudent = surnameStudentSchoolList.get(i).substring(0, surnameStudentSchoolList.get(i).indexOf(" "));
            alphabetNamesStudentSchoolList.add(nameStudent + " " + surnameStudent);
        }
        alphabetNamesStudentSchoolList.sort(null);
        for (Student u : students) {
            for (int i = 0; i < alphabetNamesStudentSchoolList.size(); i++) {
                String nameStudent = alphabetNamesStudentSchoolList.get(i).substring(0, alphabetNamesStudentSchoolList.get(i).indexOf(" "));
                String surnameStudent = alphabetNamesStudentSchoolList.get(i).substring(alphabetNamesStudentSchoolList.get(i).indexOf(" ") + 1);
                if (u.getName().equals(nameStudent) && u.getSurname().equals(surnameStudent)) {
                    alphabetStudentSchoolList.set(i, new Student(u.getidStudent(), u.getName(), u.getSurname()));
                }
            }
        }
        return alphabetStudentSchoolList;
    }
}
