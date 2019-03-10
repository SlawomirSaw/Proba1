import java.util.Scanner;
import java.util.ArrayList;

public class Szkola implements DaneSzkoly {
    public static int iloscKlasSzkole = 0;
    public static int iloscUczniowKlasie = 0;
    public static int indexNauczycieli = 0;
    public static int indexKlas = 0;
    //public static String nazwaSzkoly;
    public static DyrektorSzkoly dyrektorSzkoly;
    public static int iloscPrzedmiotowNauczaniaKlasy = 0;
    public static int iloscUczniowSzkole = 0;
    public static int iloscNauczycieliSzkole = 0;
    public static final int WPROWADZANIE_DANYCH = 1;
    public static final int ZESTAWIENIE_SZKOLA = 2;
    public static final int KONIEC = 3;
    public static final int ZMIANA_DANYCH_NAUCZYCIELI = 4;
    //public static ArrayList<Klasa> wszystkieKlasy;
    public static ArrayList<DziennikKlasowy> dziennikKlasowy;
    public static DziennikiKlasowe dziennikiKlasowe;
    public static ArrayList<DzienniczekUcznia> dzienniczekUcznia;
    public static ArrayList dziennczkiUczniow;
    public static ArrayList<DziennikPrzedmiotu> dziennikiPrzedmiotów;
    public static ArrayList<Uczen> uczniowieKlasy;
    public static ArrayList<Nauczyciel> nauczycieleKlasy;
    public static ArrayList<Nauczyciel> nauczycieleSzkoly;
    public static ArrayList<OcenyUcznia> ocenyUczniow;
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
        //nazwaSzkoly = sc.nextLine();
        System.out.print("Dyrektor Szkoły: ");
        System.out.print("Imie: ");
        imieDyrektora = sc.nextLine();
        System.out.print("Nazwisko: ");
        nazwiskoDyrekotora = sc.nextLine();
        dyrektorSzkoly = new DyrektorSzkoly(imieDyrektora, nazwiskoDyrekotora);
        System.out.print("Ilość klas w szkole: ");
        iloscKlasSzkole = sc.nextInt();
        System.out.println(" ");
        sc.nextLine();

        indexNauczycieli = 0;
        indexKlas = 0;
        //wszystkieKlasy = new ArrayList<>();
        dziennikKlasowy = new ArrayList<>();
        nauczycieleSzkoly = new ArrayList<>();
        dziennczkiUczniow = new ArrayList();
        for (int i = 0; i < iloscKlasSzkole; i++) {
            uczniowieKlasy = new ArrayList<>();
            nauczycieleKlasy = new ArrayList<>();
            dzienniczekUcznia = new ArrayList<>();
            ocenyUczniow = new ArrayList<>();


            System.out.print("  Podaj Numer lub Nazwę klasy ---------------------------- Klasa Nr : ");
            String nazwaKlasy = sc.nextLine();


            System.out.print("Ilość przedmiotów nauczania w klasie Nr " + nazwaKlasy + " : ");
            iloscPrzedmiotowNauczaniaKlasy = sc.nextInt();
            System.out.println("Podaj Imiona, Nazwiska i Funkcje nauczycieli klasy Nr " + nazwaKlasy + " : ");
            sc.nextLine();
            for (int j = 0; j < iloscPrzedmiotowNauczaniaKlasy; j++) {
                System.out.print(j + 1 + ") imię: ");
                String imieNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") nazwisko: ");
                String nazwiskoNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") przedmiot nauczania: ");
                String przedmiotNauczania = sc.nextLine();
                System.out.println(" ");
                nauczycieleKlasy.add(j, new Nauczyciel(indexNauczycieli, imieNauczyciela, nazwiskoNauczyciela, nazwaKlasy, przedmiotNauczania));
                nauczycieleSzkoly.add(i, new Nauczyciel(indexNauczycieli, imieNauczyciela, nazwiskoNauczyciela, nazwaKlasy, przedmiotNauczania));
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
                ArrayList ocenyUcznia = new ArrayList<>();
                for (int k = 0; k < iloscPrzedmiotowNauczaniaKlasy; k++) {
                    System.out.print("      " + nauczycieleKlasy.get(k).getPrzedmiotNauczania() + "     ocena: ");
                    //ArrayList oceny = new ArrayList();
                    int ocena = sc.nextInt();
                    ocenyUcznia.add(ocena);
                }

