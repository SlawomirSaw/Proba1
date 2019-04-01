import java.io.Serializable;

public class DyrektorSzkoly extends Osoba implements Serializable {
    public DyrektorSzkoly(String imie, String nazwisko) {
        super(imie, nazwisko);
    }

    public DyrektorSzkoly() {
    }
}