import java.util.ArrayList;

public class Obliczenia {
    private int iloscKlasSzkole = 0;
    private int iloscUczniowSzkole = 0;
    private int iloscNauczycieliSzkole = 0;
    private double sredniaOcenKlasy = 0;
    private double sredniaOcenSzkoly = 0;
    private ArrayList<DziennikKlasowy> wszystkieDzienniki;

    public Obliczenia(ArrayList<DziennikKlasowy> wszystkieDzienniki) {
        this.wszystkieDzienniki = wszystkieDzienniki;
    }

    public void setWszystkieDzienniki(ArrayList<DziennikKlasowy> wszystkieDzienniki) {
        this.wszystkieDzienniki = wszystkieDzienniki;
    }

    public int getIloscKlasSzkole() {
        iloscKlasSzkole = wszystkieDzienniki.size();
        return iloscKlasSzkole;
    }

    public int getIloscUczniowSzkole() {
        for (int i = 0; i < wszystkieDzienniki.size(); i++) {
            iloscUczniowSzkole += wszystkieDzienniki.get(i).getUczniowie().size();
        }
        return iloscUczniowSzkole;
    }

    public int getIloscNauczycieliSzkole() {
        for (int i = 0; i < wszystkieDzienniki.size(); i++) {
            iloscNauczycieliSzkole += wszystkieDzienniki.get(i).getNauczyciele().size();
        }
        return iloscNauczycieliSzkole;
    }

    public double getSredniaOcenKlasy() {
        return sredniaOcenKlasy;
    }

    public double getSredniaOcenSzkoly() {
        return sredniaOcenSzkoly;
    }
}