                System.out.println(" ");
                sc.nextLine();
                Uczen uczen = new Uczen(imieUcznia, nazwiskoUcznia);
                uczniowieKlasy.add(j, uczen);
                dzienniczekUcznia.add(j, new DzienniczekUcznia(uczen, nauczycieleKlasy, ocenyUcznia));
                ocenyUczniow.add(j, new OcenyUcznia(j, ocenyUcznia));

            }
            dziennczkiUczniow.add(i, dzienniczekUcznia);
            //wszystkieKlasy.add(i, new Klasa(indexKlas, nazwaKlasy, uczniowieKlasy, nauczycieleKlasy));
            //dziennikiKlasowe.add(i, new DziennikKlasowy(indexKlas, nazwaKlasy, uczniowieKlasy, nauczycieleKlasy, ocenyUczniow));
            dziennikKlasowy.add(i, new DziennikKlasowy(indexKlas, nazwaKlasy, uczniowieKlasy, nauczycieleKlasy, ocenyUczniow));
            indexKlas++;
        }
        //Szkola szkola = new Szkola(nazwaSzkoly, wszystkieKlasy, dyrektorSzkoly);
        dziennikiKlasowe = new DziennikiKlasowe(dziennikKlasowy);
    }


    private static void Zestawienie_Szkola() {

        //iloscUczniowSzkole = 0;
        //iloscNauczycieliSzkole = 0;
        System.out.println("------------------------- ZESTAWIENIE_INFORMACJI_O_SZKLOLE -------------------------");
        System.out.println(" ");
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        System.out.println("Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());

        for (int i = 0; i < dziennikKlasowy.size(); i++) {
            System.out.println(" ");
            System.out.println("----------- Dane klasy Nr " + dziennikKlasowy.get(i).getNazwaKlasy() + " ----------- ");
            System.out.println("Ilość uczniów w klasie: " + dziennikKlasowy.get(i).getUczniowie().size() + " (imie,nazwisko/przedmiot/oceny)");
            //iloscUczniowSzkole += wszystkieKlasy.get(i).getUczniowie().size();

            for (int j = 0; j < dziennikKlasowy.get(i).getUczniowie().size(); j++) {
                System.out.print(j + 1 + ") " + dziennikKlasowy.get(i).getUczniowie().get(j).getImie() + " " + dziennikKlasowy.get(i).getUczniowie().get(j).getNazwisko() + "    ");

                for (int k = 0; k < dziennikKlasowy.get(i).getNauczycieleKlasy().size(); k++) {
                    System.out.print(dziennikKlasowy.get(i).getNauczycieleKlasy().get(k).getPrzedmiotNauczania() + "  ");
                    System.out.print(dziennikKlasowy.get(i).getOcenyUcznia().get(j).getOcenyPrzedmiotu().get(k) + "    ");
                    //System.out.print(dziennikiKlasowe.getDziennikiKlasowe().get(i).getOcenyUcznia().get(j).getOcenyPrzedmiotu().get(k) + "    ");
                }
                System.out.println(" ");
            }

            System.out.println(" ");
            System.out.println("Ilość nauczycieli w klasie: " + dziennikKlasowy.get(i).getNauczycieleKlasy().size());
            //iloscNauczycieliSzkole += wszystkieKlasy.get(i).getNauczyciele().size();
            for (int j = 0; j < dziennikKlasowy.get(i).getNauczycieleKlasy().size(); j++) {
                System.out.println(j + 1 + ") " + dziennikKlasowy.get(i).getNauczycieleKlasy().get(j).getImie() + " " + dziennikKlasowy.get(i).getNauczycieleKlasy().get(j).getNazwisko());
                System.out.println(dziennikKlasowy.get(i).getNauczycieleKlasy().get(j).getPrzedmiotNauczania());
            }
        }
        Obliczenia obliczenia = new Obliczenia(dziennikKlasowy);
        System.out.println(" ");
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
            for (int j = 0; j < dziennikKlasowy.size(); j++) {


                for (int k = 0; k < dziennikKlasowy.get(j).getNauczycieleKlasy().size(); k++) {
                    System.out.println(" ");
                    System.out.print("Imie: " + dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getImie() + "    ?");
                    zmienioneImie = sc.nextLine();
                    if (zmienioneImie.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.imieDoZmiany(zmienioneImie);
                        nauczycieleSzkoly.remove(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikKlasowy);
                        dziennikKlasowy = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }

                    System.out.print("Nazwisko: " + dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getNazwisko() + "    ?");
                    zmienioneNazwisko = sc.nextLine();
                    if (zmienioneNazwisko.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.nazwiskoDoZmiany(zmienioneNazwisko);
                        nauczycieleSzkoly.remove(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikKlasowy);
                        dziennikKlasowy = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }

                    System.out.print("Klasa: " + dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getNazwaKlasy() + "    ?");
                    zmienionaKlasa = sc.nextLine();
                    if (zmienionaKlasa.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.klasaDoZmianyOdszedl(zmienionaKlasa);
                        nauczycieleSzkoly.remove(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        zmienionyNauczyciel = nauczycielDoZmiany.klasaDoZmianyPrzybyl(zmienionaKlasa, nauczycieleSzkoly.size());
                        nauczycieleSzkoly.add(zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikKlasowy);
                        dziennikKlasowy = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }

                    System.out.print("Przedmiot: " + dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getPrzedmiotNauczania() + "    ?");
                    zmienionyPrzedmiot = sc.nextLine();
                    if (zmienionyPrzedmiot.equals("")) {
                        System.out.println("   ---------- Bez zmiany");
                    } else {
                        Nauczyciel nauczycielDoZmiany = nauczycieleSzkoly.get(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        Nauczyciel zmienionyNauczyciel = nauczycielDoZmiany.przedmiotDoZmianyOdszedl(zmienionaKlasa);
                        nauczycieleSzkoly.remove(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex());
                        nauczycieleSzkoly.add(dziennikKlasowy.get(j).getNauczycieleKlasy().get(k).getIndex(), zmienionyNauczyciel);
                        zmienionyNauczyciel = nauczycielDoZmiany.przedmiotDoZmianyPrzybyl(zmienionaKlasa, nauczycieleSzkoly.size());
                        nauczycieleSzkoly.add(zmienionyNauczyciel);
                        DziennikiKlasowe dziennikiKlasoweDoZmiany = dziennikiKlasowe;
                        DziennikiKlasowe zmienionyDziennikKlasowy = dziennikiKlasoweDoZmiany.dziennikiKlasoweDoZmiany(nauczycieleSzkoly, dziennikKlasowy);
                        dziennikKlasowy = zmienionyDziennikKlasowy.getDziennikiKlasowe();
                        dziennikiKlasowe = zmienionyDziennikKlasowy;
                    }
                }

            }
            System.out.println("ZMIANY WYKONANE");
        }
    }
}