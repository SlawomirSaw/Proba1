import java.util.ArrayList;

public class DziennikPrzedmiotu {
    private NauczycielPrzedmiotu nauczyciel;
    private ArrayList <DzienniczekUcznia> OcenyUczniow;

    public DziennikPrzedmiotu(NauczycielPrzedmiotu nauczyciel, ArrayList<DzienniczekUcznia> ocenyUczniow) {
        this.nauczyciel = nauczyciel;
        OcenyUczniow = ocenyUczniow;
    }

    public NauczycielPrzedmiotu getNauczyciel() {
        return nauczyciel;
    }

    public void setNauczyciel(NauczycielPrzedmiotu nauczyciel) {
        this.nauczyciel = nauczyciel;
    }

    public ArrayList<DzienniczekUcznia> getOcenyUczniow() {
        return OcenyUczniow;
    }

    public void setOcenyUczniow(ArrayList<DzienniczekUcznia> ocenyUczniow) {
        OcenyUczniow = ocenyUczniow;
    }
}
