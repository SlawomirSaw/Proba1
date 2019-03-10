import java.util.ArrayList;

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

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public void setNazwaKlasy(String nazwaKlasy) {
        this.nazwaKlasy = nazwaKlasy;
    }

    public String getPrzedmiotNauczania() {
        return przedmiotNauczania;
    }

    public void setPrzedmiotNauczania(String przedmiotNauczania) {
        this.przedmiotNauczania = przedmiotNauczania;
    }

    public Nauczyciel imieDoZmiany(String imie) {
        Nauczyciel daneZmienione = new Nauczyciel(getIndex(), imie, getNazwisko(), getNazwaKlasy(), getPrzedmiotNauczania());
        return daneZmienione;
    }

    public Nauczyciel nazwiskoDoZmiany(String nazwisko) {
        Nauczyciel daneZmienione = new Nauczyciel(getIndex(), getImie(), nazwisko, getNazwaKlasy(), getPrzedmiotNauczania());
        return daneZmienione;
    }

    public Nauczyciel klasaDoZmianyOdszedl(String nazwaKlasy) {
        Nauczyciel daneZmienioneKlasaOdszedl = new Nauczyciel(getIndex(), "", "", getNazwaKlasy(), getPrzedmiotNauczania());
        return daneZmienioneKlasaOdszedl;
    }

    public Nauczyciel klasaDoZmianyPrzybyl(String nazwaKlasy, int nauczycieleSzkolySize) {
        Nauczyciel daneZmienioneKlasaPrzybyl = new Nauczyciel(nauczycieleSzkolySize, getImie(), getNazwisko(), nazwaKlasy, getPrzedmiotNauczania());
        return daneZmienioneKlasaPrzybyl;
    }

    public Nauczyciel przedmiotDoZmianyOdszedl(String nazwaPrzedmiotu) {
        Nauczyciel daneZmienionePrzedmiotOdszedl = new Nauczyciel(getIndex(), "", "", getNazwaKlasy(), getPrzedmiotNauczania());
        return daneZmienionePrzedmiotOdszedl;
    }

    public Nauczyciel przedmiotDoZmianyPrzybyl(String nazwaPrzedmiotu, int nauczycieleSzkolySize) {
        Nauczyciel daneZmienionePrzedmiotPrzybyl = new Nauczyciel(nauczycieleSzkolySize, getImie(), getNazwisko(), getNazwaKlasy(), nazwaPrzedmiotu);
        return daneZmienionePrzedmiotPrzybyl;
    }
}