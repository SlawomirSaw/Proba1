import java.util.ArrayList;

public class Klasa {
    private int index;
    private String nazwaKlasy;
    private ArrayList<Uczen> uczniowie;
    private ArrayList<Nauczyciel> nauczycieleKlasy;

    public Klasa(int index, String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<Nauczyciel> nauczycieleKlasy) {
        this.index = index;
        this.nazwaKlasy = nazwaKlasy;
        this.uczniowie = uczniowie;
        this.nauczycieleKlasy = nauczycieleKlasy;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public void setNazwaKlasy(String nazwaKlasy) {
        this.nazwaKlasy = nazwaKlasy;
    }

    public ArrayList<Uczen> getUczniowie() {
        return uczniowie;
    }

    public void setUczniowie(ArrayList<Uczen> uczniowie) {
        this.uczniowie = uczniowie;
    }

    public ArrayList<Nauczyciel> getNauczycieleKlasy() {
        return nauczycieleKlasy;
    }

    public void setNauczycieleKlasy(ArrayList<Nauczyciel> nauczycieleKlasy) {
        this.nauczycieleKlasy = nauczycieleKlasy;
    }
}
