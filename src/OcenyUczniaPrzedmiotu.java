import java.util.ArrayList;

public class OcenyUczniaPrzedmiotu {
    private String imieUcznia;
    private String nazwiskoUcznia;
    private ArrayList spisOcen;

    public OcenyUczniaPrzedmiotu(String imieUcznia, String nazwiskoUcznia, ArrayList spisOcenPrzedmiotu) {
        this.imieUcznia = imieUcznia;
        this.nazwiskoUcznia = nazwiskoUcznia;
        this.spisOcen = spisOcenPrzedmiotu;
    }

    public String getImieUcznia() {
        return imieUcznia;
    }

    public void setImieUcznia(String imieUcznia) {
        this.imieUcznia = imieUcznia;
    }

    public String getNazwiskoUcznia() {
        return nazwiskoUcznia;
    }

    public void setNazwiskoUcznia(String nazwiskoUcznia) {
        this.nazwiskoUcznia = nazwiskoUcznia;
    }

    public ArrayList getSpisOcen() {
        return spisOcen;
    }

    public void setSpisOcen(ArrayList spisOcen) {
        this.spisOcen = spisOcen;
    }
}
