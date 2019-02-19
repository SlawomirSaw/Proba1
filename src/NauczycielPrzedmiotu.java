public class NauczycielPrzedmiotu extends Osoba {
    private String funkcja;
    public NauczycielPrzedmiotu(String imie, String nazwisko, String przedmiotNauczania)
    {
        super(imie,nazwisko);
        this.funkcja = przedmiotNauczania;
    }


    public String getFunkcja() {
        return funkcja;
    }

}
//////