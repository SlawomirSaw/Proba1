package configuration;

public interface SchoolConfiguration {
    String NAZWA_SZKOLY = "Szkoła Podstawowa Nr 3";
    String[] NAZWY_PRZEDMIOTOW_USTAWA = {"Biologia", "Fizyka", "Historia", "Jęz. angielski", "Jęz. polski", "Matematyka", "Plastyka", "Wych.fiz.", "Geografia", "Muzyka", "Chemia", "Wych.Obywatelskie"};
    String[] DNI_NAUKI_TYGODNIA_USTAWA = {"Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"};
    String[] NAZWY_KLAS = {"1", "2", "3", "4", "5", "6", "7", "8"};
    int[] GODZINY_ROZPOCZECIA_ZAJEC = {8, 9, 10, 11, 12, 13, 14, 15};
    String STUDENT_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkoly.txt";
    String CLASS_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\Klasy.txt";
    String STUDENTSOFCLASS_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkolyWgKlas.txt";
    String TEACHER_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\Nauczyciele.txt";
    String ACTIVITY_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\Zajecia.txt";
    String SUBJECT_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\PrzedmiotyNauczania.txt";
    String GRADE_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\OcenyUczniow.txt";
    String SCHEDULE_PATH = "D:\\Java\\Próba1\\ZapisanePliki\\PlanyZajec.txt";
}