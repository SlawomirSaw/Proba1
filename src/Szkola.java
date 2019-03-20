import java.util.Scanner;
import java.util.ArrayList;

public class Szkola implements DaneSzkoly {
    public static DyrektorSzkoly dyrektorSzkoly = new DyrektorSzkoly("", "");
    public static final int WPROWADZANIE_DANYCH = 1;
    public static final int ZESTAWIENIE_SZKOLA = 2;
    public static final int KONIEC = 3;
    public static final int ZMIANA_DANYCH_NAUCZYCIELI = 4;
    public static ArrayList<DziennikKlasowy> dziennikiKlasoweLista;
    public static ArrayList<Uczen> uczniowieSzkoly = new ArrayList<>();
    public static ArrayList<Klasa> klasy = new ArrayList<>();
    public static ArrayList<UczniowieKlasy> uczniowieWgKlas = new ArrayList<>();
    public static String nazwaKlasy;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            OpcjeWyboru();
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case WPROWADZANIE_DANYCH:
                    Wprowadzanie_Danych(sc);
                    break;
                case ZESTAWIENIE_SZKOLA:
                    Zestawienie_Szkola();
                    break;
                case KONIEC:
                    System.out.println("-------------Koniec--------------");
                    break;
                case ZMIANA_DANYCH_NAUCZYCIELI:
                    zmianaDanychNauczycieli(sc);
                    break;
            }
        } while (option != KONIEC);
        sc.close();
    }

    private static void OpcjeWyboru() {
        System.out.println("OpcjeWyboru");
        System.out.println("WYBIERZ:    1 - WPROWADZANIE_DANYCH UCZNIÓW I NAUCZYCIELI KLAS  " + "2 - ZESTAWIENIE_INFORMACJI_O_KLASACH" + "   " + "3 - KONIEC" + "    " + "4 - ZMIANA DANYCH NAUCZYCIELI");
    }

    private static void Wprowadzanie_Danych(Scanner sc) {
        int key = 0;
        System.out.println("------------------------- WPROWADZANIE_DANYCH UCZNIÓW I NAUCZYCIELI KLAS -------------------------");
        do {
            System.out.println("WYBIERZ: 1-Dyrektor      2-Uczniowie     3-Klasy     4-Przypisz Uczniów do Klas    5-Powrót do głównego MENU");
            key = sc.nextInt();
            sc.nextLine();
            switch (key) {

                case 1:
                    System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
                    if (!dyrektorSzkoly.getNazwisko().equals("")) {
                        System.out.print("Obecny Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
                    }
                    if (czyDodac("nowego Dytektora Szkoły?").equals("n")) {
                        break;
                    }
                    DyrektorSzkoly nowyDyrektorSzkoly = new DyrektorSzkoly();
                    dyrektorSzkoly = new DyrektorSzkoly(nowyDyrektorSzkoly.dodajImie(), nowyDyrektorSzkoly.dodajNazwisko());
                    System.out.println("Nowym Dyrektorem Szkoły jest: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
                    break;
                case 2:
                    wyswietlListaOsob(uczniowieSzkoly);
                    if (czyDodac("nowego ucznia?").equals("n")) {
                        break;
                    }
                    System.out.println("Podaj Imiona i Nazwiska uczniów");
                    do {
                        Uczen uczen = new Uczen();
                        uczniowieSzkoly.add(new Uczen(uczniowieSzkoly.size() + 1, uczen.dodajImie(), uczen.dodajNazwisko()));
                        System.out.println("Dodano: " + uczniowieSzkoly.size() + ") " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getImie() + " " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getNazwisko());
                    } while (!czyDodac("następnego ucznia?").equals("n"));
                    break;
                case 3:
                    wyswietlListaKlas(klasy);
                    if (czyDodac("nową klasę?").equals("n")) {
                        break;
                    }
                    System.out.println("Podaj numer Klasy");
                    do {
                        System.out.print("Klasa Nr: ");
                        nazwaKlasy = sc.nextLine();
                        klasy.add(new Klasa(nazwaKlasy));
                        uczniowieWgKlas.add(new UczniowieKlasy(0, new ArrayList<Integer>()));
                        System.out.println("Dodano: " + klasy.size() + ") Klasa Nr: " + klasy.get(klasy.size() - 1).getNazwaKlasy());
                    } while (!czyDodac("nową klasę?").equals("n"));
                    break;
                case 4:
                    ArrayList<Uczen> listaWolnychUczniow = listaWolnychUczniow(uczniowieWgKlas);
                    if (listaWolnychUczniow.size() == 0) {
                        break;
                    }
                    if (czyDodac("nowych Uczni do Klas?").equals("n")) {
                        break;
                    }
                    for (Uczen wolny : listaWolnychUczniow) {
                        System.out.println("Dodaj ucznia:");
                        System.out.println(wolny.getIndex() + ") " + wolny.getImie() + " " + wolny.getNazwisko());
                        System.out.print("do klasy o indeksie: ");
                        int indexKlasy = sc.nextInt();
                        ArrayList<Integer> noweIndexyUczniowKlasy = uczniowieWgKlas.get(indexKlasy - 1).getIndexyUczniowKlasy();
                        noweIndexyUczniowKlasy.add(wolny.getIndex());
                        uczniowieWgKlas.remove(indexKlasy - 1);
                        uczniowieWgKlas.add(indexKlasy - 1, new UczniowieKlasy(indexKlasy, noweIndexyUczniowKlasy));
                    }
                    listaWolnychUczniow(uczniowieWgKlas);
            }
        } while (key != 5);
    }

    private static void Zestawienie_Szkola() {
        System.out.println("------------------------- ZESTAWIENIE_INFORMACJI_O_SZKLOLE -------------------------");
        System.out.println();
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        System.out.println("Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());

        for (int i = 0; i < dziennikiKlasoweLista.size(); i++) {
            System.out.println();
            System.out.println("----------- Dane klasy Nr " + dziennikiKlasoweLista.get(i).getNazwaKlasy() + " ----------- ");
            System.out.println("Ilość uczniów w klasie: " + dziennikiKlasoweLista.get(i).getUczniowie().size() + " (imie,nazwisko/przedmiot/oceny)");
            for (int j = 0; j < dziennikiKlasoweLista.get(i).getUczniowie().size(); j++) {
                System.out.print(j + 1 + ") " + dziennikiKlasoweLista.get(i).getUczniowie().get(j).getImie() + " " + dziennikiKlasoweLista.get(i).getUczniowie().get(j).getNazwisko() + "    ");
                for (int k = 0; k < dziennikiKlasoweLista.get(i).getNauczycieleKlasy().size(); k++) {
                    System.out.print(dziennikiKlasoweLista.get(i).getNauczycieleKlasy().get(k).getPrzedmiotNauczania() + "  ");
                    System.out.print(dziennikiKlasoweLista.get(i).getOcenyUczniow().get(j).get(k).getOcenyZZajeciaUcznia().get(0) + "    ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("Ilość nauczycieli w klasie: " + dziennikiKlasoweLista.get(i).getNauczycieleKlasy().size());
            for (int j = 0; j < dziennikiKlasoweLista.get(i).getNauczycieleKlasy().size(); j++) {
                System.out.println(j + 1 + ") " + dziennikiKlasoweLista.get(i).getNauczycieleKlasy().get(j).getImie() + " " + dziennikiKlasoweLista.get(i).getNauczycieleKlasy().get(j).getNazwisko());
                System.out.println(dziennikiKlasoweLista.get(i).getNauczycieleKlasy().get(j).getPrzedmiotNauczania());
            }
        }
        Obliczenia obliczenia = new Obliczenia(dziennikiKlasoweLista);
        System.out.println();
        System.out.println("Ilość klas w szkole:                     " + obliczenia.getIloscKlasSzkole());
        System.out.println("Ilość wszystkich uczniów w szkole:       " + obliczenia.getIloscUczniowSzkole());
        System.out.println("Ilość wszystkich nauczycieli w szkole:   " + obliczenia.getIloscNauczycieliSzkole());
    }

    private static void zmianaDanychNauczycieli(Scanner sc) {
        System.out.println("------------------------- ZMIANA DANYCH NAUCZYCIELI -------------------------");
        System.out.println("ENTER - zatwierdzaj po wprowadzeniu / nie wprowadzeniu zmian");
        System.out.println("ZMIANY WYKONANE");
    }

    private static void wyswietlListaOsob(ArrayList<Uczen> listaOsob) {
        if (listaOsob.size() >= 1) {
            for (int ii = 0; ii < listaOsob.size(); ii++) {
                System.out.println(ii + 1 + ") " + listaOsob.get(ii).getImie() + " " + listaOsob.get(ii).getNazwisko());
            }
        }
    }

    private static void wyswietlListaKlas(ArrayList<Klasa> listaKlas) {
        if (listaKlas.size() >= 1) {
            for (int ii = 0; ii < listaKlas.size(); ii++) {
                System.out.println(ii + 1 + ") Klasa Nr: " + listaKlas.get(ii).getNazwaKlasy());
            }
        }
    }

    private static ArrayList<Uczen> listaWolnychUczniow(ArrayList<UczniowieKlasy> listaUczniWgKlas) {
        ArrayList<Uczen> uczniowieWolni = new ArrayList<>();
        if (uczniowieSzkoly.size() > 0 && klasy.size() > 0) {
            int liczbaUczniZapisanychDoKlas = 0;
            for (UczniowieKlasy luwk : listaUczniWgKlas) {
                if (luwk.getIndexyUczniowKlasy().size() >= 1) {
                    int indexKlasy = luwk.getIndexKlasy();
                    System.out.println("Klasa Nr: " + klasy.get(indexKlasy - 1).getNazwaKlasy());
                    for (int iuk : luwk.getIndexyUczniowKlasy()) {
                        liczbaUczniZapisanychDoKlas++;
                        System.out.println("        " + iuk + ") " + uczniowieSzkoly.get(iuk - 1).getImie() + " " + uczniowieSzkoly.get(iuk - 1).getNazwisko());
                    }
                }
            }
            if ((liczbaUczniZapisanychDoKlas == uczniowieSzkoly.size())) {
                System.out.println("Wszyscy uczniowie już są zapisani do Klas!");
            }
            if (liczbaUczniZapisanychDoKlas > 0 && liczbaUczniZapisanychDoKlas < uczniowieSzkoly.size()) {
                System.out.println("Uczniowie, którzy jeszcze nie są przypisani do klas:");
                for (Uczen us : uczniowieSzkoly) {
                    int zawiera = 0;
                    for (UczniowieKlasy luwk : listaUczniWgKlas) {
                        if (luwk.getIndexyUczniowKlasy().contains(uczniowieSzkoly.get(uczniowieSzkoly.indexOf(us)).getIndex())) {
                            zawiera = 1;
                        }
                    }
                    if (zawiera == 0) {
                        uczniowieWolni.add(us);
                        System.out.println(us.getIndex() + ") " + us.getImie() + " " + us.getNazwisko());
                    }
                }
            }
            if (liczbaUczniZapisanychDoKlas == 0) {
                System.out.println("Rozpocznij przypisywanie uczniów do klas");
                System.out.println("Lista wszytkich uczni w szkole:");
                wyswietlListaOsob(uczniowieSzkoly);
                System.out.println();
                System.out.println("Lista wszystkich klas w szkole:");
                wyswietlListaKlas(klasy);
                uczniowieWolni = uczniowieSzkoly;
            }
        } else {
            System.out.println("Lista uczni Szkoły lub lista Klas jest pusta!");
        }
        return uczniowieWolni;
    }

    public static String czyDodac(String element) {
        String litera = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("        Czy dodać " + element + "    ENTER-Tak    n-Nie");
            litera = sc.nextLine();
            if (!litera.equals("n") && !litera.equals("")) {
                System.out.print("BŁAD ----> ");
            }
        } while (!litera.equals("n") && !litera.equals(""));
        return litera;
    }
}