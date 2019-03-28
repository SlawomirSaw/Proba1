import java.time.LocalDateTime;
import java.time.LocalTime;

public class Zajecie {
    private int idZajecie;
    private int idPrzedmiotNauczania;
    private int idNauczyciela;
    private LocalDateTime dataIGodzinaRozpoczecia;

    public Zajecie(int idZajecie, int idPrzedmiotNauczania, int idNauczyciela, LocalDateTime dataIGodzinaRozpoczecia) {
        this.idZajecie = idZajecie;
        this.idPrzedmiotNauczania = idPrzedmiotNauczania;
        this.idNauczyciela = idNauczyciela;
        this.dataIGodzinaRozpoczecia = dataIGodzinaRozpoczecia;
    }

    public int getIdZajecie() {
        return idZajecie;
    }

    public int getIdPrzedmiotNauczania() {
        return idPrzedmiotNauczania;
    }

    public int getIdNauczyciela() {
        return idNauczyciela;
    }

    public LocalDateTime getDataIGodzinaRozpoczecia() {
        return dataIGodzinaRozpoczecia;
    }
}