import java.util.ArrayList;

public class OcenyUcznia {
    private int indexUcznia;
    private ArrayList<OcenyZZajeciaUcznia> ocenyZZajeciaUcznia;

    public OcenyUcznia(int indexUcznia, ArrayList<OcenyZZajeciaUcznia> ocenyZZajeciaUcznia) {
        this.indexUcznia = indexUcznia;
        this.ocenyZZajeciaUcznia = ocenyZZajeciaUcznia;
    }

    public int getIndexUcznia() {
        return indexUcznia;
    }

    public ArrayList<OcenyZZajeciaUcznia> getOcenyZZajeciaUcznia() {
        return ocenyZZajeciaUcznia;
    }
}