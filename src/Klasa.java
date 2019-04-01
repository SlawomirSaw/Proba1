import java.io.Serializable;

public class Klasa implements Serializable {
    private int idKlasy;
    private String nazwaKlasy;

    public Klasa(int idKlasy, String nazwaKlasy) {
        this.idKlasy = idKlasy;
        this.nazwaKlasy = nazwaKlasy;
    }

    public Klasa() {
    }

    public int getIdKlasy() {
        return idKlasy;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }
}