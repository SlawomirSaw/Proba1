package service;

import model.Grade;
import repository.GradeRepository;

import javax.swing.*;
import java.util.ArrayList;

public class GradeService {

    private GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public ArrayList<Grade> getGrades() {
        return gradeRepository.getGrades();
    }

    public void addGrade(Grade grade) {
        gradeRepository.getGrades().add(grade);
        gradeRepository.save();
    }

    public void save() {
        gradeRepository.save();
    }

    public Integer selectGradeFor(String nameSubject) {
        int newGrade = -1;
        JOptionPane inputNewGradeFor = new JOptionPane();
        inputNewGradeFor.setWantsInput(true);
        Integer[] possibleValues = {0, 1, 2, 3, 4, 5, 6};
        Object selectedGrade = JOptionPane.showInputDialog(null,
                "Wybierz ocenę z listy:", nameSubject,
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedGrade != null) {
            newGrade = selectedGrade.hashCode();
        }
        return newGrade;
    }

    public ArrayList<Integer> getGradesForStudentActivity(Integer idStudent, Integer idActivity) {
        ArrayList<Integer> gradesForStudentActivity = new ArrayList<Integer>();
        for (Grade grade : getGrades()) {
            if (grade.getIdStudent() == idStudent && grade.getIdActivity() == idActivity) {
                gradesForStudentActivity.add(grade.getValueGrade());
            }
        }
        return gradesForStudentActivity;
    }

    public Float averageGradeClassFromActivity(Integer idActivity) {
        Float averageGradeClassFromActivity = 0f;
        Float sumGrade = 0f;
        int numberGrade = 0;
        for (Grade o : getGrades()) {
            if (o.getIdActivity() == idActivity) {
                sumGrade = sumGrade + o.getValueGrade();
                numberGrade++;
            }
        }
        if (numberGrade > 0) {
            averageGradeClassFromActivity = sumGrade / numberGrade;
        }
        return averageGradeClassFromActivity;
    }
}