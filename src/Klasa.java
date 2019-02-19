import java.util.ArrayList;

public class Klasa {
    private String nazwaKlasy;
    private ArrayList<Uczen> uczniowie;
    private ArrayList<NauczycielPrzedmiotu> nauczyciele;

    public Klasa(String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<NauczycielPrzedmiotu> nauczyciele) {
        this.nazwaKlasy = nazwaKlasy;
        this.uczniowie = uczniowie;
        this.nauczyciele = nauczyciele;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public ArrayList<Uczen> getUczniowie() {
        return uczniowie;
    }

    public ArrayList<NauczycielPrzedmiotu> getNauczyciele() {
        return nauczyciele;
    }
}
