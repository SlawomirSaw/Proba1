package service;

import model.StudentsOfClass;
import repository.StudentsOfClassRepository;

import java.util.ArrayList;

public class StudentsOfClassService {

    private StudentsOfClassRepository studentsOfClassRepository;

    public StudentsOfClassService(StudentsOfClassRepository studentsOfClassRepository) {
        this.studentsOfClassRepository = studentsOfClassRepository;
    }

    public ArrayList<StudentsOfClass> getStudentsOfClasses() {
        return studentsOfClassRepository.getStudentsOfClasses();
    }

    public void save() {
        studentsOfClassRepository.save();
    }
}