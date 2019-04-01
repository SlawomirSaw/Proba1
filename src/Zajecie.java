import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Zajecie implements Serializable {
    private int idZajecie;
    private int idPrzedmiotNauczania;
    private int idNauczyciela;
    private int idKlasa;
    private LocalDateTime dataIGodzinaRozpoczecia;

    public Zajecie(int idZajecie, int idPrzedmiotNauczania, int idNauczyciela, int idKlasa, LocalDateTime dataIGodzinaRozpoczecia) {
        this.idZajecie = idZajecie;
        this.idPrzedmiotNauczania = idPrzedmiotNauczania;
        this.idNauczyciela = idNauczyciela;
        this.idKlasa = idKlasa;
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

    public int getIdKlasa() {
        return idKlasa;
    }

    public LocalDateTime getDataIGodzinaRozpoczecia() {
        return dataIGodzinaRozpoczecia;
    }
}