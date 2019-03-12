import java.util.Scanner;
import java.util.ArrayList;

public class Szkola implements DaneSzkoly {
    public static int iloscKlasSzkole = 0;
    public static int iloscUczniowKlasie = 0;
    public static int indexNauczycieli = 0;
    public static int indexKlas = 0;
    public static DyrektorSzkoly dyrektorSzkoly;
    public static int iloscPrzedmiotowNauczaniaKlasy = 0;
    public static final int WPROWADZANIE_DANYCH = 1;
    public static final int ZESTAWIENIE_SZKOLA = 2;
    public static final int KONIEC = 3;
    public static final int ZMIANA_DANYCH_NAUCZYCIELI = 4;
    public static ArrayList<DziennikKlasowy> dziennikiKlasoweLista;
    public static DziennikiKlasowe dziennikiKlasowe;
    public static OcenyPrzedmiotuUcznia oceny;
    public static ArrayList<DzienniczekUcznia> dzienniczkiUczniowKlasy;
    public static ArrayList <ArrayList<DzienniczekUcznia>> dzienniczkiUczniowSzkoly;
    public static ArrayList<Uczen> uczniowieKlasy;
    public static ArrayList<Nauczyciel> nauczycieleKlasy;
    public static ArrayList<Nauczyciel> nauczycieleSzkoly;
    public static ArrayList<ArrayList<OcenyPrzedmiotuUcznia>> ocenyUczniow;
    public static String imieNauczyciela;
    public static String nazwiskoNauczyciela;
    public static String przedmiotNauczania;
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
        System.out.println("WYBIERZ:    WPROWADZANIE_DANYCH UCZNIÓW I NAUCZYCIELI KLAS = 1  " + "ZESTAWIENIE_INFORMACJI_O_KLASACH = 2" + "   " + "KONIEC = 3" + "    " + "ZMIANA DANYCH NAUCZYCIELI = 4");
    }

    private static void Wprowadzanie_Danych(Scanner sc) {
        String imieDyrektora;
        String nazwiskoDyrekotora;
        System.out.println("------------------------- Wprowadzanie danych szkoły -------------------------");
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        System.out.print("Dyrektor Szkoły: ");
        System.out.print("Imie: ");
        imieDyrektora = sc.nextLine();
        System.out.print("Nazwisko: ");
        nazwiskoDyrekotora = sc.nextLine();
        dyrektorSzkoly = new DyrektorSzkoly(imieDyrektora, nazwiskoDyrekotora);
        System.out.print("Ilość klas w szkole: ");
        iloscKlasSzkole = sc.nextInt();
        System.out.println();
        sc.nextLine();
        indexNauczycieli = 0;
        indexKlas = 0;
        dziennikiKlasoweLista = new ArrayList<>();
        nauczycieleSzkoly = new ArrayList<>();
        dzienniczkiUczniowSzkoly = new ArrayList();
        for (int i = 0; i < iloscKlasSzkole; i++) {
            uczniowieKlasy = new ArrayList<>();
            nauczycieleKlasy = new ArrayList<>();
            dzienniczkiUczniowKlasy = new ArrayList<>();
            ocenyUczniow = new ArrayList<>();
            System.out.print("  Podaj Numer lub Nazwę klasy ---------------------------- Klasa Nr : ");
            nazwaKlasy = sc.nextLine();
            System.out.print("Ilość przedmiotów nauczania w klasie Nr " + nazwaKlasy + " : ");
            iloscPrzedmiotowNauczaniaKlasy = sc.nextInt();
            System.out.println("Podaj Imiona, Nazwiska i Funkcje nauczycieli klasy Nr " + nazwaKlasy + " : ");
            sc.nextLine();
            for (int j = 0; j < iloscPrzedmiotowNauczaniaKlasy; j++) {
                System.out.print(j + 1 + ") imię: ");
                imieNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") nazwisko: ");
                nazwiskoNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") przedmiot nauczania: ");
                przedmiotNauczania = sc.nextLine();
                System.out.println();
                Nauczyciel nauczyciel = new Nauczyciel(indexNauczycieli, imieNauczyciela, nazwiskoNauczyciela, nazwaKlasy, przedmiotNauczania);
                nauczycieleKlasy.add(nauczyciel);
                nauczycieleSzkoly.add(nauczyciel);
                indexNauczycieli++;
            }
            System.out.print("Ilość uczniów w klasie Nr " + nazwaKlasy + " : ");
            iloscUczniowKlasie = sc.nextInt();
            System.out.println("Podaj Imiona, Nazwiska i oceny uczniów klasy Nr " + nazwaKlasy + " : ");
            sc.nextLine();
            for (int j = 0; j < iloscUczniowKlasie; j++) {
                System.out.print(j + 1 + ") imię: ");
                String imieUcznia = sc.nextLine();
                System.out.print(j + 1 + ") nazwisko: ");
                String nazwiskoUcznia = sc.nextLine();
                System.out.println("        oceny z poszczególnych przedmiotów:");
                ArrayList<OcenyPrzedmiotuUcznia> ocenyUcznia = new ArrayList<>();
                for (int k = 0; k < iloscPrzedmiotowNauczaniaKlasy; k++) {
                    System.out.print("      " + nauczycieleKlasy.get(k).getPrzedmiotNauczania() + "     ocena: ");
                    int ocena = sc.nextInt();
                    oceny = new OcenyPrzedmiotuUcznia(new ArrayList<>(ocena));
                    ocenyUcznia.add(oceny);
                }
                System.out.println();
                sc.nextLine();
                Uczen uczen = new Uczen(imieUcznia, nazwiskoUcznia);
                uczniowieKlasy.add(j, uczen);
                dzienniczkiUczniowKlasy.add(j, new DzienniczekUcznia(uczen, nauczycieleKlasy, ocenyUcznia));
                ocenyUczniow.add(ocenyUcznia);
            }
            dzienniczkiUczniowSzkoly.add(dzienniczkiUczniowKlasy);
            dziennikiKlasoweLista.add(new DziennikKlasowy(nazwaKlasy, uczniowieKlasy, nauczycieleKlasy, ocenyUczniow));
            indexKlas++;
        }
        dziennikiKlasowe = new DziennikiKlasowe(dziennikiKlasoweLista);
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
                    System.out.print(dziennikiKlasoweLista.get(i).getOcenyUczniow().get(j).get(k).getOcenyPrzedmiotu().get(0) + "    ");
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
        String wprowadzenie;
        String zmienioneImie;
        String zmienioneNazwisko;
        String zmienionaKlasa;
        String zmienionyPrzedmiot;
        if (nauczycieleKlasy.size() < 0) {
            System.out.println("Nie ma wprowadzonych żadnych danych o nauczycielach");
        } else {
            System.out.println("------------------------- ZMIANA DANYCH NAUCZYCIELI -------------------------");
            System.out.println("ENTER - zatwierdzaj po wprowadzeniu zmian lub pozostawieniu bez zmian");
            int m = 0;
            for (int j = 0; j < dziennikiKlasoweLista.size(); j++) {
                for (int k = 0; k < dziennikiKlasoweLista.get(j).getNauczycieleKlasy().size(); k++) {
                    System.out.println();
                    System.out.print("Imie: " + dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getImie() + "    ?");
                    zmienioneImie = sc.nextLine();
                    if (zmienioneImie.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.imieDoZmiany(zmienioneImie);
                        nauczycieleSzkoly.remove(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikiKlasoweLista);
                        dziennikiKlasoweLista = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }
                    System.out.print("Nazwisko: " + dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getNazwisko() + "    ?");
                    zmienioneNazwisko = sc.nextLine();
                    if (zmienioneNazwisko.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.nazwiskoDoZmiany(zmienioneNazwisko);
                        nauczycieleSzkoly.remove(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikiKlasoweLista);
                        dziennikiKlasoweLista = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }
                    System.out.print("Klasa: " + dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getNazwaKlasy() + "    ?");
                    zmienionaKlasa = sc.nextLine();
                    if (zmienionaKlasa.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.klasaDoZmianyOdszedl(zmienionaKlasa);
                        nauczycieleSzkoly.remove(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        zmienionyNauczyciel = nauczycielDoZmiany.klasaDoZmianyPrzybyl(zmienionaKlasa, nauczycieleSzkoly.size());
                        nauczycieleSzkoly.add(zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikiKlasoweLista);
                        dziennikiKlasoweLista = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }
                    System.out.print("Przedmiot: " + dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getPrzedmiotNauczania() + "    ?");
                    zmienionyPrzedmiot = sc.nextLine();
                    if (zmienionyPrzedmiot.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.przedmiotDoZmianyOdszedl(zmienionaKlasa);
                        nauczycieleSzkoly.remove(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikiKlasoweLista.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        zmienionyNauczyciel = nauczycielDoZmiany.przedmiotDoZmianyPrzybyl(zmienionaKlasa, nauczycieleSzkoly.size());
                        nauczycieleSzkoly.add(zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikiKlasoweLista);
                        dziennikiKlasoweLista = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }
                }
            }
            System.out.println("ZMIANY WYKONANE");
        }
    }
}