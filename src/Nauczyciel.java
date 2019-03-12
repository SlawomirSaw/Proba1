public class Nauczyciel extends Osoba {
    private int index;
    private String nazwaKlasy;
    private String przedmiotNauczania;

    public Nauczyciel(int index, String imie, String nazwisko, String nazwaKlasy, String przedmiotNauczania) {
        super(imie, nazwisko);
        this.index = index;
        this.nazwaKlasy = nazwaKlasy;
        this.przedmiotNauczania = przedmiotNauczania;
    }

    public int getIndex() {
        return index;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public String getPrzedmiotNauczania() {
        return przedmiotNauczania;
    }

    public Nauczyciel imieDoZmiany(String imie) {
        return new Nauczyciel(getIndex(), imie, getNazwisko(), getNazwaKlasy(), getPrzedmiotNauczania());
    }

    public Nauczyciel nazwiskoDoZmiany(String nazwisko) {
        return new Nauczyciel(getIndex(), getImie(), nazwisko, getNazwaKlasy(), getPrzedmiotNauczania());
    }

    public Nauczyciel klasaDoZmianyOdszedl(String nazwaKlasy) {
        return new Nauczyciel(getIndex(), "", "", getNazwaKlasy(), getPrzedmiotNauczania());
    }

    public Nauczyciel klasaDoZmianyPrzybyl(String nazwaKlasy, int nauczycieleSzkolySize) {
        return new Nauczyciel(nauczycieleSzkolySize, getImie(), getNazwisko(), nazwaKlasy, getPrzedmiotNauczania());
    }

    public Nauczyciel przedmiotDoZmianyOdszedl(String nazwaPrzedmiotu) {
        return new Nauczyciel(getIndex(), "", "", getNazwaKlasy(), getPrzedmiotNauczania());
    }

    public Nauczyciel przedmiotDoZmianyPrzybyl(String nazwaPrzedmiotu, int nauczycieleSzkolySize) {
        return new Nauczyciel(nauczycieleSzkolySize, getImie(), getNazwisko(), getNazwaKlasy(), nazwaPrzedmiotu);
    }
}