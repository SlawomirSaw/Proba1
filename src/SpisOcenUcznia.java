import java.util.ArrayList;

public class SpisOcenUcznia extends Osoba {
    private ArrayList spisOcen;

    public SpisOcenUcznia(String imie, String nazwisko, ArrayList spisOcen) {
        super(imie, nazwisko);
        this.spisOcen = spisOcen;
    }
}
