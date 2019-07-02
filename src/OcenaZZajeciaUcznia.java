import java.io.Serializable;
import java.time.LocalDateTime;

public class OcenaZZajeciaUcznia implements Serializable {
    private int idZajecia;
    private int OcenaZZajeciaUcznia;
    private LocalDateTime dataIGodzinaOceny;

    public OcenaZZajeciaUcznia(int indexZajecia, int ocenaZZajeciaUcznia, LocalDateTime dataIGodzinaOceny) {
        this.idZajecia = indexZajecia;
        this.OcenaZZajeciaUcznia = ocenaZZajeciaUcznia;
        this.dataIGodzinaOceny = dataIGodzinaOceny;
    }

    public int getidZajecia() {
        return idZajecia;
    }

    public int getOcenaZZajeciaUcznia() {
        return OcenaZZajeciaUcznia;
    }

    public LocalDateTime getdataIGodzinaOceny() {
        return dataIGodzinaOceny;
    }
}