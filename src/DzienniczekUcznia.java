import java.util.ArrayList;

public class DzienniczekUcznia {
    private Uczen uczen;
    private ArrayList<Nauczyciel> nauczycielePrzedmiotow;
    private ArrayList<OcenyPrzedmiotuUcznia> ocenyUcznia;

    public DzienniczekUcznia(Uczen uczen, ArrayList<Nauczyciel> nauczycielePrzedmiotow, ArrayList<OcenyPrzedmiotuUcznia> ocenyUcznia) {
        this.uczen = uczen;
        this.nauczycielePrzedmiotow = nauczycielePrzedmiotow;
        this.ocenyUcznia = ocenyUcznia;
    }

    public Uczen getUczen() {
        return uczen;
    }

    public ArrayList<Nauczyciel> getNauczycielePrzedmiotow() {
        return nauczycielePrzedmiotow;
    }

    public ArrayList getOcenyUcznia() {
        return ocenyUcznia;
    }
}