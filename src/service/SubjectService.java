package service;

import model.Subject;
import repository.SubjectRepository;

import javax.swing.*;
import java.util.ArrayList;

import static configuration.SchoolConfiguration.NAZWY_PRZEDMIOTOW_USTAWA;

public class SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public ArrayList<Subject> getSubjects() {
        return subjectRepository.getSubjects();
    }

    public void addSubject(Subject subject) {
        subjectRepository.getSubjects().add(subject);
        subjectRepository.save();
    }

    public void save() {
        subjectRepository.save();
    }

    public void selectSubject() {
        Subject newSubject = new Subject(0, "");
        ArrayList<String> namesAvailableSubjects = new ArrayList<String>();
        ArrayList<String> namesAvailableSubjectsToSelect = new ArrayList<String>();
        for (Subject pn : subjectRepository.getSubjects()) {
            namesAvailableSubjects.add(pn.getNameSubject());
        }
        for (String NP : NAZWY_PRZEDMIOTOW_USTAWA) {
            if (!namesAvailableSubjects.contains(NP)) {
                namesAvailableSubjectsToSelect.add(NP);
            }
        }
        namesAvailableSubjectsToSelect.sort(null);
        if (namesAvailableSubjectsToSelect.size() > 0) {
            JOptionPane selectNewSubject = new JOptionPane();
            selectNewSubject.setWantsInput(true);
            String[] possibleValues = new String[namesAvailableSubjectsToSelect.size()];
            for (int i = 0; i < namesAvailableSubjectsToSelect.size(); i++) {
                possibleValues[i] = namesAvailableSubjectsToSelect.get(i);
            }
            Object selectedSubject = JOptionPane.showInputDialog(null,
                    "Wybierz dostępny wolny przedmiot nauczania ", "Lista przedmiotów nauczania",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, null);
            if (selectedSubject != null) {
                newSubject = new Subject(subjectRepository.getSubjects().size() + 1, selectedSubject.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Brak przedmiotów do wyboru - wszystkie juz są wybrane", "Uwaga!", JOptionPane.ERROR_MESSAGE);
        }
        if (newSubject.getIdSubject() > 0) {
            addSubject(newSubject);
        }
    }

    public int getIdSubject(String subjectName) {
        int idSubject = 0;
        for (Subject d : subjectRepository.getSubjects()) {
            if (subjectName.equals(d.getNameSubject())) {
                idSubject = d.getIdSubject();
            }
        }
        return idSubject;
    }
}