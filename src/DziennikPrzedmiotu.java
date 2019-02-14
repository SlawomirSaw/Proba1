import java.util.ArrayList;

public class DziennikPrzedmiotu extends Nauczyciel {
    private ArrayList<SpisOcenUcznia> spisOcenUczniow;

    public DziennikPrzedmiotu(String imie, String nazwisko, String przedmiotNauczania, ArrayList<SpisOcenUcznia> spisOcenUczniow) {
        super(imie, nazwisko, przedmiotNauczania);
        this.spisOcenUczniow = spisOcenUczniow;
    }
}
