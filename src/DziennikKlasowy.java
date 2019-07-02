import java.util.ArrayList;

public class DziennikKlasowy {
    private String nazwaKlasy;
    private ArrayList<Uczen> uczniowie;
    private ArrayList<Nauczyciel> nauczycieleKlasy;
    private ArrayList<ArrayList<OcenaZZajeciaUcznia>> ocenyUczniow;

    public DziennikKlasowy(String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<Nauczyciel> nauczycieleKlasy, ArrayList<ArrayList<OcenaZZajeciaUcznia>> ocenyUczniow) {
        //this.index = index;
        this.nazwaKlasy = nazwaKlasy;
        this.uczniowie = uczniowie;
        this.nauczycieleKlasy = nauczycieleKlasy;
        this.ocenyUczniow = ocenyUczniow;
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

    public ArrayList<ArrayList<OcenaZZajeciaUcznia>> getOcenyUczniow() {
        return ocenyUczniow;
    }
}