public class Nauczyciel extends Osoba {
    private String funkcja;
    public Nauczyciel (String imie, String nazwisko, String przedmiotNauczania)
    {
        super(imie,nazwisko);
        this.funkcja = przedmiotNauczania;
    }


    public String getFunkcja() {
        return funkcja;
    }

}
//////