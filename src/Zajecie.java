public class Zajecie {
    private String nazwaZajecia;
    private String imieNauczyciela;
    private String nazwiskoNauczyciela;

    public Zajecie(String nazwaZajecia, String imieNauczyciela, String nazwiskoNauczyciela) {
        this.nazwaZajecia = nazwaZajecia;
        this.imieNauczyciela = imieNauczyciela;
        this.nazwiskoNauczyciela = nazwiskoNauczyciela;
    }

    public String getNazwaZajecia() {
        return nazwaZajecia;
    }

    public String getImieNauczyciela() {
        return imieNauczyciela;
    }

    public String getNazwiskoNauczyciela() {
        return nazwiskoNauczyciela;
    }
}