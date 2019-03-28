import java.util.ArrayList;

public class UczniowieKlasy {
    private int idKlasy;
    private ArrayList<Integer> idUczniowKlasy;

    public UczniowieKlasy(int idKlasy, ArrayList<Integer> idUczniowKlasy) {
        this.idKlasy = idKlasy;
        this.idUczniowKlasy = idUczniowKlasy;
    }

    public UczniowieKlasy() {
    }

    public int getIdKlasy() {
        return idKlasy;
    }

    public ArrayList<Integer> getIdUczniowKlasy() {
        return idUczniowKlasy;
    }
}