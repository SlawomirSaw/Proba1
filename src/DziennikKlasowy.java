import java.util.ArrayList;

public class DziennikKlasowy {
    private String nazwaKlasy;
    private ArrayList<DzienniczekUcznia> ocenyUczniow;

    public DziennikKlasowy(String nazwaKlasy, ArrayList<DzienniczekUcznia> ocenyUczniow) {
        this.nazwaKlasy = nazwaKlasy;
        this.ocenyUczniow = ocenyUczniow;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public void setNazwaKlasy(String nazwaKlasy) {
        this.nazwaKlasy = nazwaKlasy;
    }

    public ArrayList<DzienniczekUcznia> getOcenyUczniow() {
        return ocenyUczniow;
    }

    public void setOcenyUczniow(ArrayList<DzienniczekUcznia> ocenyUczniow) {
        this.ocenyUczniow = ocenyUczniow;
    }
}

