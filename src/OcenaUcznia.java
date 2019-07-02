import java.io.Serializable;
import java.time.LocalDateTime;

public class OcenaUcznia implements Serializable {
    private int idUcznia;
    private int idZajecia;
    private int ocenaZZajeciaUcznia;
    private LocalDateTime dataIGodzinaOceny;

    public int getIdUcznia() {
        return idUcznia;
    }

    public int getIdZajecia() {
        return idZajecia;
    }

    public int getOcenaZZajeciaUcznia() {
        return ocenaZZajeciaUcznia;
    }

    public LocalDateTime getDataIGodzinaOceny() {
        return dataIGodzinaOceny;
    }

    public OcenaUcznia(int idUcznia, int idZajecia, int ocenaZZajeciaUcznia, LocalDateTime dataIGodzinaOceny) {
        this.idUcznia = idUcznia;
        this.idZajecia = idZajecia;
        this.ocenaZZajeciaUcznia = ocenaZZajeciaUcznia;
        this.dataIGodzinaOceny = dataIGodzinaOceny;
    }

    public OcenaUcznia(int idUcznia){
        this.idUcznia = idUcznia;
    }
}