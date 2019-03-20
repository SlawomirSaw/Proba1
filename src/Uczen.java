public class Uczen extends Osoba {
    int index;

    public Uczen(int index, String imie, String nazwisko) {
        super(imie, nazwisko);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Uczen() {
    }
}