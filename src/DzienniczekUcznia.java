import java.util.ArrayList;

public class DzienniczekUcznia {
    private Uczen uczen;
    private ArrayList <NauczycielPrzedmiotu> nauczycielePrzedmiotow;
    private ArrayList <OcenyPrzedmiotu> ocenyPrzedmiotow;

    public DzienniczekUcznia(Uczen uczen, ArrayList<NauczycielPrzedmiotu> nauczycielePrzedmiotow, ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotow) {
        this.uczen = uczen;
        this.nauczycielePrzedmiotow = nauczycielePrzedmiotow;
        this.ocenyPrzedmiotow = ocenyPrzedmiotow;
    }

    public Uczen getUczen() {
        return uczen;
    }

    public void setUczen(Uczen uczen) {
        this.uczen = uczen;
    }

    public ArrayList<NauczycielPrzedmiotu> getNauczycielePrzedmiotow() {
        return nauczycielePrzedmiotow;
    }

    public void setNauczycielePrzedmiotow(ArrayList<NauczycielPrzedmiotu> nauczycielePrzedmiotow) {
        this.nauczycielePrzedmiotow = nauczycielePrzedmiotow;
    }

    public ArrayList<OcenyPrzedmiotu> getOcenyPrzedmiotow() {
        return ocenyPrzedmiotow;
    }

    public void setOcenyPrzedmiotow(ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotow) {
        this.ocenyPrzedmiotow = ocenyPrzedmiotow;
    }
}
