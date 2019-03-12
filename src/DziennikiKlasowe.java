import java.util.ArrayList;

public class DziennikiKlasowe {
    private ArrayList<DziennikKlasowy> dziennikiKlasowe;

    public DziennikiKlasowe(ArrayList<DziennikKlasowy> dziennikiKlasowe) {
        this.dziennikiKlasowe = dziennikiKlasowe;
    }

    public ArrayList<DziennikKlasowy> getDziennikiKlasowe() {
        return dziennikiKlasowe;
    }

    public DziennikiKlasowe dziennikiKlasoweDoZmiany(ArrayList<Nauczyciel> nauczycieleSzkoly, ArrayList<DziennikKlasowy> dzienniki) {

        ArrayList<DziennikKlasowy> dziennikKlasowy = new ArrayList<>();
        for (int j = 0; j < dzienniki.size(); j++) {
            ArrayList<Nauczyciel> nauczycieleKlasyZmienione = new ArrayList<>();
            for (int i = 0; i < nauczycieleSzkoly.size(); i++) {
                if (getDziennikiKlasowe().get(j).getNazwaKlasy().equals(nauczycieleSzkoly.get(i).getNazwaKlasy())) {
                    nauczycieleKlasyZmienione.add(i, new Nauczyciel(i, nauczycieleSzkoly.get(i).getImie(), nauczycieleSzkoly.get(i).getNazwisko(), nauczycieleSzkoly.get(i).getNazwaKlasy(), nauczycieleSzkoly.get(i).getPrzedmiotNauczania()));
                }
                dziennikKlasowy.add(new DziennikKlasowy(dzienniki.get(j).getNazwaKlasy(), dzienniki.get(j).getUczniowie(), nauczycieleKlasyZmienione, dzienniki.get(j).getOcenyUczniow()));
            }
        }
        return new DziennikiKlasowe(dziennikKlasowy);
    }
}