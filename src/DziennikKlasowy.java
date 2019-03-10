import java.util.ArrayList;

public class DziennikKlasowy extends Klasa {
    private ArrayList<OcenyUcznia> ocenyUcznia;

    public DziennikKlasowy(int index, String nazwaKlasy, ArrayList<Uczen> uczniowie, ArrayList<Nauczyciel> nauczycieleKlasy, ArrayList<OcenyUcznia> ocenyUcznia) {
        super(index, nazwaKlasy, uczniowie, nauczycieleKlasy);
        this.ocenyUcznia = ocenyUcznia;
    }

    public ArrayList<OcenyUcznia> getOcenyUcznia() {
        return ocenyUcznia;
    }

    public void setOcenyUcznia(ArrayList<OcenyUcznia> ocenyUcznia) {
        this.ocenyUcznia = ocenyUcznia;
    }




}

