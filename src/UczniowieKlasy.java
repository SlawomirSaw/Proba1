import java.util.ArrayList;

public class UczniowieKlasy {
    private int indexKlasy;
    private ArrayList<Integer> indexyUczniowKlasy;

    public UczniowieKlasy(int indexKlasy, ArrayList<Integer> indexyUczniowKlasy) {
        this.indexKlasy = indexKlasy;
        this.indexyUczniowKlasy = indexyUczniowKlasy;
    }

    public UczniowieKlasy() {
    }

    public int getIndexKlasy() {
        return indexKlasy;
    }

    public ArrayList<Integer> getIndexyUczniowKlasy() {
        return indexyUczniowKlasy;
    }
}