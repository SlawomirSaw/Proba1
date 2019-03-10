import java.util.ArrayList;

public class DziennikPrzedmiotu {
    private Nauczyciel nauczyciel;
    private ArrayList <DzienniczekUcznia> OcenyUczniow;

    public DziennikPrzedmiotu(Nauczyciel nauczyciel, ArrayList<DzienniczekUcznia> ocenyUczniow) {
        this.nauczyciel = nauczyciel;
        OcenyUczniow = ocenyUczniow;
    }

    public Nauczyciel getNauczyciel() {
        return nauczyciel;
    }

    public void setNauczyciel(Nauczyciel nauczyciel) {
        this.nauczyciel = nauczyciel;
    }

    public ArrayList<DzienniczekUcznia> getOcenyUczniow() {
        return OcenyUczniow;
    }

    public void setOcenyUczniow(ArrayList<DzienniczekUcznia> ocenyUczniow) {
        OcenyUczniow = ocenyUczniow;
    }
}
