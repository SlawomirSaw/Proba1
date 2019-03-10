import java.util.ArrayList;

public class OcenyUcznia {
    int index;
    private ArrayList ocenyPrzedmiotuUcznia;

    public OcenyUcznia(int index, ArrayList ocenyPrzedmiotuUcznia) {
        this.index = index;
        this.ocenyPrzedmiotuUcznia = ocenyPrzedmiotuUcznia;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList getOcenyPrzedmiotu() {
        return ocenyPrzedmiotuUcznia;
    }

    public void setOcenyPrzedmiotu(ArrayList ocenyPrzedmiotu) {
        this.ocenyPrzedmiotuUcznia = ocenyPrzedmiotu;
    }
}
