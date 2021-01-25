import repository.*;
import service.*;
import view.SchoolGUI;

import static configuration.SchoolConfiguration.*;

public class SchoolLauncher {

    public static void main(String[] args) {
        StudentService studentService = new StudentService(new StudentRepository(STUDENT_PATH));
        ClassService classService = new ClassService(new ClassRepository(CLASS_PATH));
        StudentsOfClassService studentsOfClassService = new StudentsOfClassService(new StudentsOfClassRepository(STUDENTSOFCLASS_PATH));
        TeacherService teacherService = new TeacherService(new TeacherRepistory(TEACHER_PATH));
        ActivityService activityService = new ActivityService(new ActivityRepository(ACTIVITY_PATH));
        SubjectService subjectService = new SubjectService(new SubjectRepository(SUBJECT_PATH));
        GradeService gradeService = new GradeService(new GradeRepository(GRADE_PATH));
        ScheduleService scheduleService = new ScheduleService(new ScheduleRepository(SCHEDULE_PATH));
        SchoolGUI gui = new SchoolGUI();
        gui.mainFrame(studentService, classService, studentsOfClassService, teacherService, activityService, subjectService, gradeService, scheduleService);
    }
}