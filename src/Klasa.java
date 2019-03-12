import java.util.ArrayList;

public class Klasa {
    private String nazwaKlasy;
    private ArrayList<Uczen> uczniowie;
    private ArrayList<Nauczyciel> nauczycieleKlasy;

    public Klasa(String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<Nauczyciel> nauczycieleKlasy) {
        this.nazwaKlasy = nazwaKlasy;
        this.uczniowie = uczniowie;
        this.nauczycieleKlasy = nauczycieleKlasy;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public ArrayList<Uczen> getUczniowie() {
        return uczniowie;
    }

    public ArrayList<Nauczyciel> getNauczycieleKlasy() {
        return nauczycieleKlasy;
    }
}