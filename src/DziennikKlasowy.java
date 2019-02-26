import java.util.ArrayList;

public class DziennikKlasowy extends Klasa {
    private ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotu;

    public DziennikKlasowy(String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<NauczycielPrzedmiotu> nauczyciele, ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotu) {
        super(nazwaKlasy, uczniowie, nauczyciele);
        this.ocenyPrzedmiotu = ocenyPrzedmiotu;
    }

    public ArrayList<OcenyPrzedmiotu> getOcenyPrzedmiotu() {
        return ocenyPrzedmiotu;
    }

    public void setOcenyPrzedmiotu(ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotu) {
        this.ocenyPrzedmiotu = ocenyPrzedmiotu;
    }
}

