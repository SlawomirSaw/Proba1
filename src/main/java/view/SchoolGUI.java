package view;

import model.Class;
import model.*;
import service.*;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static configuration.SchoolConfiguration.*;

public class SchoolGUI extends JFrame implements ActionListener, Serializable {

    private ArrayList<Schedule> schedulesPrevious = new ArrayList<>();
    private Boolean editSchedule = false;
    private JScrollPane scrolPanelMain;
    private JPanel panelMain;
    private JPanel panelClasses = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel panelSubjects = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel panelTeachers = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel panelStudents = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JFrame mainFrame;
    private JPanel paneSub = new JPanel();
    private JFrame frameSub = new JFrame();
    private JMenuBar menuBar;
    private Box box2 = new Box(BoxLayout.X_AXIS);
    private Box box3 = new Box(BoxLayout.Y_AXIS);
    private ArrayList<Class> classes = new ArrayList<>();
    private Integer[][][] tableIdActivitiesOfSchedulePrevious = new Integer[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
    private static int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static int selectedColumn = -1;
    private static int selectedRow = -1;
    private static int initialSelectedRow = -1;

    private ArrayList<Student> alphabetStudentSchoolList = new ArrayList<>();
    private ArrayList<StudentsOfClass> studentsOfClasses = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();
    public ArrayList<Grade> grades = new ArrayList<>();
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public StudentService studentService;
    public ClassService classService;
    public StudentsOfClassService studentsOfClassService;
    public TeacherService teacherService;
    public ActivityService activityService;
    public SubjectService subjectService;
    public GradeService gradeService;
    public ScheduleService scheduleService;

    public void mainFrame(StudentService studentService, ClassService classService, StudentsOfClassService studentsOfClassService, TeacherService teacherService, ActivityService activityService, SubjectService subjectService, GradeService gradeService, ScheduleService scheduleService) {
        this.studentService = studentService;
        this.classService = classService;
        this.studentsOfClassService = studentsOfClassService;
        this.teacherService = teacherService;
        this.activityService = activityService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
        this.scheduleService = scheduleService;
        alphabetStudentSchoolList = studentService.alphabetStudentSchoolList();
        classes = classService.getClasses();
        studentsOfClasses = studentsOfClassService.getStudentsOfClasses();
        if (studentsOfClasses.size() < 1) {
            for (int i = 0; i < classes.size(); i++) {
                studentsOfClasses.add(new StudentsOfClass(i + 1, new ArrayList<Integer>()));
            }
            studentsOfClassService.save();
        }
        teachers = teacherService.getTeachers();
        activities = activityService.getActivities();
        subjects = subjectService.getSubjects();
        grades = gradeService.getGrades();
        schedules = scheduleService.getSchedules();
        int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
        int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
        mainFrame = new JFrame(NAZWA_SZKOLY);
        setDefaultLookAndFeelDecorated(true);
        mainFrame.setBounds(0, 0, widthScreen, heightScreen);
        panelMain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMain.setAlignmentY(JPanel.TOP_ALIGNMENT);
        scrolPanelMain = new JScrollPane();
        scrolPanelMain.setViewportView(panelMain);
        panelMain.setBackground(Color.BLUE);
        mainFrame.setJMenuBar(createMainMenu());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.revalidate();
    }

    private ArrayList<String> namesSubjectsForClass(String nameClass) {
        ArrayList<String> namesSubjectsForClass = new ArrayList<String>();
        for (int l = 0; l < activities.size(); l++) {
            if (classService.getIdClass(nameClass) == activities.get(l).getIdClass()) {
                namesSubjectsForClass.add(subjects.get(activities.get(l).getIdSubject() - 1).getNameSubject());
            }
        }
        return namesSubjectsForClass;
    }

    private ArrayList<Activity> activitiesForClass(String nameClass) {
        ArrayList<Activity> activitiesForClass = new ArrayList<Activity>();
        for (Activity activity : activities) {
            if (classService.getIdClass(nameClass) == activity.getIdClass()) {
                activitiesForClass.add(activity);
            }
        }
        return activitiesForClass;
    }

    private ArrayList<Activity> alphabeticActivitiesForStudent(Integer idStudent) {
        ArrayList<Activity> alphabeticActivitiesForStudent = new ArrayList<Activity>();
        ArrayList<String> alphabeticNamesSubjectsForStudent = namesSubjectsForClass(nameClassOfStudentId(idStudent));
        alphabeticNamesSubjectsForStudent.sort(null);
        for (int i = 0; i < alphabeticNamesSubjectsForStudent.size(); i++) {
            for (Activity z : activitiesForClass(nameClassOfStudentId(idStudent))) {
                if ((subjectService.getIdSubject(alphabeticNamesSubjectsForStudent.get(i)) == z.getIdSubject()) && !alphabeticActivitiesForStudent.contains(z)) {
                    alphabeticActivitiesForStudent.add(z);
                }
            }
        }
        return alphabeticActivitiesForStudent;
    }

    private ArrayList<Activity> activitiesForSubject(String nameSubject) {
        ArrayList<Activity> activitiesForSubject = new ArrayList<Activity>();
        for (Activity z : activities) {
            if (subjectService.getIdSubject(nameSubject) == z.getIdSubject()) {
                activitiesForSubject.add(z);
            }
        }
        return activitiesForSubject;
    }

    private ArrayList<String> namesSubjectsForTeacher(int idTeacher) {
        ArrayList<String> namesSubjectsForTeacher = new ArrayList<String>();
        for (Activity z : activities) {
            if (z.getIdTeacher() == idTeacher) {
                if (!namesSubjectsForTeacher.contains(subjects.get(z.getIdSubject() - 1).getNameSubject())) {
                    namesSubjectsForTeacher.add(subjects.get(z.getIdSubject() - 1).getNameSubject());
                }
            }
        }
        namesSubjectsForTeacher.sort(null);
        return namesSubjectsForTeacher;
    }

    private ArrayList<Teacher> teachersForSubject(int idSubject) {
        ArrayList<Teacher> teachersForSubject = new ArrayList<Teacher>();
        ArrayList<Integer> idTeachersForSubject = new ArrayList<Integer>();
        for (Activity z : activities) {
            if (z.getIdSubject() == idSubject) {
                if (!idTeachersForSubject.contains(z.getIdTeacher())) {
                    idTeachersForSubject.add(z.getIdTeacher());
                    teachersForSubject.add(new Teacher(z.getIdTeacher(), teachers.get(z.getIdTeacher() - 1).getName(), teachers.get(z.getIdTeacher() - 1).getSurname()));
                }
            }
        }
        return teachersForSubject;
    }

    private ArrayList<String> namesClassForSubjectTeacher(int idSubject, int idTeacher) {
        ArrayList<String> namesClassForIdSubjectIdTeacher = new ArrayList<String>();
        for (Activity z : activities) {
            if (z.getIdSubject() == idSubject && z.getIdTeacher() == idTeacher) {
                if (!namesClassForIdSubjectIdTeacher.contains(classes.get(z.getIdClass() - 1).getNameClass())) {
                    namesClassForIdSubjectIdTeacher.add(classes.get(z.getIdClass() - 1).getNameClass());
                }
            }
        }
        return namesClassForIdSubjectIdTeacher;
    }

    private ArrayList<Activity> activitiesForTeacher(String nameSurname) {
        ArrayList<Activity> activitiesForTeacher = new ArrayList<Activity>();
        for (int l = 0; l < this.activities.size(); l++) {
            if (this.activities.get(l).getIdTeacher() == teacherService.getIdTeacher(nameSurname)) {
                activitiesForTeacher.add(this.activities.get(l));
            }
        }
        return activitiesForTeacher;
    }

    private String nameActivityForClassDayHour(String nameClass, String dayOfWeek, int hourOfDay) {
        String nameActivityForClassDayHour = "";
        for (Schedule pz : schedules) {
            if (pz.getIdSchedule() > 0) {
                if (activities.get(pz.getIdActivity() - 1).getIdClass() == classService.getIdClass(nameClass) && pz.getWeekDay().equals(dayOfWeek) && pz.getHour() == hourOfDay) {
                    for (Subject p : subjects) {
                        if (activities.get(pz.getIdActivity() - 1).getIdSubject() == p.getIdSubject()) {
                            nameActivityForClassDayHour = p.getNameSubject() + " " + teachers.get(activities.get(pz.getIdActivity() - 1).getIdTeacher() - 1).getName() + " " + teachers.get(activities.get(pz.getIdActivity() - 1).getIdTeacher() - 1).getSurname();
                        }
                    }
                }
            }
        }
        return nameActivityForClassDayHour;
    }

    private Integer idActivityForClassDayHour(String nameClass, String dayOfWeek, int hourOfDay) {
        int idActivityForClassDayHour = -1;
        for (Schedule pz : schedules) {
            if (pz.getIdSchedule() > 0) {
                if (activities.get(pz.getIdActivity() - 1).getIdClass() == classService.getIdClass(nameClass) && pz.getWeekDay().equals(dayOfWeek) && pz.getHour() == hourOfDay) {
                    idActivityForClassDayHour = pz.getIdActivity();
                }
            }
        }
        return idActivityForClassDayHour;
    }

    private ArrayList<Activity> availableActivitiesForClassDayHour(String nameClass, String dayOfWeek, int hourOfDay) {
        Boolean isAvailableDate = true;
        ArrayList<Activity> availableActivitiesForClassDayHour = new ArrayList<Activity>();
        for (Activity activityKlasy : activitiesForClass(nameClass)) {
            if (schedules.size() > 0) {
                for (Schedule pz : schedules) {
                    if (pz.getIdActivity() > 0) {
                        for (Activity z : activities) {
                            if (pz.getIdActivity() == z.getIdActivity()) {
                                if (activityKlasy.getIdTeacher() == z.getIdTeacher() && pz.getWeekDay().equals(dayOfWeek) && pz.getHour() == hourOfDay) {
                                    isAvailableDate = false;
                                }
                            }
                        }
                    }
                }
            }
            if (isAvailableDate) {
                if (!availableActivitiesForClassDayHour.contains(activityKlasy)) {
                    availableActivitiesForClassDayHour.add(activityKlasy);
                }
            }
            isAvailableDate = true;
        }
        return availableActivitiesForClassDayHour;
    }

    private Float averageGradeForClass(String nameClass) {
        Float averageGradeForClass = 0f;
        Float sumGradesForClass = 0f;
        int numberGrades = 0;
        for (Student u : studentsOfClassName(nameClass)) {
            for (Activity z : activitiesForClass(nameClass)) {
                for (int ocena : gradeService.getGradesForStudentActivity(u.getidStudent(), z.getIdActivity())) {
                    sumGradesForClass = sumGradesForClass + ocena;
                    numberGrades++;
                }
            }
        }
        if (numberGrades > 0) {
            averageGradeForClass = sumGradesForClass / numberGrades;
        }
        return averageGradeForClass;
    }

    private Float averageGradeForTeacherSubject(int idTeacher, int idSubject) {
        Float averageGradeForTeacherSubject = 0f;
        Float sumGrades = 0f;
        int numberGrades = 0;
        for (Activity z : activities) {
            if (z.getIdTeacher() == idTeacher && z.getIdSubject() == idSubject) {
                for (Grade o : grades) {
                    if (o.getIdActivity() == z.getIdActivity()) {
                        sumGrades = sumGrades + o.getValueGrade();
                        numberGrades++;
                    }
                }
            }
        }
        if (numberGrades > 0) {
            averageGradeForTeacherSubject = sumGrades / numberGrades;
        }
        return averageGradeForTeacherSubject;
    }

    private Float averageAllGradesForStudent(Integer idStudent) {
        Float averageAllGradesForStudent = -1f;
        Float sumGradesForStudent = 0f;
        int numberGrades = 0;
        for (Grade o : grades) {
            if (o.getIdStudent() == idStudent) {
                sumGradesForStudent = sumGradesForStudent + o.getValueGrade();
                numberGrades++;
            }
        }
        if (numberGrades > 0) {
            averageAllGradesForStudent = sumGradesForStudent / numberGrades;
        }
        return averageAllGradesForStudent;
    }

    private Float averageGradeStudentForActivity(Integer idStudent, Integer idActivity) {
        Float averageGradeStudentForActivity = -1f;
        Float sumGradesStudent = 0f;
        int numberGrades = 0;
        for (Grade o : grades) {
            if (o.getIdStudent() == idStudent && o.getIdActivity() == idActivity) {
                sumGradesStudent = sumGradesStudent + o.getValueGrade();
                numberGrades++;
            }
        }
        if (numberGrades > 0) {
            averageGradeStudentForActivity = sumGradesStudent / numberGrades;
        }
        return averageGradeStudentForActivity;
    }

    private String nameClassOfStudentId(int idStudent) {
        String nameClassOfStudent = "";
        for (StudentsOfClass u : studentsOfClassService.getStudentsOfClasses()) {
            for (Integer idU : u.getIdStudentsOfClass()) {
                if (idU == idStudent) {
                    nameClassOfStudent = classService.getClasses().get(u.getIdClass() - 1).getNameClass();
                }
            }
        }
        return nameClassOfStudent;
    }

    private ArrayList<Student> studentsOfClassName(String nameClass) {
        ArrayList<Student> studentsOfClass = new ArrayList<>();
        for (int j = 0; j < studentsOfClassService.getStudentsOfClasses().get(classService.getIdClass(nameClass) - 1).getIdStudentsOfClass().size(); j++) {
            studentsOfClass.add(new Student(studentsOfClassService.getStudentsOfClasses().get(classService.getIdClass(nameClass) - 1).getIdStudentsOfClass().get(j), studentService.getStudents().get(studentsOfClassService.getStudentsOfClasses().get(classService.getIdClass(nameClass) - 1).getIdStudentsOfClass().get(j) - 1).getName(), studentService.getStudents().get(studentsOfClassService.getStudentsOfClasses().get(classService.getIdClass(nameClass) - 1).getIdStudentsOfClass().get(j) - 1).getSurname()));
        }
        return studentsOfClass;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private JMenuBar createMainMenu() {
        LinkedHashMap<String, String[]> rows1 = new LinkedHashMap<>();
        LinkedHashMap<String, String[]> rowsMain = new LinkedHashMap<>();
        String menuA = "KLASY";
        String A1 = "A1";
        String A2 = "A2";
        String A3 = "A3";
        String A4 = "A4";
        String menuB = "PRZEDMIOTY";
        String B1 = "B1";
        String B2 = "B2";
        String B3 = "B3";
        String B4 = "B4";
        String menuC = "NAUCZYCIELE";
        String C1 = "C1";
        String C2 = "C2";
        String C3 = "C3";
        String C4 = "C4";
        String menuD = "UCZNIOWIE";
        String D1 = "D1";
        String D2 = "D2";
        String D3 = "D3";
        String D4 = "D4";
        String menuE = "PLAN ZAJĘĆ";
        String E1 = "E1";
        String E2 = "E2";
        String E3 = "E3";
        String E4 = "E4";
        String menuF = "F";
        String F1 = "F1";
        String F2 = "F2";
        String F3 = "F3";
        String F4 = "F4";
        //rowsMain.put(menuA, new String[]{A1, A2, A3, A4});
        String[] menuANamesTab = new String[classes.size() + 1];
        for (Class aClass : classes) {
            menuANamesTab[classes.indexOf(aClass)] = "Klasa nr " + aClass.getNameClass();
        }
        menuANamesTab[menuANamesTab.length - 1] = "Spis ";
        ArrayList<String> nameSubjects = new ArrayList<String>();
        String[] menuBNameTab = new String[subjects.size() + 1];
        for (Subject subject : subjects) {
            nameSubjects.add(subject.getNameSubject());
        }
        nameSubjects.sort(null);
        for (int i = 0; i < nameSubjects.size(); i++) {
            menuBNameTab[i] = nameSubjects.get(i);
        }
        menuBNameTab[menuBNameTab.length - 1] = "Spis ";
        ArrayList<String> nameTeachers = new ArrayList<String>();
        String[] menuCNameTab = new String[teachers.size() + 1];
        for (Teacher teacher : teachers) {
            nameTeachers.add(teacher.getName() + " " + teacher.getSurname());
        }
        nameTeachers.sort(null);
        for (int i = 0; i < nameTeachers.size(); i++) {
            menuCNameTab[i] = nameTeachers.get(i);
        }
        menuCNameTab[menuCNameTab.length - 1] = "Spis ";
        String[] menuDNameTab = new String[1];
        menuDNameTab[0] = "Alfabetyczny spis uczniów";
        String[] menuENameTab = new String[1];
        menuENameTab[0] = "Plan zajęć wszystkie klasy";
        rowsMain.put(menuA, menuANamesTab);
        rowsMain.put(menuB, menuBNameTab);
        rowsMain.put(menuC, menuCNameTab);
        rowsMain.put(menuD, menuDNameTab);
        rowsMain.put(menuE, menuENameTab);
        //rowsMain.put(menuF, new String[]{F1, F2, F3, F4});
        //rows1.put(A1, new String[]{"A1_1", "A1_2", "A1_3"});
        //rows1.put(A2, new String[]{"A2_1", "A2_2", "A2_3"});
        //rows1.put(A3, new String[]{"A3_1", "A3_2", "A3_3"});
        //rows1.put(B1, new String[]{"B1_1", "B1_2", "B1_3"});
        //rows1.put(B2, new String[]{"B2_1", "B2_2", "B2_3"});
        menuBar = new JMenuBar();
        for (String m : rowsMain.keySet()) {
            JMenu menu = new JMenu(" " + m + " ");
            menuBar.add(menu);
            for (String m1 : rowsMain.get(m)) {
                if (rows1.containsKey(m1)) {
                    JMenu subMenu1 = new JMenu(m1);
                    menu.add(subMenu1);
                    for (String m2 : rows1.get(m1)) {
                        JMenuItem subMenu2 = new JMenuItem(m2);
                        subMenu1.add(subMenu2);
                        subMenu1.addSeparator();
                    }
                } else {
                    JMenuItem subMenu11 = new JMenuItem(m1);

                    menu.add(subMenu11);
                    subMenu11.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelMain.removeAll();
                            subMenu11.setVisible(true);
                            subMenu11.setFocusable(true);
                            paneSub.removeAll();
                            frameSub.getContentPane().removeAll();
                            frameSub.setVisible(false);
                            box2 = new Box(BoxLayout.X_AXIS);
                            box3 = new Box(BoxLayout.Y_AXIS);
                            selectedColumn = -1;
                            if (m.equals(menuA)) {
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box2.add(box3);
                                scrolPanelMain.setViewportView(createPanelClasses(m1.replaceAll("Klasa nr ", "")));
                            }
                            if (m.equals(menuB)) {
                                scrolPanelMain.setViewportView(createPanelSubjects(m1));
                            }
                            if (m.equals(menuC)) {
                                scrolPanelMain.setViewportView(createPanelTeachers(m1));
                            }
                            if (m.equals(menuD)) {
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box2.add(box3);
                                scrolPanelMain.setViewportView(createPanelStudents(m1));
                            }
                            if (m.equals(menuE)) {
                                scrolPanelMain.setViewportView(createPanelSchedule(m1));
                            }
                            menuBar.setVisible(true);
                            menuBar.setFocusable(true);
                            menuBar.repaint();
                            menuBar.revalidate();
                            menuBar.setOpaque(true);
                            menuBar.setEnabled(true);
                        }
                    });
                }
                menu.addSeparator();
            }
        }
        menuBar.setVisible(true);
        mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
        return menuBar;
    }

    private JPanel createPanelClasses(String nameClass) {
        panelClasses.removeAll();
        if (!nameClass.equals("Spis ")) {
            String[] columnNames = new String[3 + namesSubjectsForClass(nameClass).size()];
            columnNames[0] = "ID ucznia";
            columnNames[1] = "Imię ucznia";
            columnNames[2] = "Nazwisko ucznia";
            for (int j = 0; j < namesSubjectsForClass(nameClass).size(); j++) {
                columnNames[3 + j] = namesSubjectsForClass(nameClass).get(j) + " " + teachers.get(activitiesForClass(nameClass).get(j).getIdTeacher() - 1).getName() + " " + teachers.get(activitiesForClass(nameClass).get(j).getIdTeacher() - 1).getSurname();
            }

            JTable tableGradesClass = new JTable(new TableModel() {
                @Override
                public int getRowCount() {
                    return studentsOfClassName(nameClass).size() + 1;
                }

                @Override
                public int getColumnCount() {
                    return 3 + namesSubjectsForClass(nameClass).size();
                }

                @Override
                public String getColumnName(int columnIndex) {
                    return columnNames[columnIndex];
                }

                @Override
                public java.lang.Class getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object newValueCell = null;
                    if (rowIndex < getRowCount() - 1) {
                        if (columnIndex == 0) {
                            newValueCell = studentsOfClassName(nameClass).get(rowIndex).getidStudent();
                        }
                        if (columnIndex == 1) {
                            newValueCell = studentsOfClassName(nameClass).get(rowIndex).getName();
                        }
                        if (columnIndex == 2) {
                            newValueCell = studentsOfClassName(nameClass).get(rowIndex).getSurname();
                        }
                        if (columnIndex > 2) {
                            newValueCell = gradeService.getGradesForStudentActivity(studentsOfClassName(nameClass).get(rowIndex).getidStudent(), activitiesForClass(nameClass).get(columnIndex - 3).getIdActivity());
                        }
                    }
                    if (rowIndex == getRowCount() - 1 && columnIndex == 2) {
                        newValueCell = "średnia ocen:";
                    }
                    if (rowIndex == getRowCount() - 1 && columnIndex > 2) {
                        if (gradeService.averageGradeClassFromActivity(activitiesForClass(nameClass).get(columnIndex - 3).getIdActivity()) > 0) {
                            newValueCell = gradeService.averageGradeClassFromActivity(activitiesForClass(nameClass).get(columnIndex - 3).getIdActivity());
                        }
                    }
                    return newValueCell;
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                }

                @Override
                public void addTableModelListener(TableModelListener l) {

                }

                @Override
                public void removeTableModelListener(TableModelListener l) {

                }
            });

            for (int i = 0; i < tableGradesClass.getColumnCount(); i++) {
                tableGradesClass.getColumn(columnNames[i]).setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JLabel newL = new JLabel();
                        String rowTemp = "";
                        if (tableGradesClass.getValueAt(row, column) != null) {
                            rowTemp = tableGradesClass.getValueAt(row, column).toString();
                            if ((row == selectedRow && column == selectedColumn)) {
                                newL.setText(rowTemp);
                                newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                newL.setForeground(Color.RED);
                                newL.setBackground(Color.yellow);
                                newL.setOpaque(true);
                            } else {
                                newL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                                if (row == tableGradesClass.getRowCount() - 1) {
                                    newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                    newL.setBackground(Color.LIGHT_GRAY);
                                    newL.setOpaque(true);
                                }
                                if (column >= 0 && column <= 2) {
                                    newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                }
                                newL.setText(rowTemp);
                            }
                            if (isSelected) {

                                box2 = new Box(BoxLayout.X_AXIS);
                                box3 = new Box(BoxLayout.Y_AXIS);
                                if (tableGradesClass.getSelectedRow() >= 0 && tableGradesClass.getSelectedRow() < studentsOfClassName(nameClass).size() && tableGradesClass.getSelectedColumn() > 2 && tableGradesClass.getSelectedColumn() < activitiesForClass(nameClass).size() + 3) {
                                    if (tableGradesClass.getSelectedColumn() > -1 && tableGradesClass.getSelectedRow() > -1) {
                                        selectedColumn = tableGradesClass.getSelectedColumn();
                                        selectedRow = tableGradesClass.getSelectedRow();
                                    }
                                    int idSelectedStudent = studentsOfClassName(nameClass).get(tableGradesClass.getSelectedRow()).getidStudent();
                                    int idSelectedActivity = activitiesForClass(nameClass).get(tableGradesClass.getSelectedColumn() - 3).getIdActivity();
                                    String nameSelectedStudent = studentService.getStudent(idSelectedStudent - 1).getName();
                                    String surnameSelectedStudent = studentService.getStudent(idSelectedStudent - 1).getSurname();
                                    String nameSelectedSubject = subjects.get(activities.get(idSelectedActivity - 1).getIdSubject() - 1).getNameSubject();
                                    String nameStudentSubject = idSelectedStudent + ") " + nameSelectedStudent + " " + surnameSelectedStudent + " - " + nameSelectedSubject + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getName() + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getSurname();

                                    box3.add(new JLabel(idSelectedStudent + ") " + nameSelectedStudent + " " + surnameSelectedStudent)).setFont(new Font(null, Font.BOLD, 16));
                                    box3.add(new JLabel(nameSelectedSubject + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getName() + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getSurname()));
                                    box3.add(new JLabel("Oceny: " + gradeService.getGradesForStudentActivity(idSelectedStudent, idSelectedActivity)));
                                    box2.add(box3);
                                    box2.add(Box.createGlue());
                                    JButton buttonNewGrade = new JButton("Dodaj ocenę");
                                    box2.add(buttonNewGrade);
                                    buttonNewGrade.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            int newGrade = gradeService.selectGradeFor(nameStudentSubject);
                                            if (newGrade != -1) {
                                                gradeService.addGrade(new Grade(idSelectedStudent, idSelectedActivity, newGrade, LocalDateTime.now()));
                                                box2 = new Box(BoxLayout.X_AXIS);
                                                box3 = new Box(BoxLayout.Y_AXIS);
                                                box3.add(new JLabel(idSelectedStudent + ") " + nameSelectedStudent + " " + surnameSelectedStudent)).setFont(new Font(null, Font.BOLD, 16));
                                                box3.add(new JLabel(nameSelectedSubject + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getName() + " " + teachers.get(activities.get(idSelectedActivity - 1).getIdTeacher() - 1).getSurname()));
                                                box3.add(new JLabel("Oceny: " + gradeService.getGradesForStudentActivity(idSelectedStudent, idSelectedActivity)));
                                                box2.add(box3);
                                                box2.add(Box.createGlue());
                                                box2.add(buttonNewGrade);
                                                createPanelClasses(nameClass);
                                            }
                                        }
                                    });
                                } else {
                                    box3.add(new JLabel(" "));
                                    box3.add(new JLabel(" "));
                                    box3.add(new JLabel(" "));
                                    box2.add(box3);
                                    selectedColumn = -1;
                                    selectedRow = -1;
                                }
                                createPanelClasses(nameClass);
                            }
                        }
                        return newL;
                    }
                });
            }
            int totalHeaderTableWidth = 0;
            for (int i = 0; i < columnNames.length; i++) {
                JLabel label = new JLabel(columnNames[i]);
                tableGradesClass.getColumn(columnNames[i]).setPreferredWidth(label.getPreferredSize().width + 10);
                totalHeaderTableWidth = totalHeaderTableWidth + label.getPreferredSize().width + 10;
            }
            tableGradesClass.setRowHeight(20);

            JLabel headTable1 = new JLabel("KLASA nr " + nameClass + " - spis ocen");
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(totalHeaderTableWidth, (headTable1.getPreferredSize().height + tableGradesClass.getTableHeader().getPreferredSize().height + tableGradesClass.getPreferredSize().height) + box2.getPreferredSize().height));
            boxMain.add(headTable1);
            boxMain.add(tableGradesClass.getTableHeader());
            boxMain.add(tableGradesClass);
            boxMain.add(Box.createGlue());
            boxMain.add(box2);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelClasses.add(scrollBox);
            panelClasses.setSize(widthScreen, heightScreen);
            panelClasses.setBackground(Color.BLUE);
        } else {
            String[] columnNames = new String[3];
            columnNames[0] = "model.Klasa Nr";
            columnNames[1] = "Przedmioty";
            columnNames[2] = "Śrenia ocen";

            JTable tableGrades = new JTable(new Object[classes.size()][3], columnNames);
            for (int lo = 0; lo <= classes.size() - 1; lo++) {
                tableGrades.setValueAt(classes.get(lo).getNameClass(), lo, 0);
                tableGrades.setValueAt(namesSubjectsForClass(classes.get(lo).getNameClass()), lo, 1);
                tableGrades.setValueAt(averageGradeForClass(classes.get(lo).getNameClass()), lo, 2);
            }
            tableGrades.setAutoCreateRowSorter(false);
            tableGrades.getColumn(columnNames[0]).setPreferredWidth(2);
            tableGrades.getColumn(columnNames[1]).setPreferredWidth(800);
            tableGrades.getColumn(columnNames[2]).setPreferredWidth(10);
            JLabel headTable1 = new JLabel("Spis klas " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(1000, (tableGrades.getRowCount() + 6) * tableGrades.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(tableGrades.getTableHeader());
            boxMain.add(tableGrades);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelClasses.add(scrollBox);
            panelClasses.setSize(widthScreen, heightScreen);
            panelClasses.setBackground(Color.BLUE);
        }
        return panelClasses;
    }

    private JPanel createPanelSubjects(String nameSubject) {
        panelSubjects.removeAll();
        if (!nameSubject.equals("Spis ")) {
            String[] columnNames = new String[6];
            columnNames[0] = "ID nauczyciela";
            columnNames[1] = "Imię";
            columnNames[2] = "Nazwisko";
            columnNames[3] = "Id zajęcia";
            columnNames[4] = "Zajęcie w klasie";
            columnNames[5] = "Średnia ocen";
            JTable table1 = new JTable(new Object[activitiesForSubject(nameSubject).size()][6], columnNames);
            for (int lo = 0; lo <= activitiesForSubject(nameSubject).size() - 1; lo++) {
                table1.setValueAt(activitiesForSubject(nameSubject).get(lo).getIdTeacher(), lo, 0);
                table1.setValueAt(teachers.get(activitiesForSubject(nameSubject).get(lo).getIdTeacher() - 1).getName(), lo, 1);
                table1.setValueAt(teachers.get(activitiesForSubject(nameSubject).get(lo).getIdTeacher() - 1).getSurname(), lo, 2);
                table1.setValueAt(activitiesForSubject(nameSubject).get(lo).getIdActivity(), lo, 3);
                table1.setValueAt(classes.get(activitiesForSubject(nameSubject).get(lo).getIdClass() - 1).getNameClass(), lo, 4);
                table1.setValueAt(gradeService.averageGradeClassFromActivity(activitiesForSubject(nameSubject).get(lo).getIdActivity()), lo, 5);
            }
            table1.setSelectionForeground(Color.BLUE);
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Lista zajęć przedmiotu: " + nameSubject);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(6 * 100, (activitiesForSubject(nameSubject).size() + 5) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewAactivity = new JButton("Dodaj nowe zajęcie przedmiotu " + nameSubject);
            box2.add(Box.createGlue());
            box2.add(buttonNewAactivity);
            boxMain.add(box2);
            buttonNewAactivity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Teacher newTeacher = selectTeacherForSubject(nameSubject);
                    if (newTeacher.getIdTeacher() > 0) {
                        String nowaKlasa = classService.selectClassForActivityOfTeacherId(newTeacher.getName() + newTeacher.getSurname());
                        if (!nowaKlasa.equals("")) {
                            activityService.addActivity(new Activity(activities.size() + 1, subjectService.getIdSubject(nameSubject), newTeacher.getIdTeacher(), classService.getIdClass(nowaKlasa), LocalDateTime.now()));
                            createPanelSubjects(nameSubject);
                        }
                    }
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelSubjects.add(scrollBox);
            panelSubjects.setSize(widthScreen, heightScreen);
            panelSubjects.setBackground(Color.BLUE);
        } else {
            String[] columnNames = new String[4];
            columnNames[0] = "Przedmiot";
            columnNames[1] = "model.Nauczyciel";
            columnNames[2] = "model.Klasa";
            columnNames[3] = "Średnia ocen";
            ArrayList<String> namesOfSubjectsForActivity = new ArrayList<String>();
            ArrayList<String> namesOfSubjectsforAllTeachers = new ArrayList<String>();
            ArrayList<String> teachersForAllSubjects = new ArrayList<String>();
            ArrayList<ArrayList<String>> namesOfClassesForAllTeachers = new ArrayList<ArrayList<String>>();
            for (Subject pn : subjects) {
                namesOfSubjectsForActivity.add(pn.getNameSubject());
            }
            namesOfSubjectsForActivity.sort(null);
            for (String npn : namesOfSubjectsForActivity) {
                if (teachersForSubject(subjectService.getIdSubject(npn)).size() > 0) {
                    for (Teacher n : teachersForSubject(subjectService.getIdSubject(npn))) {
                        namesOfSubjectsforAllTeachers.add(npn);
                        teachersForAllSubjects.add(n.getName() + " " + n.getSurname());
                        namesOfClassesForAllTeachers.add(namesClassForSubjectTeacher(subjectService.getIdSubject(npn), n.getIdTeacher()));
                    }
                } else {
                    namesOfSubjectsforAllTeachers.add(npn);
                    teachersForAllSubjects.add("");
                    namesOfClassesForAllTeachers.add(new ArrayList<String>());
                }
            }
            JTable table1 = new JTable(new Object[namesOfSubjectsforAllTeachers.size()][4], columnNames);
            for (int lo = 0; lo <= namesOfSubjectsforAllTeachers.size() - 1; lo++) {
                table1.setValueAt(namesOfSubjectsforAllTeachers.get(lo), lo, 0);
                table1.setValueAt(teachersForAllSubjects.get(lo), lo, 1);
                table1.setValueAt(namesOfClassesForAllTeachers.get(lo), lo, 2);
                if (!teachersForAllSubjects.get(lo).equals("")) {
                    table1.setValueAt(averageGradeForTeacherSubject(teacherService.getIdTeacher(teachersForAllSubjects.get(lo)), subjectService.getIdSubject(namesOfSubjectsforAllTeachers.get(lo))), lo, 3);
                }
            }
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Spis przedmiotów nauczania " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(500, (table1.getRowCount() + 6) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewSubjetTeaching = new JButton("Dodaj nowy przedmiot");
            box2.add(Box.createGlue());
            box2.add(buttonNewSubjetTeaching);
            boxMain.add(box2);
            buttonNewSubjetTeaching.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    subjectService.selectSubject();
                    subjects = subjectService.getSubjects();
                    mainFrame.remove(menuBar);
                    mainFrame.setJMenuBar(createMainMenu());
                    mainFrame.setVisible(true);
                    createPanelSubjects(nameSubject);
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelSubjects.add(scrollBox);
            panelSubjects.setSize(widthScreen, heightScreen);
            panelSubjects.setBackground(Color.BLUE);
        }
        return panelSubjects;
    }

    private JPanel createPanelTeachers(String nameSurnameTeacher) {
        panelTeachers.removeAll();
        if (!nameSurnameTeacher.equals("Spis ")) {
            String[] columnNames = new String[4];
            columnNames[0] = "ID zajęcia";
            columnNames[1] = "Nazwa przedmiotu";
            columnNames[2] = "model.Klasa";
            columnNames[3] = "Średnia ocen";
            JTable table1 = new JTable(new Object[activitiesForTeacher(nameSurnameTeacher).size()][4], columnNames);
            for (int lo = 0; lo <= activitiesForTeacher(nameSurnameTeacher).size() - 1; lo++) {
                table1.setValueAt(activitiesForTeacher(nameSurnameTeacher).get(lo).getIdActivity(), lo, 0);
                table1.setValueAt(subjects.get(activitiesForTeacher(nameSurnameTeacher).get(lo).getIdSubject() - 1).getNameSubject(), lo, 1);
                table1.setValueAt(classes.get(activitiesForTeacher(nameSurnameTeacher).get(lo).getIdClass() - 1).getNameClass(), lo, 2);
                table1.setValueAt(gradeService.averageGradeClassFromActivity(activitiesForTeacher(nameSurnameTeacher).get(lo).getIdActivity()), lo, 3);
            }
            table1.setSelectionForeground(Color.BLUE);
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Lista zajęć nauczyciela: " + nameSurnameTeacher);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(6 * 100, (activitiesForTeacher(nameSurnameTeacher).size() + 5) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonAddNewActivity = new JButton("Dodaj nowe zajęcie nauczyciela " + nameSurnameTeacher);
            box2.add(Box.createGlue());
            box2.add(buttonAddNewActivity);
            boxMain.add(box2);
            buttonAddNewActivity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Subject newSubject = selectSubjectForTeacher(teacherService.getIdTeacher(nameSurnameTeacher));
                    if (newSubject.getIdSubject() > 0) {
                        String nowaKlasa = classService.selectClassForActivityOfTeacherId(nameSurnameTeacher);
                        if (!nowaKlasa.equals("")) {
                            activityService.addActivity(new Activity(activities.size() + 1, newSubject.getIdSubject(), teacherService.getIdTeacher(nameSurnameTeacher), classService.getIdClass(nowaKlasa), LocalDateTime.now()));
                            createPanelTeachers(nameSurnameTeacher);
                        }
                    }
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelTeachers.add(scrollBox);
            panelTeachers.setSize(widthScreen, heightScreen);
            panelTeachers.setBackground(Color.BLUE);
        } else {
            String[] columnNames = new String[4];
            columnNames[0] = "model.Nauczyciel";
            columnNames[1] = "Przedmiot";
            columnNames[2] = "model.Klasa";
            columnNames[3] = "Średnia ocen";
            ArrayList<String> teachersOfSchool = new ArrayList<String>();
            ArrayList<String> teachersOfAllSubjects = new ArrayList<String>();
            ArrayList<String> namesSubjects = new ArrayList<String>();
            ArrayList<ArrayList<String>> namesClassForAllTeachers = new ArrayList<ArrayList<String>>();
            for (Teacher n : teachers) {
                teachersOfSchool.add(n.getName() + " " + n.getSurname());
            }
            teachersOfSchool.sort(null);
            for (String ns : teachersOfSchool) {
                if (namesSubjectsForTeacher(teacherService.getIdTeacher(ns)).size() > 0) {
                    for (String lp : namesSubjectsForTeacher(teacherService.getIdTeacher(ns))) {
                        teachersOfAllSubjects.add(ns);
                        namesSubjects.add(lp);
                        namesClassForAllTeachers.add(namesClassForSubjectTeacher(subjectService.getIdSubject(lp), teacherService.getIdTeacher(ns)));
                    }
                } else {
                    teachersOfAllSubjects.add(ns);
                    namesSubjects.add("");
                    namesClassForAllTeachers.add(new ArrayList<String>());
                }
            }
            JTable table1 = new JTable(new Object[teachersOfAllSubjects.size()][4], columnNames);
            for (int lo = 0; lo <= teachersOfAllSubjects.size() - 1; lo++) {
                table1.setValueAt(teachersOfAllSubjects.get(lo), lo, 0);
                table1.setValueAt(namesSubjects.get(lo), lo, 1);
                table1.setValueAt(namesClassForAllTeachers.get(lo), lo, 2);
                if (!namesSubjects.get(lo).equals("")) {
                    table1.setValueAt(averageGradeForTeacherSubject(teacherService.getIdTeacher(teachersOfAllSubjects.get(lo)), subjectService.getIdSubject(namesSubjects.get(lo))), lo, 3);
                }
            }
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Spis nauczycieli " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(500, (table1.getRowCount() + 6) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewTeacher = new JButton("Dodaj nowego nauczyciela");
            box2.add(Box.createGlue());
            box2.add(buttonNewTeacher);
            boxMain.add(box2);
            buttonNewTeacher.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    teacherService.inputNewTeacher();
                    teachers = teacherService.getTeachers();
                    mainFrame.remove(menuBar);
                    mainFrame.setJMenuBar(createMainMenu());
                    mainFrame.setVisible(true);
                    box2.removeAll();
                    createPanelTeachers(nameSurnameTeacher);
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelTeachers.add(scrollBox);
            panelTeachers.setSize(widthScreen, heightScreen);
            panelTeachers.setBackground(Color.BLUE);
        }
        return panelTeachers;
    }

    private JPanel createPanelStudents(String equals) {
        panelStudents.removeAll();
        panelStudents.setAlignmentY(JPanel.TOP_ALIGNMENT);
        alphabetStudentSchoolList = studentService.alphabetStudentSchoolList();
        if (equals.equals("Alfabetyczny spis uczniów")) {
            String[] columnNames = new String[5];
            columnNames[0] = "Imię ucznia";
            columnNames[1] = "Nazwisko ucznia";
            columnNames[2] = "ID ucznia";
            columnNames[3] = "model.Klasa";
            columnNames[4] = "Średnia ocen";
            JTable tableStudents = new JTable(new TableModel() {
                @Override
                public int getRowCount() {
                    return studentService.getStudentSize();
                }

                @Override
                public int getColumnCount() {
                    return columnNames.length;
                }

                @Override
                public String getColumnName(int columnIndex) {
                    return columnNames[columnIndex];
                }

                @Override
                public java.lang.Class getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object newValueCell = null;
                    if (rowIndex > -1) {
                        if (columnIndex == 0) {
                            newValueCell = alphabetStudentSchoolList.get(rowIndex).getName();
                        }
                        if (columnIndex == 1) {
                            newValueCell = alphabetStudentSchoolList.get(rowIndex).getSurname();
                        }
                        if (columnIndex == 2) {
                            newValueCell = alphabetStudentSchoolList.get(rowIndex).getidStudent();
                        }
                        if (columnIndex == 3) {
                            newValueCell = nameClassOfStudentId(alphabetStudentSchoolList.get(rowIndex).getidStudent());
                        }
                        if (columnIndex == 4) {
                            if (averageAllGradesForStudent(alphabetStudentSchoolList.get(rowIndex).getidStudent()) > -1) {
                                newValueCell = averageAllGradesForStudent(alphabetStudentSchoolList.get(rowIndex).getidStudent());
                            }
                        }
                    }
                    return newValueCell;
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                }

                @Override
                public void addTableModelListener(TableModelListener l) {

                }

                @Override
                public void removeTableModelListener(TableModelListener l) {

                }
            });

            int totalHeaderTableWidth = 0;
            for (int i = 0; i < columnNames.length; i++) {
                JLabel label = new JLabel(columnNames[i]);
                tableStudents.getColumn(columnNames[i]).setPreferredWidth(label.getPreferredSize().width + 10);
                totalHeaderTableWidth = totalHeaderTableWidth + label.getPreferredSize().width + 10;
            }
            tableStudents.setRowHeight(20);
            JLabel headTable1 = new JLabel("Alfabetyczny spis uczniów " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.add(headTable1);
            JButton buttonNewPupil = new JButton();
            buttonNewPupil.setText("Dodaj nowego ucznia");
            box2.add(buttonNewPupil);
            boxMain.add(Box.createGlue());
            boxMain.add(box2);
            boxMain.add(tableStudents.getTableHeader());
            boxMain.add(tableStudents);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            //scrollBox.setPreferredSize(new Dimension(totalHeaderTableWidth + 20, (Toolkit.getDefaultToolkit().getScreenSize().height) - 100));
            panelStudents.add(scrollBox);
            panelStudents.setSize(widthScreen, heightScreen);
            panelStudents.setBackground(Color.BLUE);
            frameSub.setVisible(false);
            frameSub.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameSub.setFocusableWindowState(false);
            //frameSub.setLocationRelativeTo(panelUczniowie);
            frameSub.addHierarchyListener(new HierarchyListener() {
                @Override
                public void hierarchyChanged(HierarchyEvent e) {
                    if (!frameSub.isDisplayable()) {
                        selectedColumn = -1;
                        selectedRow = -1;
                        initialSelectedRow = -1;
                        panelStudents.removeAll();
                        box2 = new Box(BoxLayout.X_AXIS);
                        box3 = new Box(BoxLayout.Y_AXIS);
                        createPanelStudents(equals);
                    }
                }
            });
            panelStudents.addHierarchyListener(new HierarchyListener() {
                @Override
                public void hierarchyChanged(HierarchyEvent e) {
                    if (!panelStudents.isDisplayable()) {
                        if (initialSelectedRow == -1) {
                            initialSelectedRow = selectedRow;
                        }
                    }
                }
            });
            for (int i = 0; i < tableStudents.getColumnCount(); i++) {
                tableStudents.getColumn(columnNames[i]).setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (row == initialSelectedRow && initialSelectedRow > -1) {
                            isSelected = true;
                        }
                        JLabel newL = new JLabel();
                        String rowTemp = "";
                        if (tableStudents.getValueAt(row, column) != null) {
                            rowTemp = tableStudents.getValueAt(row, column).toString();
                        }
                        if (isSelected) {
                            if (column == 0 && initialSelectedRow > -1 && tableStudents.getSelectedRow() > -1) {
                                initialSelectedRow = -1;
                                tableStudents.revalidate();
                                tableStudents.repaint();
                            }
                            newL.setText(rowTemp);
                            newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                            newL.setForeground(Color.RED);
                            newL.setBackground(Color.yellow);
                            newL.setOpaque(true);

                        } else {
                            newL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                            newL.setText(rowTemp);
                            newL.setForeground(Color.BLACK);
                            newL.setBackground(Color.WHITE);
                            if (column >= 0 && column <= 1) {
                                newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                            }
                            newL.setOpaque(true);
                        }
                        if (initialSelectedRow > -1) {
                            if (isSelected && column == 0) {
                                frameSub.getContentPane().removeAll();
                                frameSub.setVisible(false);
                                paneSub.removeAll();
                                frameSub.setTitle("Dzienniczek ucznia " + tableStudents.getValueAt(initialSelectedRow, 0).toString() + " " + tableStudents.getValueAt(initialSelectedRow, 1).toString());
                                JScrollPane scrolStudentRekord = scrollRecordStudent(alphabetStudentSchoolList.get(initialSelectedRow).getidStudent());
                                frameSub.getContentPane().add(scrolStudentRekord);
                                selectedRow = initialSelectedRow;
                                frameSub.setBounds(scrollBox.getLocationOnScreen().x + scrollBox.getWidth() + 2, scrollBox.getLocationOnScreen().y, 600, 300);
                                frameSub.setVisible(true);
                                frameSub.revalidate();
                            }
                            if (isSelected) {
                                frameSub.setBounds(scrollBox.getLocationOnScreen().x + scrollBox.getWidth() + 2, scrollBox.getLocationOnScreen().y, 600, 300);
                                frameSub.revalidate();
                                frameSub.setVisible(true);
                            }
                        } else {
                            if (isSelected && selectedRow != tableStudents.getSelectedRow()) {

                                frameSub.getContentPane().removeAll();
                                frameSub.setVisible(false);
                                paneSub.removeAll();
                                frameSub.setTitle("Dzienniczek ucznia " + tableStudents.getValueAt(tableStudents.getSelectedRow(), 0).toString() + " " + tableStudents.getValueAt(tableStudents.getSelectedRow(), 1).toString());
                                JScrollPane scrolDziennikUcznia = scrollRecordStudent(alphabetStudentSchoolList.get(tableStudents.getSelectedRow()).getidStudent());
                                frameSub.getContentPane().add(scrolDziennikUcznia);
                                selectedRow = tableStudents.getSelectedRow();
                                frameSub.setVisible(true);
                                frameSub.revalidate();
                            }
                            if (isSelected && selectedRow > -1) {
                                frameSub.setBounds(scrollBox.getLocationOnScreen().x + scrollBox.getWidth() + 2, scrollBox.getLocationOnScreen().y, 600, 300);
                                frameSub.setVisible(true);
                                frameSub.revalidate();
                            }
                        }
                        return newL;
                    }
                });
            }
            JPanel paneSub1 = new JPanel();
            JFrame frameSub1 = new JFrame("Dodaj nowego ucznia");
            frameSub1.setVisible(false);
            frameSub1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameSub1.setFocusableWindowState(false);
            buttonNewPupil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paneSub1.removeAll();
                    paneSub1.setLayout(new GridLayout(5, 1));
                    paneSub1.add(new JLabel("Imię"));
                    JTextField inputName = new JTextField(20);
                    paneSub1.add(inputName);
                    paneSub1.add(new JLabel("Nazwisko"));
                    JTextField inputSurname = new JTextField(20);
                    paneSub1.add(inputSurname);
                    paneSub1.add(new JLabel("Wybierz klasę nowego ucznia"));
                    String[] nameClasses = new String[classes.size()];
                    for (int i = 0; i < classes.size(); i++) {
                        nameClasses[i] = classes.get(i).getNameClass();
                    }
                    JComboBox<String> inputClassForNewPupil = new JComboBox<>(nameClasses);
                    paneSub1.add(inputClassForNewPupil);
                    JButton buttonOk = new JButton("OK");
                    paneSub1.add(new JLabel());
                    paneSub1.add(new JLabel());
                    paneSub1.add(new JLabel());
                    paneSub1.add(buttonOk);
                    paneSub1.setVisible(true);
                    buttonOk.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!inputName.getText().equals("") && !inputSurname.getText().equals("")) {
                                studentService.addStudent(new Student(studentService.getStudentSize() + 1, inputName.getText(), inputSurname.getText()));
                                gradeService.addGrade(new Grade(grades.size() + 1));
                                for (StudentsOfClass u : studentsOfClasses) {
                                    if (u.getIdClass() == classService.getIdClass(inputClassForNewPupil.getSelectedItem().toString())) {
                                        u.getIdStudentsOfClass().add(studentService.getStudentSize());
                                    }
                                }
                                studentsOfClassService.save();
                            }
                            frameSub1.getContentPane().removeAll();
                            frameSub1.setVisible(false);
                            selectedColumn = -1;
                            selectedRow = -1;
                            alphabetStudentSchoolList = studentService.alphabetStudentSchoolList();
                            for (Student u : alphabetStudentSchoolList) {
                                if (u.getidStudent() == studentService.getStudentSize()) {
                                    initialSelectedRow = alphabetStudentSchoolList.indexOf(u);
                                }
                            }
                            frameSub.getContentPane().removeAll();
                            frameSub.setVisible(false);
                            paneSub.removeAll();
                            panelStudents.removeAll();
                            box2 = new Box(BoxLayout.X_AXIS);
                            box3 = new Box(BoxLayout.Y_AXIS);
                            createPanelStudents(equals);
                        }
                    });
                    inputName.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            inputName.setBackground(Color.YELLOW);
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            inputName.setBackground(Color.white);
                        }
                    });
                    inputSurname.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            inputSurname.setBackground(Color.YELLOW);
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            inputSurname.setBackground(Color.white);
                        }
                    });
                    inputClassForNewPupil.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            inputClassForNewPupil.setBackground(Color.YELLOW);
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            inputClassForNewPupil.setBackground(Color.white);
                        }
                    });
                    frameSub1.getContentPane().add(paneSub1);
                    frameSub1.setLocationRelativeTo(panelStudents);
                    frameSub1.setBounds((widthScreen / 2) - 250, (heightScreen / 2) - 150, 500, 300);
                    frameSub1.setLayout(new FlowLayout(FlowLayout.LEFT));
                    frameSub1.setVisible(true);
                    frameSub1.setFocusableWindowState(true);
                }
            });
        }
        return panelStudents;
    }

    private JPanel createPanelSchedule(String typeOfSchedule) {
        String[] columnNames = new String[1 + DNI_NAUKI_TYGODNIA_USTAWA.length];
        columnNames[0] = "Godzina";
        for (int j = 0; j < DNI_NAUKI_TYGODNIA_USTAWA.length; j++) {
            columnNames[1 + j] = DNI_NAUKI_TYGODNIA_USTAWA[j];
        }
        ArrayList<Activity>[][][] tableAvailableActivities = new ArrayList[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
        ArrayList<String>[][][] tableNamesAvailableActivities = new ArrayList[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
        String[][][] tableNamesActivitiesOfSchedule = new String[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
        Integer[][][] tableIdActivitiesoFSchedule = new Integer[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
        JComboBox<String>[][][] comboAvailableActivitiesForSelection = new JComboBox[classes.size()][GODZINY_ROZPOCZECIA_ZAJEC.length][DNI_NAUKI_TYGODNIA_USTAWA.length];
        for (int k = 0; k < classes.size(); k++) {
            String nameClass = classes.get(k).getNameClass();
            for (int i = 0; i < GODZINY_ROZPOCZECIA_ZAJEC.length; i++) {
                for (int j = 0; j < DNI_NAUKI_TYGODNIA_USTAWA.length; j++) {
                    tableAvailableActivities[k][i][j] = availableActivitiesForClassDayHour(nameClass, DNI_NAUKI_TYGODNIA_USTAWA[j], GODZINY_ROZPOCZECIA_ZAJEC[i]);
                    tableNamesActivitiesOfSchedule[k][i][j] = nameActivityForClassDayHour(nameClass, DNI_NAUKI_TYGODNIA_USTAWA[j], GODZINY_ROZPOCZECIA_ZAJEC[i]);
                    tableIdActivitiesoFSchedule[k][i][j] = idActivityForClassDayHour(nameClass, DNI_NAUKI_TYGODNIA_USTAWA[j], GODZINY_ROZPOCZECIA_ZAJEC[i]);
                    tableNamesAvailableActivities[k][i][j] = new ArrayList<String>();
                    tableNamesAvailableActivities[k][i][j].add(tableNamesActivitiesOfSchedule[k][i][j]);
                    tableNamesAvailableActivities[k][i][j].add("");
                    for (int l = 0; l < tableAvailableActivities[k][i][j].size(); l++) {
                        String nameAvailableActivityForClass = subjects.get(tableAvailableActivities[k][i][j].get(l).getIdSubject() - 1).getNameSubject() + " " + teachers.get(tableAvailableActivities[k][i][j].get(l).getIdTeacher() - 1).getName() + " " + teachers.get(tableAvailableActivities[k][i][j].get(l).getIdTeacher() - 1).getSurname();
                        tableNamesAvailableActivities[k][i][j].add(nameAvailableActivityForClass);
                    }
                }
            }
        }
        JPanel[] tablePanelSchedule = new JPanel[classes.size()];
        for (int k = 0; k < classes.size(); k++) {
            tablePanelSchedule[k] = new JPanel();
        }
        JPanel panelSchedule = new JPanel();
        panelSchedule.setLayout(new GridLayout(classes.size(), 1, 10, 0));
        JButton buttonEditScheduleOk = new JButton("OK");
        buttonEditScheduleOk.setAlignmentX(CENTER_ALIGNMENT);
        JButton buttonEditScheduleCancel = new JButton("CANCEL");
        buttonEditScheduleCancel.setAlignmentX(CENTER_ALIGNMENT);
        JButton buttonEditScheduleEdit = new JButton("Edycja planu zajęć");
        buttonEditScheduleEdit.setAlignmentX(CENTER_ALIGNMENT);
        for (int k = 0; k < classes.size(); k++) {
            String nameClass = classes.get(k).getNameClass();
            tablePanelSchedule[k].removeAll();
            tablePanelSchedule[k].setLayout(new GridLayout(GODZINY_ROZPOCZECIA_ZAJEC.length + 1, DNI_NAUKI_TYGODNIA_USTAWA.length, 2, 2));
            tablePanelSchedule[k].add(new Label("Godzina")).setBackground(Color.white);
            tablePanelSchedule[k].setVisible(true);
            for (int j = 0; j < DNI_NAUKI_TYGODNIA_USTAWA.length; j++) {
                Label labelHour = new Label();
                labelHour.setText(DNI_NAUKI_TYGODNIA_USTAWA[j]);
                labelHour.setBackground(Color.WHITE);
                labelHour.setAlignment(Label.CENTER);
                tablePanelSchedule[k].add(labelHour);
            }
            for (int i = 0; i < GODZINY_ROZPOCZECIA_ZAJEC.length; i++) {
                tablePanelSchedule[k].add(new Label(Integer.toString(GODZINY_ROZPOCZECIA_ZAJEC[i]))).setBackground(Color.white);
                for (int j = 0; j < DNI_NAUKI_TYGODNIA_USTAWA.length; j++) {
                    if (editSchedule) {
                        String[] tableNamesAvailableActivitiesForClass = new String[tableNamesAvailableActivities[k][i][j].size()];
                        for (int z = 0; z < tableNamesAvailableActivities[k][i][j].size(); z++) {
                            tableNamesAvailableActivitiesForClass[z] = tableNamesAvailableActivities[k][i][j].get(z);
                        }
                        comboAvailableActivitiesForSelection[k][i][j] = new JComboBox<String>(tableNamesAvailableActivitiesForClass);
                        if (tableIdActivitiesOfSchedulePrevious[k][i][j] != tableIdActivitiesoFSchedule[k][i][j]) {
                            comboAvailableActivitiesForSelection[k][i][j].setBackground(Color.YELLOW);
                        }
                        comboAvailableActivitiesForSelection[k][i][j].setVisible(true);
                        tablePanelSchedule[k].add(comboAvailableActivitiesForSelection[k][i][j]);
                        int finalK = k;
                        int finalI = i;
                        int finalJ = j;
                        comboAvailableActivitiesForSelection[k][i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean changeSelected = false;
                                if (comboAvailableActivitiesForSelection[finalK][finalI][finalJ].getSelectedIndex() > 0) {
                                    if (comboAvailableActivitiesForSelection[finalK][finalI][finalJ].getSelectedItem().equals("")) {
                                        for (Schedule pz : schedules) {
                                            if (pz.getIdActivity() == tableIdActivitiesoFSchedule[finalK][finalI][finalJ] && pz.getHour() == GODZINY_ROZPOCZECIA_ZAJEC[finalI] && pz.getWeekDay().equals(DNI_NAUKI_TYGODNIA_USTAWA[finalJ])) {
                                                schedules.set(schedules.indexOf(pz), new Schedule());
                                            }
                                        }
                                    }
                                    if (comboAvailableActivitiesForSelection[finalK][finalI][finalJ].getSelectedIndex() > 1) {
                                        for (Schedule pz : schedules) {
                                            if (pz.getIdActivity() == tableIdActivitiesoFSchedule[finalK][finalI][finalJ] && pz.getHour() == GODZINY_ROZPOCZECIA_ZAJEC[finalI] && pz.getWeekDay().equals(DNI_NAUKI_TYGODNIA_USTAWA[finalJ])) {
                                                schedules.set(schedules.indexOf(pz), new Schedule(schedules.indexOf(pz) + 1, tableAvailableActivities[finalK][finalI][finalJ].get(comboAvailableActivitiesForSelection[finalK][finalI][finalJ].getSelectedIndex() - 2).getIdActivity(), DNI_NAUKI_TYGODNIA_USTAWA[finalJ], GODZINY_ROZPOCZECIA_ZAJEC[finalI]));
                                                changeSelected = true;
                                            }
                                        }
                                        if (!changeSelected) {
                                            schedules.add(new Schedule(schedules.size() + 1, tableAvailableActivities[finalK][finalI][finalJ].get(comboAvailableActivitiesForSelection[finalK][finalI][finalJ].getSelectedIndex() - 2).getIdActivity(), DNI_NAUKI_TYGODNIA_USTAWA[finalJ], GODZINY_ROZPOCZECIA_ZAJEC[finalI]));
                                        }
                                    }
                                    editSchedule = true;
                                    panelMain.removeAll();
                                    paneSub.removeAll();
                                    frameSub.getContentPane().removeAll();
                                    frameSub.setVisible(false);
                                    panelSchedule.removeAll();
                                    scrolPanelMain.setViewportView(createPanelSchedule("m1"));
                                    mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
                                    mainFrame.setVisible(true);
                                }
                            }
                        });
                    } else {
                        Label nameActivityOfSchedule = new Label(tableNamesActivitiesOfSchedule[k][i][j]);
                        nameActivityOfSchedule.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
                        nameActivityOfSchedule.setBackground(Color.WHITE);
                        tablePanelSchedule[k].add(nameActivityOfSchedule);
                    }
                }
            }
            tablePanelSchedule[k].setBackground(Color.BLUE);
            tablePanelSchedule[k].setVisible(true);
            Box boxSchedule = new Box(BoxLayout.Y_AXIS);
            if (k == 0) {
                if (editSchedule) {
                    Box boxEditSchedule = new Box(BoxLayout.X_AXIS);
                    boxEditSchedule.add(buttonEditScheduleOk);
                    boxEditSchedule.add(buttonEditScheduleCancel);
                    boxSchedule.add(boxEditSchedule);
                } else {
                    boxSchedule.add(buttonEditScheduleEdit);
                }
            }
            Label labelnameClass = new Label("model.Klasa Nr " + nameClass);
            JPanel panelnameClass = new JPanel();
            panelnameClass.add(labelnameClass);
            panelnameClass.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            panelnameClass.setBackground(Color.LIGHT_GRAY);
            boxSchedule.add(panelnameClass);
            boxSchedule.add(tablePanelSchedule[k]);
            panelSchedule.add(boxSchedule);
        }
        buttonEditScheduleEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableIdActivitiesOfSchedulePrevious = tableIdActivitiesoFSchedule;
                schedulesPrevious = new ArrayList<Schedule>();
                schedulesPrevious.addAll(schedules);
                editSchedule = true;
                panelMain.removeAll();
                paneSub.removeAll();
                frameSub.getContentPane().removeAll();
                frameSub.setVisible(false);
                panelSchedule.removeAll();
                scrolPanelMain.setViewportView(createPanelSchedule("m1"));
                mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
                mainFrame.setVisible(true);
            }
        });
        buttonEditScheduleOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleService.save();
                editSchedule = false;
                panelMain.removeAll();
                paneSub.removeAll();
                frameSub.getContentPane().removeAll();
                frameSub.setVisible(false);
                panelSchedule.removeAll();
                scrolPanelMain.setViewportView(createPanelSchedule("m1"));
                mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
                mainFrame.setVisible(true);
            }
        });
        buttonEditScheduleCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSchedule = false;
                schedules = new ArrayList<Schedule>();
                schedules.addAll(schedulesPrevious);
                panelMain.removeAll();
                paneSub.removeAll();
                frameSub.getContentPane().removeAll();
                frameSub.setVisible(false);
                panelSchedule.removeAll();
                scrolPanelMain.setViewportView(createPanelSchedule("m1"));
                mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
                mainFrame.setVisible(true);
            }
        });
        panelSchedule.setBackground(Color.BLUE);
        panelSchedule.setVisible(true);
        return panelSchedule;
    }

    private JScrollPane scrollRecordStudent(int idStudent) {
        String[] columnNames = new String[6];
        columnNames[0] = "Nazwa przedmiotu";
        columnNames[1] = "Imię i mazwisko nauczyciela";
        columnNames[2] = "ID zajędia";
        columnNames[3] = "model.Klasa nr";
        columnNames[4] = "Oceny z przedmiotu";
        columnNames[5] = "Średnia ocen z przedmiotu";

        JTable tableRecordStudent = new JTable(new TableModel() {
            @Override
            public int getRowCount() {
                return alphabeticActivitiesForStudent(idStudent).size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public java.lang.Class getColumnClass(int columnIndex) {
                if (getValueAt(0, columnIndex) != null) {
                    return getValueAt(0, columnIndex).getClass();
                } else {
                    return Object.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object newValueCell = null;
                if (columnIndex == 0) {
                    newValueCell = subjects.get(alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdSubject() - 1).getNameSubject();
                }
                if (columnIndex == 1) {
                    newValueCell = teachers.get(alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdTeacher() - 1).getName() + " " + teachers.get(alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdTeacher() - 1).getSurname();
                }
                if (columnIndex == 2) {
                    newValueCell = alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdActivity();
                }
                if (columnIndex == 3) {
                    newValueCell = nameClassOfStudentId(idStudent);
                }
                if (columnIndex == 4) {
                    newValueCell = gradeService.getGradesForStudentActivity(idStudent, alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdActivity());
                }
                if (columnIndex == 5) {
                    if (averageGradeStudentForActivity(idStudent, alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdActivity()) > -1) {
                        newValueCell = averageGradeStudentForActivity(idStudent, alphabeticActivitiesForStudent(idStudent).get(rowIndex).getIdActivity());
                    }
                }
                return newValueCell;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        });
        return new JScrollPane(tableRecordStudent);
    }

    private Teacher selectTeacherForSubject(String nameSubject) {
        Teacher newTeacher = new Teacher();
        ArrayList<String> namesSurnamesTeachers = new ArrayList<String>();
        for (Teacher n : teachers) {
            namesSurnamesTeachers.add(n.getName() + " " + n.getSurname());
        }
        namesSurnamesTeachers.sort(null);
        JOptionPane inputNewTeacherforNewActivity = new JOptionPane();
        inputNewTeacherforNewActivity.setWantsInput(true);
        String[] possibleValues = new String[teachers.size()];
        for (int i = 0; i < namesSurnamesTeachers.size(); i++) {
            possibleValues[i] = namesSurnamesTeachers.get(i);
        }
        Object selectedTeacher = JOptionPane.showInputDialog(null,
                "Wybierz nauczyciela dla nowego zajęcia " + nameSubject + ":", "Lista nauczycieli szkoły",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        System.out.println(selectedTeacher);
        if (selectedTeacher != null) {
            newTeacher = new Teacher(teacherService.getIdTeacher(selectedTeacher.toString()), teachers.get(teacherService.getIdTeacher(selectedTeacher.toString()) - 1).getName(), teachers.get(teacherService.getIdTeacher(selectedTeacher.toString()) - 1).getSurname());
        }
        return newTeacher;
    }

    private Subject selectSubjectForTeacher(int idTeacher) {
        Subject newSubject = new Subject(0, "");
        ArrayList<String> namesSubjects = new ArrayList<String>();
        for (Subject p : subjects) {
            namesSubjects.add(p.getNameSubject());
        }
        namesSubjects.sort(null);
        JOptionPane selectSubject = new JOptionPane();
        selectSubject.setWantsInput(true);
        String[] possibleValues = new String[namesSubjects.size()];
        for (int i = 0; i < namesSubjects.size(); i++) {
            possibleValues[i] = namesSubjects.get(i);
        }
        Object selectedSubject = JOptionPane.showInputDialog(null,
                "Wybierz przedmiot dla nauczyciela " + teachers.get(idTeacher - 1).getName() + " " + teachers.get(idTeacher - 1).getSurname() + ":", "Lista przedmiotów nauczania",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedSubject != null) {
            newSubject = new Subject(subjectService.getIdSubject(selectedSubject.toString()), selectedSubject.toString());
        }
        return newSubject;
    }
}