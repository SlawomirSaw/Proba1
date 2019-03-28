import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;

public class Szkola implements DaneSzkoly {
    public static DyrektorSzkoly dyrektorSzkoly = new DyrektorSzkoly("", "");
    public static final int WPROWADZANIE_DANYCH_UCZNIOW_I_KLAS = 1;
    public static final int WPROWADZANIE_DANYCH_NAUCZYCIELI_I_PRZEDMIOTOW = 2;
    public static final int ZESTAWIENIE_SZKOLA = 3;
    public static final int KONIEC = 4;
    public static int key = 0;
    public static int iloscZapisanychUczniDoKlas = 0;
    public static int iloscZajecWSzkole = 0;
    public static ArrayList<DziennikKlasowy> dziennikiKlasoweLista;
    public static ArrayList<Uczen> uczniowieSzkoly = new ArrayList<>();
    public static ArrayList<Klasa> klasy = new ArrayList<>();
    public static ArrayList<UczniowieKlasy> uczniowieSzkolyWgKlas = new ArrayList<>();
    public static ArrayList<Nauczyciel> nauczyciele = new ArrayList<>();
    public static ArrayList<PrzedmiotNauczania> przedmiotyNauczania = new ArrayList<>();
    public static ArrayList<Zajecie> zajecia = new ArrayList<>();
    public static String nazwaKlasy;
    public static boolean tak = false;
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        int option = 0;
        do {
            OpcjeWyboru();
            option = sc.nextInt();
            switch (option) {
                case WPROWADZANIE_DANYCH_UCZNIOW_I_KLAS:
                    WprowadzanieDanychUczniowIKlas();
                    break;
                case WPROWADZANIE_DANYCH_NAUCZYCIELI_I_PRZEDMIOTOW:
                    wprowadzanieDanychNauczycieliPrzedmiotowZajec();
                    break;
                case ZESTAWIENIE_SZKOLA:
                    zestawienieSzkola();
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
        System.out.print("WYBIERZ:    1-WPROWADZANIE DANYCH UCZNIÓW I KLAS    2-WPROWADZANIE DANYCH NAUCZYCIELI I PRZEDMIOTÓW    3-ZESTAWIENIE INFORMACJI O SZKOLE   4-KONIEC\t");
        System.out.println();
    }

    private static void WprowadzanieDanychUczniowIKlas() {
        System.out.println("------------------------- 1-WPROWADZANIE_DANYCH UCZNIÓW I KLAS -------------------------");
        do {
            System.out.print("WYBIERZ: 1-Dyrektor      2-Uczniowie     3-Klasy     4-Przypisz Uczniów do Klas    5-Powrót do głównego MENU\t");
            key = sc.nextInt();
            System.out.println();
            switch (key) {
                case 1:
                    dodajDyrektora();
                    System.out.println();
                    break;
                case 2:
                    wyswietlListeUczniow();
                    dodajUczniow();
                    System.out.println();
                    break;
                case 3:
                    wyswietlListeKlas();
                    dodajKlasy();
                    System.out.println();
                    break;
                case 4:
                    wyswietlListeUczniZapisanychDoKlas();
                    dodajUczniDoKlas();
                    System.out.println();
                    break;
            }
        } while (key != 5);
    }

    private static void wprowadzanieDanychNauczycieliPrzedmiotowZajec() {
        System.out.println("------------------------- 2-WPROWADZANIE DANYCH NAUCZYCIELI PRZEDMIOTÓW ZAJĘć -------------------------");
        do {
            System.out.print("WYBIERZ: 1-Nauczyciele      2-Przedmioty     3-Zajęcia     4-Powrót do głównego MENU\t");
            key = sc.nextInt();
            System.out.println();
            switch (key) {

                case 1:
                    wyswietlListeNauczycieli();
                    dodajNauczyciela();
                    System.out.println();
                    break;
                case 2:
                    wyswietlListePrzedmiotow();
                    dodajPrzedmiot();
                    System.out.println();
                    break;
                case 3:
                    wyswietlListeZajec();
                    dodajZajecia();
                    System.out.println();
                    break;
            }
        } while (key != 4);
    }

    private static void dodajDyrektora() {
        System.out.println("Obecny Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
        if (czyDodac("nowego Dytektora Szkoły?")) {
            dyrektorSzkoly = new DyrektorSzkoly(dodajImie(), dodajNazwisko());
            System.out.println("Nowym Dyrektorem Szkoły jest: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
        }
    }

    private static void dodajUczniow() {
        while (czyDodac("nowego ucznia?")) {
            System.out.println("Podaj Imiona i Nazwiska uczniów");
            uczniowieSzkoly.add(new Uczen(uczniowieSzkoly.size() + 1, dodajImie(), dodajNazwisko()));
            System.out.println("Dodano: " + uczniowieSzkoly.size() + ") " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getImie() + " " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getNazwisko());
        }
    }

    private static void dodajKlasy() {
        sc = new Scanner(System.in);
        while (czyDodac("nową klasę?")) {
            System.out.println("Podaj numer Klasy");
            do {
                do {
                    System.out.print("Klasa Nr: ");
                    nazwaKlasy = sc.nextLine();
                } while (czyJestPustyString(nazwaKlasy));
            } while (czyIstnieje(nazwaKlasy, klasy));
            klasy.add(new Klasa(klasy.size() + 1, nazwaKlasy));
            uczniowieSzkolyWgKlas.add(new UczniowieKlasy(klasy.size() + 1, new ArrayList<Integer>()));
            System.out.println("Dodano: " + klasy.size() + ") Klasa Nr: " + klasy.get(klasy.size() - 1).getNazwaKlasy());
        }
    }

    private static void dodajUczniDoKlas() {
        sc = new Scanner(System.in);
        if (uczniowieSzkoly.size() > 0 && klasy.size() > 0) {
            ArrayList<Uczen> uczniowieNieZapisaniDoKlas = new ArrayList<>();
            if (iloscZapisanychUczniDoKlas == uczniowieSzkoly.size()) {
                System.out.println("Wszyscy uczniowie już są zapisani do Klas!");
                System.out.println();
            } else {
                if (czyDodac("nowych Uczni do Klas?")) {
                    if (iloscZapisanychUczniDoKlas == 0) {
                        System.out.println("Rozpocznij przypisywanie uczniów do klas");
                        System.out.println("Lista wszytkich uczni w szkole:");
                        wyswietlListeUczniow();
                        System.out.println();
                        System.out.println("Lista wszystkich klas w szkole:");
                        wyswietlListeKlas();
                        System.out.println();
                        uczniowieNieZapisaniDoKlas = uczniowieSzkoly;
                    } else {
                        System.out.println("Lista wszystkich klas w szkole:");
                        wyswietlListeKlas();
                        System.out.println("Uczniowie, którzy jeszcze nie są przypisani do klas:");
                        for (Uczen us : uczniowieSzkoly) {
                            int zawiera = 0;
                            for (UczniowieKlasy uswk : uczniowieSzkolyWgKlas) {
                                if (uswk.getIdUczniowKlasy().contains(us.getidUcznia())) {
                                    zawiera = 1;
                                }
                            }
                            if (zawiera == 0) {
                                System.out.println(us.getidUcznia() + ") " + us.getImie() + " " + us.getNazwisko());
                                uczniowieNieZapisaniDoKlas.add(us);
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("Dodaj ucznia:");
                    for (Uczen wolny : uczniowieNieZapisaniDoKlas) {
                        System.out.print(wolny.getidUcznia() + ") " + wolny.getImie() + " " + wolny.getNazwisko());
                        int idKlasy = 0;
                        do {
                            System.out.print("      do Klasy nr: ");
                            String nazwaKlasy = sc.nextLine();
                            for (Klasa k : klasy) {
                                if (nazwaKlasy.equals(k.getNazwaKlasy())) {
                                    idKlasy = k.getIdKlasy();
                                }
                            }
                            if (idKlasy == 0) {
                                System.out.println("BŁĄD ---> nie ma klasy o takim numerze");
                            }
                        } while (idKlasy == 0);
                        ArrayList<Integer> noweIdUczniowKlasy = uczniowieSzkolyWgKlas.get(idKlasy - 1).getIdUczniowKlasy();
                        noweIdUczniowKlasy.add(wolny.getidUcznia());
                        iloscZapisanychUczniDoKlas++;
                        uczniowieSzkolyWgKlas.remove(idKlasy - 1);
                        uczniowieSzkolyWgKlas.add(idKlasy - 1, new UczniowieKlasy(idKlasy, noweIdUczniowKlasy));
                    }
                    System.out.println("Wszyscy uczniowie już są zapisani do Klas!");
                    wyswietlListeUczniZapisanychDoKlas();
                    System.out.println();
                }
            }
        } else {
            System.out.println("Lista uczni Szkoły lub lista Klas jest pusta!");
        }
    }

    private static void dodajNauczyciela() {
        while (czyDodac("nowego nauczyciela?")) {
            System.out.println("Podaj Imiona i Nazwiska nauczycieli");
            nauczyciele.add(new Nauczyciel(nauczyciele.size() + 1, dodajImie(), dodajNazwisko()));
            System.out.println("Dodano: " + nauczyciele.get(nauczyciele.size() - 1).getIdNauczyciela() + ") " + nauczyciele.get(nauczyciele.size() - 1).getImie() + " " + nauczyciele.get(nauczyciele.size() - 1).getNazwisko());
        }
    }

    private static void dodajPrzedmiot() {
        while (czyDodac("nowy przedmiot nauczania?")) {
            System.out.println("Podaj nazwę");
            przedmiotyNauczania.add(new PrzedmiotNauczania(przedmiotyNauczania.size() + 1, dodajNazwePrzedmiotu()));
            System.out.println("Dodano: " + przedmiotyNauczania.get(przedmiotyNauczania.size() - 1).getIdPrzedmiotNauczania() + ") " + przedmiotyNauczania.get(przedmiotyNauczania.size() - 1).getPrzedmiotNauczania());
        }
    }

    private static void dodajZajecia() {
        if (nauczyciele.size() > 0 && przedmiotyNauczania.size() > 0) {
            while (czyDodac("nowe Zajęcia szkolne?")) {
                sc = new Scanner(System.in);
                if (iloscZajecWSzkole == 0) {
                    System.out.println("Rozpocznij przypisywanie Nauczycieli do Zajęć");
                    System.out.println("Lista wszytkich nauczycieli w szkole:");
                    wyswietlListeNauczycieli();
                    System.out.println();
                    System.out.println("Lista wszystkich przedmiotów nauczania:");
                    wyswietlListePrzedmiotow();
                    System.out.println();
                } else {
                    System.out.println("Lista wszytkich nauczycieli w szkole:");
                    wyswietlListeNauczycieli();
                    System.out.println();
                    System.out.println("Lista wszystkich przedmiotów nauczania:");
                    wyswietlListePrzedmiotow();
                    System.out.println();
                    System.out.println("Lista zaplanowanych zajęć:");
                    wyswietlListeZajec();
                    System.out.println();
                }
                System.out.print("Dodaj nauczyciela z ID:");
                int id = sc.nextInt();
                sc = new Scanner(System.in);
                int idZajecie = 0;
                String nazwaPrzedmiotuNauczania;
                for (Nauczyciel nId : nauczyciele) {
                    if (nId.getIdNauczyciela() == id) {
                        System.out.print(nId.getIdNauczyciela() + ") " + nId.getImie() + " " + nId.getNazwisko());
                        do {
                            System.out.print("      do zajęć z przedmiotu nauczania o nazwie: ");
                            nazwaPrzedmiotuNauczania = sc.nextLine();
                            if (podajIdPrzedmiotuNauczania(nazwaPrzedmiotuNauczania) == 0) {
                                System.out.println("BŁĄD ---> nie ma takiego przedmiotu nauczania");
                            }
                        } while (podajIdPrzedmiotuNauczania(nazwaPrzedmiotuNauczania) == 0);
                        zajecia.add(new Zajecie(zajecia.size() + 1, podajIdPrzedmiotuNauczania(nazwaPrzedmiotuNauczania), nId.getIdNauczyciela(), LocalDateTime.now()));
                        System.out.println("Dodano: " + zajecia.size() + " )" + nazwaPrzedmiotuNauczania + "\t\t\t" + nId.getImie() + " " + nId.getNazwisko() + "\t\t\t" + zajecia.get(zajecia.size() - 1).getDataIGodzinaRozpoczecia());
                        System.out.println();
                    }
                    iloscZajecWSzkole++;
                }
            }
        } else {
            System.out.println("Lista nauczycieli lub lista przedmiotów nauczania jest pusta!");
        }
    }

    private static void wyswietlListeUczniow() {
        for (Uczen lo : uczniowieSzkoly) {
            System.out.println(lo.getidUcznia() + ") " + lo.getImie() + " " + lo.getNazwisko());
        }
    }

    private static void wyswietlListeKlas() {
        for (Klasa lk : klasy) {
            System.out.println(lk.getIdKlasy() + ") Klasa Nr: " + lk.getNazwaKlasy());
        }
    }

    private static void wyswietlListeUczniZapisanychDoKlas() {
        if (uczniowieSzkolyWgKlas.size() > 0) {
            for (UczniowieKlasy u : uczniowieSzkolyWgKlas) {
                for (Klasa k : klasy) {
                    if (k.getIdKlasy() == u.getIdKlasy()) {
                        System.out.println("Klasa nr: " + klasy.get(klasy.indexOf(k)).getNazwaKlasy());
                        for (int iu : u.getIdUczniowKlasy()) {
                            for (Uczen us : uczniowieSzkoly) {
                                if (us.getidUcznia() == iu) {
                                    System.out.println("\t\t" + uczniowieSzkoly.get(uczniowieSzkoly.indexOf(us)).getidUcznia() + ") " + uczniowieSzkoly.get(uczniowieSzkoly.indexOf(us)).getImie() + " " + uczniowieSzkoly.get(uczniowieSzkoly.indexOf(us)).getNazwisko());
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private static void wyswietlListeNauczycieli() {
        for (Nauczyciel ln : nauczyciele) {
            System.out.println(ln.getIdNauczyciela() + ") " + ln.getImie() + " " + ln.getNazwisko());
        }
    }

    private static void wyswietlListePrzedmiotow() {
        for (PrzedmiotNauczania pn : przedmiotyNauczania) {
            System.out.println(pn.getIdPrzedmiotNauczania() + ") " + pn.getPrzedmiotNauczania());
        }
    }

    private static void wyswietlListeZajec() {
        String imieNauczyciela = "";
        String nazwiskoNauczyciela = "";
        String nazwaPrzedmiotu = "";
        for (Zajecie z : zajecia) {
            for (PrzedmiotNauczania p : przedmiotyNauczania) {
                if (p.getIdPrzedmiotNauczania() == z.getIdPrzedmiotNauczania()) {
                    nazwaPrzedmiotu = p.getPrzedmiotNauczania();
                }
            }
            for (Nauczyciel n : nauczyciele) {
                if (n.getIdNauczyciela() == z.getIdNauczyciela()) {
                    imieNauczyciela = n.getImie();
                    nazwiskoNauczyciela = n.getNazwisko();
                }
            }
            System.out.println(z.getIdZajecie() + ") " + nazwaPrzedmiotu + "\t\t\t" + imieNauczyciela + " " + nazwiskoNauczyciela + "\t\t\t" + z.getDataIGodzinaRozpoczecia());
        }
    }

    private static boolean czyDodac(String element) {
        sc = new Scanner(System.in);
        tak = false;
        int spr = 0;
        System.out.print("        Czy dodać " + element + "    ENTER-Tak    n-Nie\t");
        do {
            spr = 0;
            String litera = sc.nextLine();
            if (!litera.equals("n") && !litera.equals("")) {
                System.out.print("BŁAD ---->   Czy dodać " + element + "    ENTER-Tak    n-Nie\t");
                spr = 1;
            }
            if (litera.equals("n")) {
                tak = false;
            } else {
                tak = true;
            }
        }
        while (spr == 1);
        return tak;
    }

    private static boolean czyIstnieje(String dana, ArrayList<Klasa> lista) {
        tak = false;
        for (Klasa d : lista) {
            if (dana.equals(d.getNazwaKlasy())) {
                System.out.print("Oznaczenie " + dana + " już istnieje ---> Wprowadź inne oznaczenie\t");
                tak = true;
            }
        }
        return tak;
    }

    private static int podajIdPrzedmiotuNauczania(String dana) {
        int idPrzedmiotuNauczania = 0;
        for (PrzedmiotNauczania d : przedmiotyNauczania) {
            if (dana.equals(d.getPrzedmiotNauczania())) {
                idPrzedmiotuNauczania = d.getIdPrzedmiotNauczania();
            }
        }
        return idPrzedmiotuNauczania;
    }

    private static String dodajImie() {
        sc = new Scanner(System.in);
        String imie = "";
        do {
            System.out.print("imię: ");
            imie = sc.nextLine();
        } while (czyJestPustyString(imie));
        return imie;
    }

    private static String dodajNazwisko() {
        sc = new Scanner(System.in);
        String nazwisko = "";
        do {
            System.out.print("nazwisko: ");
            nazwisko = sc.nextLine();
        } while (czyJestPustyString(nazwisko));
        return nazwisko;
    }

    private static String dodajNazwePrzedmiotu() {
        sc = new Scanner(System.in);
        String przedmiot = "";
        do {
            System.out.print("przedmiot: ");
            przedmiot = sc.nextLine();
        } while (czyJestPustyString(przedmiot));
        return przedmiot;
    }

    private static boolean czyJestPustyString(String nazwa) {
        tak = false;
        if (nazwa.equals("")) {
            System.out.print("Nic nie wprowadziłeś ---> wprowadź\t");
            tak = true;
        }
        return tak;
    }

    private static void zestawienieSzkola() {
    }
}