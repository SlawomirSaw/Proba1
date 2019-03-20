import java.util.ArrayList;

public class OcenyZZajeciaUcznia {
    private int indexZajecia;
    private ArrayList<Integer> OcenyZZajeciaUcznia;

    public OcenyZZajeciaUcznia(int indexZajecia, ArrayList<Integer> ocenyZZajeciaUcznia) {
        this.indexZajecia = indexZajecia;
        OcenyZZajeciaUcznia = ocenyZZajeciaUcznia;
    }

    public int getindexZajecia() {
        return indexZajecia;
    }

    public ArrayList<Integer> getOcenyZZajeciaUcznia() {
        return OcenyZZajeciaUcznia;
    }
}