import java.io.Serializable;

public class PrzedmiotNauczania implements Serializable {
    private int idPrzedmiotNauczania;
    private String przedmiotNauczania;

    public PrzedmiotNauczania(int idPrzedmiotNauczania, String przedmiotNauczania) {
        this.idPrzedmiotNauczania = idPrzedmiotNauczania;
        this.przedmiotNauczania = przedmiotNauczania;
    }

    public int getIdPrzedmiotNauczania() {
        return idPrzedmiotNauczania;
    }

    public String getPrzedmiotNauczania() {
        return przedmiotNauczania;
    }

}
