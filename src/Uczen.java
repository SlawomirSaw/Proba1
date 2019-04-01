import java.io.Serializable;

public class Uczen extends Osoba implements Serializable {
    int idUcznia;

    public Uczen(int idUcznia, String imie, String nazwisko) {
        super(imie, nazwisko);
        this.idUcznia = idUcznia;
    }

    public int getidUcznia() {
        return idUcznia;
    }

    public Uczen() {
    }
}