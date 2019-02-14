import java.util.ArrayList;

public class Klasa {
    private String nazwa;
    private ArrayList<Uczen> uczniowie;
    private ArrayList<Nauczyciel> nauczyciele;

    public Klasa(String nazwa, ArrayList<Uczen> uczniowie, ArrayList<Nauczyciel> nauczyciele) {
        this.nazwa = nazwa;
        this.uczniowie = uczniowie;
        this.nauczyciele = nauczyciele;
    }

    public String getNazwa() {
        return nazwa;
    }

    public ArrayList<Uczen> getUczniowie() {
        return uczniowie;
    }

    public ArrayList<Nauczyciel> getNauczyciele() {
        return nauczyciele;
    }
}
