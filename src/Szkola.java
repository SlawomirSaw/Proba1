import java.util.Scanner;
import java.util.ArrayList;


public class Szkola implements DaneSzkoly {
    public static int iloscKlasSzkole = 0;
    public static int iloscUczniowKlasie = 0;
    //public static String nazwaSzkoly;
    public static DyrektorSzkoly dyrektorSzkoly;
    public static int iloscNauczycieliKlasie = 0;
    public static int iloscUczniowSzkole = 0;
    public static int iloscNauczycieliSzkole = 0;
    public static final int WPROWADZANIE_DANYCH = 1;
    public static final int ZESTAWIENIE_SZKOLA = 2;
    public static final int KONIEC = 3;
    public static ArrayList<Klasa> wszystkieKlasy;
    public static ArrayList<DziennikKlasowy> dziennikKlasowy;
    public static ArrayList<DzienniczekUcznia> dzienniczekUcznia;
    public static ArrayList<DziennikPrzedmiotu> dziennikiPrzedmiotów;
    public static ArrayList<Uczen> uczniowieKlasy;
    public static ArrayList<NauczycielPrzedmiotu> nauczycieleKlasy;
    public static ArrayList<OcenyPrzedmiotu> ocenyPrzedmiotu;


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
            }
        } while (option != KONIEC);
        sc.close();
    }


    private static void OpcjeWyboru() {
        System.out.println("OpcjeWyboru");
        System.out.println("WYBIERZ:    WPROWADZANIE_DANYCH UCZNIÓW I NAUCZYCIELI KLAS = 1  " + "ZESTAWIENIE_INFORMACJI_O_KLASACH = 2" + "   " + "KONIEC = 3");
    }


    private static void Wprowadzanie_Danych(Scanner sc) {

        wszystkieKlasy = new ArrayList<>();
        dziennikKlasowy = new ArrayList<>();
        Klasa klasa;

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

        for (int i = 0; i < iloscKlasSzkole; i++) {
            uczniowieKlasy = new ArrayList<>();
            nauczycieleKlasy = new ArrayList<>();
            dzienniczekUcznia = new ArrayList<>();
            ocenyPrzedmiotu = new ArrayList<>();


            System.out.print("  Podaj Numer lub Nazwę klasy ---------------------------- Klasa Nr : ");
            String nazwaKlasy = sc.nextLine();


            System.out.print("Ilość nauczycieli uczących w klasie Nr " + nazwaKlasy + " : ");
            iloscNauczycieliKlasie = sc.nextInt();
            System.out.println("Podaj Imiona, Nazwiska i Funkcje nauczycieli klasy Nr " + nazwaKlasy + " : ");
            sc.nextLine();
            for (int j = 0; j < iloscNauczycieliKlasie; j++) {
                System.out.print(j + 1 + ") imię: ");
                String imieNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") nazwisko: ");
                String nazwiskoNauczyciela = sc.nextLine();
                System.out.print(j + 1 + ") funkacja: ");
                String funkcjaNauczyciela = sc.nextLine();
                System.out.println(" ");
                nauczycieleKlasy.add(j, new NauczycielPrzedmiotu(imieNauczyciela, nazwiskoNauczyciela, funkcjaNauczyciela));
            }

            System.out.print("Ilość uczniów w klasie Nr " + nazwaKlasy + " : ");
            iloscUczniowKlasie = sc.nextInt();
            //System.out.println(" ");
            System.out.println("Podaj Imiona, Nazwiska i oceny uczniów klasy Nr " + nazwaKlasy + " : ");
            sc.nextLine();
            for (int j = 0; j < iloscUczniowKlasie; j++) {
                System.out.print(j + 1 + ") imię: ");
                String imieUcznia = sc.nextLine();
                System.out.print(j + 1 + ") nazwisko: ");
                String nazwiskoUcznia = sc.nextLine();
                System.out.println("        oceny z poszczególnych przedmiotów:");
                ArrayList ocena = new ArrayList();
                for (int k = 0; k < iloscNauczycieliKlasie; k++) {
                    System.out.print("      " + nauczycieleKlasy.get(k).getFunkcja() + "     ocena: ");
                    ocena.add(k, sc.nextInt());
                }
                System.out.println(" ");
                sc.nextLine();
                Uczen uczen = new Uczen(imieUcznia, nazwiskoUcznia);
                uczniowieKlasy.add(j, uczen);
                dzienniczekUcznia.add(j, new DzienniczekUcznia(uczen, nauczycieleKlasy, ocena));
                ocenyPrzedmiotu.add(j, new OcenyPrzedmiotu(ocena));

            }
            wszystkieKlasy.add(i, new Klasa(nazwaKlasy, uczniowieKlasy, nauczycieleKlasy));
            dziennikKlasowy.add(i, new DziennikKlasowy(nazwaKlasy, uczniowieKlasy, nauczycieleKlasy, ocenyPrzedmiotu));
        }
        //Szkola szkola = new Szkola(nazwaSzkoly, wszystkieKlasy, dyrektorSzkoly);
    }


    private static void Zestawienie_Szkola() {

        //iloscUczniowSzkole = 0;
        //iloscNauczycieliSzkole = 0;
        System.out.println("------------------------- ZESTAWIENIE_INFORMACJI_O_SZKLOLE -------------------------");
        System.out.println(" ");
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        System.out.println("Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());

        for (int i = 0; i < wszystkieKlasy.size(); i++) {
            System.out.println(" ");
            System.out.println("----------- Dane klasy Nr: " + wszystkieKlasy.get(i).getNazwaKlasy() + " ----------- ");
            System.out.println("Ilość uczniów w klasie: " + wszystkieKlasy.get(i).getUczniowie().size());
            //iloscUczniowSzkole += wszystkieKlasy.get(i).getUczniowie().size();

            for (int j = 0; j < wszystkieKlasy.get(i).getUczniowie().size(); j++) {
                System.out.print(j + 1 + ") " + wszystkieKlasy.get(i).getUczniowie().get(j).getImie() + " " + wszystkieKlasy.get(i).getUczniowie().get(j).getNazwisko() + "    ");

                for (int k = 0; k < wszystkieKlasy.get(i).getNauczyciele().size(); k++) {
                    System.out.print(wszystkieKlasy.get(i).getNauczyciele().get(k).getFunkcja() + "  ");
                    System.out.print(dziennikKlasowy.get(i).getOcenyPrzedmiotu().get(j).getOcenyPrzedmiotu().get(k) + "    ");
                }
                System.out.println(" ");
            }

            System.out.println(" ");
            System.out.println("Ilość nauczycieli w klasie: " + wszystkieKlasy.get(i).getNauczyciele().size());
            //iloscNauczycieliSzkole += wszystkieKlasy.get(i).getNauczyciele().size();
            for (int j = 0; j < wszystkieKlasy.get(i).getNauczyciele().size(); j++) {
                System.out.println(j + 1 + ") " + wszystkieKlasy.get(i).getNauczyciele().get(j).getImie() + " " + wszystkieKlasy.get(i).getNauczyciele().get(j).getNazwisko());
                System.out.println(wszystkieKlasy.get(i).getNauczyciele().get(j).getFunkcja());
            }
        }
        Obliczenia obliczenia = new Obliczenia(dziennikKlasowy);
        System.out.println(" ");
        System.out.println("Ilość klas w szkole:                     " + obliczenia.getIloscKlasSzkole());
        System.out.println("Ilość wszystkich uczniów w szkole:       " + obliczenia.getIloscUczniowSzkole());
        System.out.println("Ilość wszystkich nauczycieli w szkole:   " + obliczenia.getIloscNauczycieliSzkole());
    }
}