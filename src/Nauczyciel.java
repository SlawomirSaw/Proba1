public class Nauczyciel extends Osoba {
    private int idNauczyciela;

    public Nauczyciel(int idNauczyciela, String imie, String nazwisko) {
        super(imie, nazwisko);
        this.idNauczyciela = idNauczyciela;
    }

    public Nauczyciel(int idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public int getIdNauczyciela() {
        return idNauczyciela;
    }

    public Nauczyciel() {
    }
}