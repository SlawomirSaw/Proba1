import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Szkola extends JFrame implements ActionListener, DaneSzkoly, Serializable {
    private static DyrektorSzkoly dyrektorSzkoly = new DyrektorSzkoly("", "");
    private static final int WPROWADZANIE_DANYCH_UCZNIOW_I_KLAS = 1;
    private static final int WPROWADZANIE_DANYCH_NAUCZYCIELI_I_PRZEDMIOTOW = 2;
    private static final int ZESTAWIENIE_SZKOLA = 3;
    private static final int KONIEC = 4;
    private static int key = 0;
    private static int iloscZapisanychUczniDoKlas = 0;
    private static int iloscZajecWSzkole = 0;
    private static int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static int selectedColumn = -1;
    private static int selectedRow = -1;
    private static ArrayList<Uczen> uczniowieSzkoly = new ArrayList<Uczen>();
    private static ArrayList<Uczen> uczniowieSzkolySpisAlfabet = new ArrayList<Uczen>();
    private static ArrayList<Klasa> klasy = new ArrayList<Klasa>();
    private static ArrayList<UczniowieKlasy> uczniowieSzkolyWgKlas = new ArrayList<UczniowieKlasy>();
    private static ArrayList<Nauczyciel> nauczyciele = new ArrayList<Nauczyciel>();
    private static ArrayList<PrzedmiotNauczania> przedmiotyNauczania = new ArrayList<PrzedmiotNauczania>();
    private static ArrayList<Zajecie> zajecia = new ArrayList<Zajecie>();
    private static ArrayList<OcenaUcznia> ocenyUczniow = new ArrayList<OcenaUcznia>();
    private static boolean tak = false;
    private static Scanner sc = new Scanner(System.in);
    private static String imie = "";
    private static String nazwisko = "";
    private static String przedmiot = "";
    private static String nazwaWprowadzona = "";
    private static JButton button;
    private static JScrollPane scrolPanelMain;
    private static JPanel panelMain;
    private static JPanel panelKlasy = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelPrzedmioty = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelNauczyciele = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelUczniowie = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panel1 = new JPanel();
    private static JPanel panel2 = new JPanel();
    private static JPanel panel3 = new JPanel();
    private static JPanel panel4 = new JPanel();
    private static JFrame mainFrame;
    private static JMenuBar menuBar;
    private static Box box2 = new Box(BoxLayout.X_AXIS);
    private static Box box3 = new Box(BoxLayout.Y_AXIS);

    public static void main(String[] args) {
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        uczniowieSzkoly = odczytZPlikuUczniowieSzkoly();
        uczniowieSzkolySpisAlfabet = listaAlfabetUczniowSzkoly();
        uczniowieSzkolyWgKlas = odczytZPlikuUczniowieSzkolyWgKlas();
        for (UczniowieKlasy uk : uczniowieSzkolyWgKlas) {
            if (!uk.getIdUczniowKlasy().isEmpty()) {
                iloscZapisanychUczniDoKlas += uk.getIdUczniowKlasy().size();
            }
        }
        klasy = odczytZPlikuKlasy();
        nauczyciele = odczytZPlikuNauczyciele();
        przedmiotyNauczania = odczytZPlikuPrzedmiotyNauczania();
        zajecia = odczytZPlikuZajecia();
        ocenyUczniow = odczytZPlikuOcenyUczniow();
        iloscZajecWSzkole = zajecia.size();
        mainFrame();
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
            System.out.print("WYBIERZ: 1-Dyrektor      2-Uczniowie     3-Klasy     4-Przypisz Uczniów do Klas    5-Oceny    6-Powrót do głównego MENU\t");
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
                    zapiszDoPlikuUczniowieSzkoly();
                    System.out.println();
                    break;
                case 3:
                    wyswietlListeKlas();
                    dodajKlasy();
                    zapiszDoPlikuKlasy();
                    System.out.println();
                    break;
                case 4:
                    wyswietlListeUczniZapisanychDoKlas();
                    dodajUczniDoKlas();
                    zapiszDoPlikuUczniowieSzkolyWgKlas();
                    System.out.println();
                    break;
                case 5:
                    wyswietlListeUczniow();
                    dodajOcene();
                    zapiszDoPlikuOcenyUczniow();
                    System.out.println();
                    break;
            }
        } while (key != 6);
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
                    zapiszDoPlikuNauczyciele();
                    System.out.println();
                    break;
                case 2:
                    wyswietlListePrzedmiotow();
                    dodajPrzedmiot();
                    zapiszDoPlikuPrzedmiotyNauczania();
                    System.out.println();
                    break;
                case 3:
                    wyswietlListeZajec();
                    dodajZajecia();
                    zapiszDoPlikuZajecia();
                    System.out.println();
                    break;
            }
        } while (key != 4);
    }

    private static void dodajDyrektora() {
        System.out.println("Obecny Dyrektor Szkoły: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
        if (czyDodac("nowego Dytektora Szkoły?")) {
            dyrektorSzkoly = new DyrektorSzkoly(dodaj("imię"), dodaj("nazwisko"));
            System.out.println("Nowym Dyrektorem Szkoły jest: " + dyrektorSzkoly.getImie() + " " + dyrektorSzkoly.getNazwisko());
        }
    }

    private static void dodajUczniow() {
        while (czyDodac("nowego ucznia?")) {
            System.out.println("Podaj Imiona i Nazwiska uczniów");
            uczniowieSzkoly.add(new Uczen(uczniowieSzkoly.size() + 1, dodajImie(), dodajNazwisko()));
            ocenyUczniow.add(new OcenaUcznia(ocenyUczniow.size() + 1));
            System.out.println("Dodano: " + uczniowieSzkoly.size() + ") " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getImie() + " " + uczniowieSzkoly.get(uczniowieSzkoly.size() - 1).getNazwisko());
        }
    }

    private static void dodajKlasy() {
        sc = new Scanner(System.in);
        while (czyDodac("nową klasę?")) {
            System.out.println("Podaj numer Klasy");
            String nazwaKlasy;
            do {
                do {
                    System.out.print("Klasa Nr: ");
                    nazwaKlasy = sc.nextLine();
                } while (czyJestPustyString(nazwaKlasy));
            } while (czyIstnieje(nazwaKlasy, klasy));
            klasy.add(new Klasa(klasy.size() + 1, nazwaKlasy));
            uczniowieSzkolyWgKlas.add(new UczniowieKlasy(klasy.size(), new ArrayList<>()));
            zapiszDoPlikuUczniowieSzkolyWgKlas();
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
                        sc = new Scanner(System.in);
                        do {
                            System.out.print("      do Klasy nr: ");
                            String nazwaKlasy = sc.nextLine();
                            for (Klasa k : klasy) {
                                if (nazwaKlasy.equals(k.getNazwaKlasy())) {
                                    idKlasy = k.getIdKlasy();
                                    ArrayList<Integer> noweIdUczniowKlasy = uczniowieSzkolyWgKlas.get(idKlasy - 1).getIdUczniowKlasy();
                                    noweIdUczniowKlasy.add(wolny.getidUcznia());
                                    iloscZapisanychUczniDoKlas++;
                                    uczniowieSzkolyWgKlas.remove(idKlasy - 1);
                                    uczniowieSzkolyWgKlas.add(idKlasy - 1, new UczniowieKlasy(idKlasy, noweIdUczniowKlasy));
                                }
                            }
                            if (idKlasy == 0) {
                                System.out.println("BŁĄD ---> nie ma klasy o takim numerze");
                            }
                        } while (idKlasy == 0);
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

    private static void dodajOcene() {
        sc = new Scanner(System.in);
        while (czyDodac("oceny ucznia?")) {
            System.out.print("Podaj ID ucznia ");
            int idUcznia = sc.nextInt();
            System.out.println(uczniowieSzkoly.get(idUcznia - 1).getImie() + " " + uczniowieSzkoly.get(idUcznia - 1).getNazwisko());
            for (UczniowieKlasy uk : uczniowieSzkolyWgKlas) {
                for (int idU : uk.getIdUczniowKlasy()) {
                    if (idU == idUcznia) {
                        for (Zajecie z : zajecia) {
                            if (uk.getIdKlasy() == z.getIdKlasa()) {
                                System.out.print(z.getIdZajecie() + ") " + przedmiotyNauczania.get(z.getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania() + " - ");
                                System.out.print("istniejące oceny: ");
                                System.out.print(listaOcenUczniaZZajec(idUcznia, z.getIdZajecie()));
                                System.out.println();
                                while (czyDodac("ocenę?")) {
                                    sc = new Scanner(System.in);
                                    int ocenaUcznia = sc.nextInt();
                                    ocenyUczniow.add(new OcenaUcznia(idUcznia, z.getIdZajecie(), ocenaUcznia, LocalDateTime.now()));
                                }
                            }
                        }
                    }
                }
            }
            System.out.println();
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
        if (nauczyciele.size() > 0 && przedmiotyNauczania.size() > 0 && klasy.size() > 0) {
            boolean ponownaPetla = true;
            while (czyDodac("nowe Zajęcia szkolne?")) {
                sc = new Scanner(System.in);
                if (ponownaPetla) {
                    System.out.println("Lista wszytkich nauczycieli w szkole:");
                    wyswietlListeNauczycieli();
                    System.out.println();
                    System.out.println("Lista wszystkich przedmiotów nauczania:");
                    wyswietlListePrzedmiotow();
                    System.out.println();
                    System.out.println("Lista wszystkich klas w szkole:");
                    wyswietlListeKlas();
                    System.out.println();
                    if (iloscZajecWSzkole == 0) {
                        System.out.println("Nie ma jeszcze ustalonych żadnych zajęć, ustal nowe zajęcia");
                    } else {
                        System.out.println("Lista zaplanowanych zajęć:");
                        wyswietlListeZajec();
                        System.out.println();
                    }
                }
                int id;
                do {
                    System.out.print("Nowe zajęcie z nauczycielem ID nr:");
                    id = sc.nextInt();
                    if (!czyIstnieje(id, nauczyciele)) {
                        System.out.println("BŁĄD ---> nie ma nauczyciela o takim ID");
                    }
                } while (!czyIstnieje(id, nauczyciele));
                sc = new Scanner(System.in);
                String nazwaPrzedmiotuNauczania;
                String nazwaKlasy;
                for (Nauczyciel nId : nauczyciele) {
                    if (nId.getIdNauczyciela() == id) {
                        System.out.print(nId.getIdNauczyciela() + ") " + nId.getImie() + " " + nId.getNazwisko());
                        do {
                            System.out.print("      z przedmiotem nauczania o nazwie: ");
                            nazwaPrzedmiotuNauczania = sc.nextLine();
                            if (podajIdPrzedmiotu(nazwaPrzedmiotuNauczania) == 0) {
                                System.out.println("BŁĄD ---> nie ma takiego przedmiotu nauczania");
                            }
                        } while (podajIdPrzedmiotu(nazwaPrzedmiotuNauczania) == 0);
                        do {
                            System.out.print("      z klasą o nazwie: ");
                            nazwaKlasy = sc.nextLine();
                            if (podajIdKlasy(nazwaKlasy) == 0) {
                                System.out.println("BŁĄD ---> nie ma takiej klasy");
                            }
                        } while (podajIdKlasy(nazwaKlasy) == 0);
                        zajecia.add(new Zajecie(zajecia.size() + 1, podajIdPrzedmiotu(nazwaPrzedmiotuNauczania), nId.getIdNauczyciela(), podajIdKlasy(nazwaKlasy), LocalDateTime.now()));
                        ponownaPetla = false;
                        System.out.println("Dodano: " + zajecia.size() + ") " + nazwaPrzedmiotuNauczania + "\t\t\t" + nId.getImie() + " " + nId.getNazwisko() + "\t\t\t" + nazwaKlasy + "\t\t\t" + zajecia.get(zajecia.size() - 1).getDataIGodzinaRozpoczecia());
                        System.out.println();
                    }
                    iloscZajecWSzkole++;
                }
            }
        } else {
            System.out.println("Lista nauczycieli lub lista przedmiotów nauczania lub lista klas jest pusta!");
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
        String nazwaKlasy = "";
        for (Zajecie z : zajecia) {
            for (PrzedmiotNauczania p : przedmiotyNauczania) {
                if (z.getIdPrzedmiotNauczania() == p.getIdPrzedmiotNauczania()) {
                    nazwaPrzedmiotu = p.getPrzedmiotNauczania();
                }
            }
            for (Nauczyciel n : nauczyciele) {
                if (z.getIdNauczyciela() == n.getIdNauczyciela()) {
                    imieNauczyciela = n.getImie();
                    nazwiskoNauczyciela = n.getNazwisko();
                }
            }
            for (Klasa k : klasy) {
                if (z.getIdKlasa() == k.getIdKlasy()) {
                    nazwaKlasy = k.getNazwaKlasy();
                }
            }
            System.out.println(z.getIdZajecie() + ") " + nazwaPrzedmiotu + "\t\t\t" + imieNauczyciela + " " + nazwiskoNauczyciela + "\t\t\t" + nazwaKlasy + "\t\t\t" + z.getDataIGodzinaRozpoczecia());
        }
    }

    private static boolean czyDodac(String element) {
        sc = new Scanner(System.in);
        tak = false;
        int spr;
        System.out.print("        Czy dodać " + element + "    ENTER-Tak    n-Nie\t");
        do {
            spr = 0;
            String litera = sc.nextLine();
            if (!litera.equals("n") && !litera.equals("")) {
                System.out.print("BŁAD ---->   Czy dodać " + element + "    ENTER-Tak    n-Nie\t");
                spr = 1;
            }
            tak = !litera.equals("n");
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

    private static boolean czyIstnieje(int dana, ArrayList<Nauczyciel> lista) {
        tak = false;
        for (Nauczyciel d : lista) {
            if (dana == d.getIdNauczyciela()) {
                tak = true;
            }
        }
        return tak;
    }

    private static int podajIdPrzedmiotu(String nazwaPrzedmiotu) {
        int idPrzedmiotuNauczania = 0;
        for (PrzedmiotNauczania d : przedmiotyNauczania) {
            if (nazwaPrzedmiotu.equals(d.getPrzedmiotNauczania())) {
                idPrzedmiotuNauczania = d.getIdPrzedmiotNauczania();
            }
        }
        return idPrzedmiotuNauczania;
    }

    private static int podajIdKlasy(String nazwaKlasy) {
        int idKlasy = 0;
        for (Klasa k : klasy) {
            if (nazwaKlasy.equals(k.getNazwaKlasy())) {
                idKlasy = k.getIdKlasy();
            }
        }
        return idKlasy;
    }

    private static String podajNazweKlasyUczniaId(int idUcznia) {
        String nazwaKlasyUcznia = "";
        for (UczniowieKlasy u : uczniowieSzkolyWgKlas) {
            for (Integer idU : u.getIdUczniowKlasy()) {
                if (idU == idUcznia) {
                    nazwaKlasyUcznia = klasy.get(u.getIdKlasy() - 1).getNazwaKlasy();
                }
            }
        }
        return nazwaKlasyUcznia;
    }

    private static ArrayList<Uczen> listaUczniKlasy(String nazwaKlasy) {
        ArrayList<Uczen> listaUczniKlasy = new ArrayList<Uczen>();
        for (int j = 0; j < uczniowieSzkolyWgKlas.get(podajIdKlasy(nazwaKlasy) - 1).getIdUczniowKlasy().size(); j++) {
            listaUczniKlasy.add(new Uczen(uczniowieSzkolyWgKlas.get(podajIdKlasy(nazwaKlasy) - 1).getIdUczniowKlasy().get(j), uczniowieSzkoly.get(uczniowieSzkolyWgKlas.get(podajIdKlasy(nazwaKlasy) - 1).getIdUczniowKlasy().get(j) - 1).getImie(), uczniowieSzkoly.get(uczniowieSzkolyWgKlas.get(podajIdKlasy(nazwaKlasy) - 1).getIdUczniowKlasy().get(j) - 1).getNazwisko()));
        }
        return listaUczniKlasy;
    }

    private static ArrayList<String> listaNazwZajecKlasy(String nazwaKlasy) {
        ArrayList<String> listaNazwZajecKlasy = new ArrayList<String>();
        for (int l = 0; l < zajecia.size(); l++) {
            if (podajIdKlasy(nazwaKlasy) == zajecia.get(l).getIdKlasa()) {
                listaNazwZajecKlasy.add(przedmiotyNauczania.get(zajecia.get(l).getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania());
            }
        }
        return listaNazwZajecKlasy;
    }

    private static ArrayList<Zajecie> listaZajecKlasy(String nazwaKlasy) {
        ArrayList<Zajecie> listaZajecKlasy = new ArrayList<Zajecie>();
        for (Zajecie zajecie : zajecia) {
            if (podajIdKlasy(nazwaKlasy) == zajecie.getIdKlasa()) {
                listaZajecKlasy.add(zajecie);
            }
        }
        return listaZajecKlasy;
    }

    private static ArrayList<Integer> listaOcenUczniaZZajec(Integer idUcznia, Integer idZajecia) {
        ArrayList<Integer> listaOcenUczniaZZajec = new ArrayList<Integer>();
        for (OcenaUcznia ocenaUcznia : ocenyUczniow) {
            if (ocenaUcznia.getIdUcznia() == idUcznia && ocenaUcznia.getIdZajecia() == idZajecia) {
                listaOcenUczniaZZajec.add(ocenaUcznia.getOcenaZZajeciaUcznia());
            }
        }
        return listaOcenUczniaZZajec;
    }

    private static ArrayList<Zajecie> listaAlfabetZajecUczniaId(Integer idUcznia) {
        ArrayList<Zajecie> listaAlfabetZajecUcznia = new ArrayList<Zajecie>();
        ArrayList<String> listaAlfabetNazwPrzedmiotowNauczaniaUcznia = listaNazwZajecKlasy(podajNazweKlasyUczniaId(idUcznia));
        listaAlfabetNazwPrzedmiotowNauczaniaUcznia.sort(null);
        for (int i = 0; i < listaAlfabetNazwPrzedmiotowNauczaniaUcznia.size(); i++) {
            for (Zajecie z : listaZajecKlasy(podajNazweKlasyUczniaId(idUcznia))) {
                if ((podajIdPrzedmiotu(listaAlfabetNazwPrzedmiotowNauczaniaUcznia.get(i)) == z.getIdPrzedmiotNauczania()) && !listaAlfabetZajecUcznia.contains(z)) {
                    listaAlfabetZajecUcznia.add(z);
                }
            }
        }
        return listaAlfabetZajecUcznia;
    }

    private static ArrayList<Zajecie> listaZajecPrzedmiotu(String nazwaPrzedmiotu) {
        ArrayList<Zajecie> listaZajecPrzedmiotu = new ArrayList<Zajecie>();
        for (Zajecie z : zajecia) {
            if (podajIdPrzedmiotu(nazwaPrzedmiotu) == z.getIdPrzedmiotNauczania()) {
                listaZajecPrzedmiotu.add(z);
            }
        }
        return listaZajecPrzedmiotu;
    }

    private static ArrayList<String> listaNazwPrzedmiotowNauczycielaId(int idNauczyciela) {
        ArrayList<String> listaNazwPrzedmiotowNauczyciela = new ArrayList<String>();
        for (Zajecie z : zajecia) {
            if (z.getIdNauczyciela() == idNauczyciela) {
                if (!listaNazwPrzedmiotowNauczyciela.contains(przedmiotyNauczania.get(z.getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania())) {
                    listaNazwPrzedmiotowNauczyciela.add(przedmiotyNauczania.get(z.getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania());
                }
            }
        }
        listaNazwPrzedmiotowNauczyciela.sort(null);
        return listaNazwPrzedmiotowNauczyciela;
    }

    private static ArrayList<Nauczyciel> listaNauczycieliPrzedmiotuId(int idPrzedmiotu) {
        ArrayList<Nauczyciel> listaNauczycieliPrzedmiotu = new ArrayList<Nauczyciel>();
        ArrayList<String> listaNazwNauczycieliPrzedmiotu = new ArrayList<String>();
        ArrayList<Integer> listaIdNauczycieliPrzedmiotu = new ArrayList<Integer>();
        for (Zajecie z : zajecia) {
            if (z.getIdPrzedmiotNauczania() == idPrzedmiotu) {
                if (!listaIdNauczycieliPrzedmiotu.contains(z.getIdNauczyciela())) {
                    listaIdNauczycieliPrzedmiotu.add(z.getIdNauczyciela());
                    listaNauczycieliPrzedmiotu.add(new Nauczyciel(z.getIdNauczyciela(), nauczyciele.get(z.getIdNauczyciela() - 1).getImie(), nauczyciele.get(z.getIdNauczyciela() - 1).getNazwisko()));
                }
            }
        }
        return listaNauczycieliPrzedmiotu;
    }

    private static ArrayList<String> listaNazwKlasIdPrzedmiotuNauczyciela(int idPrzedmiot, int idNauczyciel) {
        ArrayList<String> listaNazwKlasIdPrzedmiotuNauczyciela = new ArrayList<String>();
        for (Zajecie z : zajecia) {
            if (z.getIdPrzedmiotNauczania() == idPrzedmiot && z.getIdNauczyciela() == idNauczyciel) {
                if (!listaNazwKlasIdPrzedmiotuNauczyciela.contains(klasy.get(z.getIdKlasa() - 1).getNazwaKlasy())) {
                    listaNazwKlasIdPrzedmiotuNauczyciela.add(klasy.get(z.getIdKlasa() - 1).getNazwaKlasy());
                }
            }
        }
        return listaNazwKlasIdPrzedmiotuNauczyciela;
    }

    private static ArrayList<Zajecie> listaZajecNauczyciela(String imieINazwiskoNauczyciela) {
        ArrayList<Zajecie> listaZajecNauczyciela = new ArrayList<Zajecie>();
        for (int l = 0; l < zajecia.size(); l++) {
            if (zajecia.get(l).getIdNauczyciela() == podajIdNauczyciela(imieINazwiskoNauczyciela)) {
                listaZajecNauczyciela.add(zajecia.get(l));
            }
        }
        return listaZajecNauczyciela;
    }

    private static ArrayList<Uczen> listaAlfabetUczniowSzkoly() {
        ArrayList<Uczen> listaAlfabetUczniowSzkoly = new ArrayList<Uczen>(uczniowieSzkoly);
        ArrayList<String> listaAlfabetNazwUczniowSzkoly = new ArrayList<String>();
        ArrayList<String> listaNazwiskUczniowSzkoly = new ArrayList<String>();
        for (Uczen u : uczniowieSzkoly) {
            listaNazwiskUczniowSzkoly.add(u.getNazwisko() + " " + u.getImie());
        }
        listaNazwiskUczniowSzkoly.sort(null);
        for (int i = 0; i < listaNazwiskUczniowSzkoly.size(); i++) {
            String imieUcznia = listaNazwiskUczniowSzkoly.get(i).substring(listaNazwiskUczniowSzkoly.get(i).indexOf(" ") + 1);
            String nazwiskoUcznia = listaNazwiskUczniowSzkoly.get(i).substring(0, listaNazwiskUczniowSzkoly.get(i).indexOf(" "));
            listaAlfabetNazwUczniowSzkoly.add(imieUcznia + " " + nazwiskoUcznia);
        }
        listaAlfabetNazwUczniowSzkoly.sort(null);
        for (Uczen u : uczniowieSzkoly) {
            for (int i = 0; i < listaAlfabetNazwUczniowSzkoly.size(); i++) {
                String imieUcznia = listaAlfabetNazwUczniowSzkoly.get(i).substring(0, listaAlfabetNazwUczniowSzkoly.get(i).indexOf(" "));
                String nazwiskoUcznia = listaAlfabetNazwUczniowSzkoly.get(i).substring(listaAlfabetNazwUczniowSzkoly.get(i).indexOf(" ") + 1);
                if (u.getImie().equals(imieUcznia) && u.getNazwisko().equals(nazwiskoUcznia)) {
                    listaAlfabetUczniowSzkoly.set(i, new Uczen(u.getidUcznia(), u.getImie(), u.getNazwisko()));
                }
            }
        }
        return listaAlfabetUczniowSzkoly;
    }

    private static int podajIdNauczyciela(String imieINazwiskoOsoby) {
        int idNauczyla = 0;
        String imieNauczyciela = imieINazwiskoOsoby.substring(0, imieINazwiskoOsoby.indexOf(" "));
        String nazwiskoNauczyciela = imieINazwiskoOsoby.substring(imieINazwiskoOsoby.indexOf(" ") + 1);
        for (Nauczyciel n : nauczyciele) {
            if (n.getImie().equals(imieNauczyciela) && n.getNazwisko().equals(nazwiskoNauczyciela)) {
                idNauczyla = n.getIdNauczyciela();
            }
        }
        return idNauczyla;
    }

    private static Float sredniaOcenKlasyZZajec(Integer idZajecia) {
        Float sredniaOcenKlasyZZajec = 0f;
        Float sumaOcen = 0f;
        int liczbaOcen = 0;
        for (OcenaUcznia o : ocenyUczniow) {
            if (o.getIdZajecia() == idZajecia) {
                sumaOcen = sumaOcen + o.getOcenaZZajeciaUcznia();
                liczbaOcen++;
            }
        }
        if (liczbaOcen > 0) {
            sredniaOcenKlasyZZajec = sumaOcen / liczbaOcen;
        }
        return sredniaOcenKlasyZZajec;
    }

    private static Float sredniaOcenKlasy(String nazwaKlasy) {
        Float sredniaOcenKlasy = 0f;
        Float sumaOcenKlasy = 0f;
        int liczbaOcen = 0;
        for (Uczen u : listaUczniKlasy(nazwaKlasy)) {
            for (Zajecie z : listaZajecKlasy(nazwaKlasy)) {
                for (int ocena : listaOcenUczniaZZajec(u.getidUcznia(), z.getIdZajecie())) {
                    sumaOcenKlasy = sumaOcenKlasy + ocena;
                    liczbaOcen++;
                }
            }
        }
        if (liczbaOcen > 0) {
            sredniaOcenKlasy = sumaOcenKlasy / liczbaOcen;
        }
        return sredniaOcenKlasy;
    }

    private static Float sredniaOcenNauczycielazPrzedmiotu(int idNauczyciela, int idPrzedmiotu) {
        Float sredniaOcenNauczycielazPrzedmiotu = 0f;
        Float sumaOcen = 0f;
        int liczbaOcen = 0;
        for (Zajecie z : zajecia) {
            if (z.getIdNauczyciela() == idNauczyciela && z.getIdPrzedmiotNauczania() == idPrzedmiotu) {
                for (OcenaUcznia o : ocenyUczniow) {
                    if (o.getIdZajecia() == z.getIdZajecie()) {
                        sumaOcen = sumaOcen + o.getOcenaZZajeciaUcznia();
                        liczbaOcen++;
                    }
                }
            }
        }
        if (liczbaOcen > 0) {
            sredniaOcenNauczycielazPrzedmiotu = sumaOcen / liczbaOcen;
        }
        return sredniaOcenNauczycielazPrzedmiotu;
    }

    private static Float sredniaWszystkichOcenUczniaId(Integer idUcznia) {
        Float sredniaWszystkichOcenUcznia = -1f;
        Float sumaOcenUcznia = 0f;
        int liczbaOcen = 0;
        for (OcenaUcznia o : ocenyUczniow) {
            if (o.getIdUcznia() == idUcznia) {
                sumaOcenUcznia = sumaOcenUcznia + o.getOcenaZZajeciaUcznia();
                liczbaOcen++;
            }
        }
        if (liczbaOcen > 0) {
            sredniaWszystkichOcenUcznia = sumaOcenUcznia / liczbaOcen;
        }
        return sredniaWszystkichOcenUcznia;
    }

    private static Float sredniaOcenUczniaZZajecId(Integer idUcznia, Integer idZajecia) {
        Float sredniaOcenUczniaZZajec = -1f;
        Float sumaOcenUcznia = 0f;
        int liczbaOcen = 0;
        for (OcenaUcznia o : ocenyUczniow) {
            if (o.getIdUcznia() == idUcznia && o.getIdZajecia() == idZajecia) {
                sumaOcenUcznia = sumaOcenUcznia + o.getOcenaZZajeciaUcznia();
                liczbaOcen++;
            }
        }
        if (liczbaOcen > 0) {
            sredniaOcenUczniaZZajec = sumaOcenUcznia / liczbaOcen;
        }
        return sredniaOcenUczniaZZajec;
    }

    private static String dodaj(String nazwa) {
        sc = new Scanner(System.in);
        do {
            System.out.print(nazwa + ": ");
            nazwaWprowadzona = sc.nextLine();
        } while (czyJestPustyString(nazwaWprowadzona));
        return nazwaWprowadzona;
    }

    private static String dodajImie() {
        sc = new Scanner(System.in);
        do {
            System.out.print("imię: ");
            imie = sc.nextLine();
        } while (czyJestPustyString(imie));
        return imie;
    }

    private static String dodajNazwisko() {
        sc = new Scanner(System.in);
        do {
            System.out.print("nazwisko: ");
            nazwisko = sc.nextLine();
        } while (czyJestPustyString(nazwisko));
        return nazwisko;
    }

    private static String dodajNazwePrzedmiotu() {
        sc = new Scanner(System.in);
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

    private static void zapiszDoPlikuUczniowieSzkoly() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkoly.txt"));
            out.writeObject(uczniowieSzkoly);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static void zapiszDoPlikuUczniowieSzkolyWgKlas() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkolyWgKlas.txt"));
            out.writeObject(uczniowieSzkolyWgKlas);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static void zapiszDoPlikuOcenyUczniow() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\OcenyUczniow.txt"));
            out.writeObject(ocenyUczniow);
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe + "Error!");
        }
    }

    private static void zapiszDoPlikuKlasy() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\Klasy.txt"));
            out.writeObject(klasy);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static void zapiszDoPlikuNauczyciele() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\Nauczyciele.txt"));
            out.writeObject(nauczyciele);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static void zapiszDoPlikuPrzedmiotyNauczania() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\PrzedmiotyNauczania.txt"));
            out.writeObject(przedmiotyNauczania);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static void zapiszDoPlikuZajecia() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Java\\Próba1\\ZapisanePliki\\Zajecia.txt"));
            out.writeObject(zajecia);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        }
    }

    private static ArrayList<Uczen> odczytZPlikuUczniowieSzkoly() {
        ArrayList<Uczen> uczniowieSzkolyOdczyt = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkoly.txt"));
            uczniowieSzkolyOdczyt = (ArrayList<Uczen>) in.readObject();
            System.out.println("Oczyt pliku UczniowieSzkoly.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return uczniowieSzkolyOdczyt;
    }

    private static ArrayList<UczniowieKlasy> odczytZPlikuUczniowieSzkolyWgKlas() {
        ArrayList<UczniowieKlasy> UczniowieSzkolyWgKlasOdczyt = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\UczniowieSzkolyWgKlas.txt"));
            UczniowieSzkolyWgKlasOdczyt = (ArrayList<UczniowieKlasy>) in.readObject();
            System.out.println("Oczyt pliku UczniowieSzkolyWgKlas.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return UczniowieSzkolyWgKlasOdczyt;
    }

    private static ArrayList<Klasa> odczytZPlikuKlasy() {
        ArrayList<Klasa> klasyOdczyt = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\Klasy.txt"));
            klasyOdczyt = (ArrayList<Klasa>) in.readObject();
            System.out.println("Oczyt pliku Klasy.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return klasyOdczyt;
    }

    private static ArrayList<Nauczyciel> odczytZPlikuNauczyciele() {
        ArrayList<Nauczyciel> odczytZPlikuNauczyciele = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\Nauczyciele.txt"));
            odczytZPlikuNauczyciele = (ArrayList<Nauczyciel>) in.readObject();
            System.out.println("Oczyt pliku Nauczyciele.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return odczytZPlikuNauczyciele;
    }

    private static ArrayList<PrzedmiotNauczania> odczytZPlikuPrzedmiotyNauczania() {
        ArrayList<PrzedmiotNauczania> odczytZPlikuPrzedmiotyNauczania = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\PrzedmiotyNauczania.txt"));
            odczytZPlikuPrzedmiotyNauczania = (ArrayList<PrzedmiotNauczania>) in.readObject();
            System.out.println("Oczyt pliku PrzedmiotyNauczania.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return odczytZPlikuPrzedmiotyNauczania;
    }

    private static ArrayList<Zajecie> odczytZPlikuZajecia() {
        ArrayList<Zajecie> odczytZPlikuZajecia = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\Zajecia.txt"));
            odczytZPlikuZajecia = (ArrayList<Zajecie>) in.readObject();
            System.out.println("Oczyt pliku Zajecia.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println("Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return odczytZPlikuZajecia;
    }

    private static ArrayList<OcenaUcznia> odczytZPlikuOcenyUczniow() {
        ArrayList<OcenaUcznia> odczytZPlikuOcenyUczniow = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Java\\Próba1\\ZapisanePliki\\OcenyUczniow.txt"));
            odczytZPlikuOcenyUczniow = (ArrayList<OcenaUcznia>) in.readObject();
            System.out.println("Oczyt pliku OcenyUczniow.txt");
            in.close();
        } catch (IOException ioe) {
            System.out.println(ioe + "Error!");
        } catch (ClassNotFoundException exc) {
            System.out.println("Nie można odnaleźć klasy obiektu");
            System.exit(1);
        }
        return odczytZPlikuOcenyUczniow;
    }

    private static void zestawienieSzkola() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private static void mainFrame() {
        int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
        int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
        mainFrame = new JFrame(NAZWA_SZKOLY);
        setDefaultLookAndFeelDecorated(true);
        mainFrame.setBounds(0, 0, widthScreen, heightScreen);
        panelMain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMain.setAlignmentY(JPanel.TOP_ALIGNMENT);
        scrolPanelMain = new JScrollPane();
        scrolPanelMain.setViewportView(panelMain);
        panelMain.setBackground(Color.BLUE);
        mainFrame.setJMenuBar(createMenuGlowne());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static JButton ButtonActPerfor(String name) {
        button = new JButton(name);
        button.setPreferredSize(new Dimension(400, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMain.setBackground(Color.RED);
                //tabbedPanel.getSelectedComponent().setBackground(Color.BLUE);
            }
        });
        return button;
    }

    private static Integer newGradeFor(String NamePupilSubject) {
        int newGrade = -1;
        JOptionPane inputNewGradeFor = new JOptionPane();
        inputNewGradeFor.setWantsInput(true);
        Integer[] possibleValues = {0, 1, 2, 3, 4, 5, 6};
        Object selectedGrade = JOptionPane.showInputDialog(null,
                "Wybierz ocenę z listy:", NamePupilSubject,
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedGrade != null) {
            newGrade = selectedGrade.hashCode();
        }
        return newGrade;
    }

    private static Nauczyciel wybierzNowyNauczycielZajeciaPrzedmiotu(String nazwaPrzedmiotu) {
        Nauczyciel nowyNauczycielZajecia = new Nauczyciel();
        ArrayList<String> imieINazwiskaNauczycieli = new ArrayList<String>();
        for (Nauczyciel n : nauczyciele) {
            imieINazwiskaNauczycieli.add(n.getImie() + " " + n.getNazwisko());
        }
        imieINazwiskaNauczycieli.sort(null);
        JOptionPane inputNewTeacherforNewActivity = new JOptionPane();
        inputNewTeacherforNewActivity.setWantsInput(true);
        String[] possibleValues = new String[nauczyciele.size()];
        for (int i = 0; i < imieINazwiskaNauczycieli.size(); i++) {
            possibleValues[i] = imieINazwiskaNauczycieli.get(i);
        }
        Object selectedTeacher = JOptionPane.showInputDialog(null,
                "Wybierz nauczyciela dla nowego zajęcia " + nazwaPrzedmiotu + ":", "Lista nauczycieli szkoły",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        System.out.println(selectedTeacher);
        if (selectedTeacher != null) {
            nowyNauczycielZajecia = new Nauczyciel(podajIdNauczyciela(selectedTeacher.toString()), nauczyciele.get(podajIdNauczyciela(selectedTeacher.toString()) - 1).getImie(), nauczyciele.get(podajIdNauczyciela(selectedTeacher.toString()) - 1).getNazwisko());
        }
        return nowyNauczycielZajecia;
    }

    private static String wybierzNowaKlasaZajeciaNauczycielaId(int idNauczyciel) {
        String nowaKlasaZajecia = "";
        JOptionPane inputnowaKlasaNowegoZajecia = new JOptionPane();
        inputnowaKlasaNowegoZajecia.setWantsInput(true);
        String[] possibleValues = new String[klasy.size()];
        for (int i = 0; i < klasy.size(); i++) {
            possibleValues[i] = klasy.get(i).getNazwaKlasy();
        }
        Object selectedClass = JOptionPane.showInputDialog(null,
                "Wybierz klasę dla nauczyciela " + nauczyciele.get(idNauczyciel - 1).getImie() + " " + nauczyciele.get(idNauczyciel - 1).getNazwisko() + ":", "Lista klas szkoły",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedClass != null) {
            nowaKlasaZajecia = selectedClass.toString();
        }
        return nowaKlasaZajecia;
    }

    private static PrzedmiotNauczania wybierzNowyPrzedmiotZajeciaNauczycielaId(int idNauczyciel) {
        PrzedmiotNauczania nowyPrzedmiotZajecia = new PrzedmiotNauczania(0, "");
        ArrayList<String> nazwyPrzedmiotow = new ArrayList<String>();
        for (PrzedmiotNauczania p : przedmiotyNauczania) {
            nazwyPrzedmiotow.add(p.getPrzedmiotNauczania());
        }
        nazwyPrzedmiotow.sort(null);
        JOptionPane inputnowyPrzedmiotZajecia = new JOptionPane();
        inputnowyPrzedmiotZajecia.setWantsInput(true);
        String[] possibleValues = new String[nazwyPrzedmiotow.size()];
        for (int i = 0; i < nazwyPrzedmiotow.size(); i++) {
            possibleValues[i] = nazwyPrzedmiotow.get(i);
        }
        Object selectedSubject = JOptionPane.showInputDialog(null,
                "Wybierz przedmiot dla nauczyciela " + nauczyciele.get(idNauczyciel - 1).getImie() + " " + nauczyciele.get(idNauczyciel - 1).getNazwisko() + ":", "Lista przedmiotów nauczania",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, null);
        if (selectedSubject != null) {
            nowyPrzedmiotZajecia = new PrzedmiotNauczania(podajIdPrzedmiotu(selectedSubject.toString()), selectedSubject.toString());
        }
        return nowyPrzedmiotZajecia;
    }

    private static PrzedmiotNauczania wybierzNowyPrzedmiot() {
        PrzedmiotNauczania nowyPrzedmiot = new PrzedmiotNauczania(0, "");
        ArrayList<String> nazwyDostepnychPrzedmiotow = new ArrayList<String>();
        ArrayList<String> nazwyDostepnychPrzedmiotowDoWyboru = new ArrayList<String>();
        for (PrzedmiotNauczania pn : przedmiotyNauczania) {
            nazwyDostepnychPrzedmiotow.add(pn.getPrzedmiotNauczania());
        }
        for (String NP : NAZWY_PRZEDMIOTOW) {
            if (!nazwyDostepnychPrzedmiotow.contains(NP)) {
                nazwyDostepnychPrzedmiotowDoWyboru.add(NP);
            }

        }
        nazwyDostepnychPrzedmiotowDoWyboru.sort(null);
        if (nazwyDostepnychPrzedmiotowDoWyboru.size() > 0) {
            JOptionPane inputNowyPrzedmiot = new JOptionPane();
            inputNowyPrzedmiot.setWantsInput(true);
            String[] possibleValues = new String[nazwyDostepnychPrzedmiotowDoWyboru.size()];
            for (int i = 0; i < nazwyDostepnychPrzedmiotowDoWyboru.size(); i++) {
                possibleValues[i] = nazwyDostepnychPrzedmiotowDoWyboru.get(i);
            }
            Object selectedSubject = JOptionPane.showInputDialog(null,
                    "Wybierz dostępny wolny przedmiot nauczania ", "Lista przedmiotów nauczania",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, null);
            if (selectedSubject != null) {
                nowyPrzedmiot = new PrzedmiotNauczania(przedmiotyNauczania.size() + 1, selectedSubject.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Brak przedmiotów do wyboru - wszystkie juz są wybrane", "Uwaga!", JOptionPane.ERROR_MESSAGE);
        }
        return nowyPrzedmiot;
    }

    private static Nauczyciel dodajNowegoNauczyciela() {
        Nauczyciel nowyNauczyciel = new Nauczyciel();
        String imieNauczyciela = JOptionPane.showInputDialog("Wprowadź imię nauczyciela");
        if (imieNauczyciela != null) {
            String nazwiskoNauczyciela = JOptionPane.showInputDialog("Wprowadź nazwisko nauczyciela");
            if (nazwiskoNauczyciela != null) {
                nowyNauczyciel = new Nauczyciel(nauczyciele.size() + 1, imieNauczyciela, nazwiskoNauczyciela);
            }
        }
        return nowyNauczyciel;
    }

    private static JComboBox ComboBoxActPerfor() {
        JComboBox box1 = new JComboBox();
        box1.addItem("pos.1");
        box1.addItem("pos.2");
        box1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(((JComboBox) e.getSource()).getSelectedItem());
            }
        });
        return box1;
    }

    private static JMenuBar createMenuGlowne() {
        LinkedHashMap<String, String[]> rows1 = new LinkedHashMap<>();
        LinkedHashMap<String, String[]> rowsMain = new LinkedHashMap<>();
        String menuA = "KLASY";
        String A1 = "A1";
        String A2 = "A2";
        String A3 = "A3";
        String A4 = "A4";
        String menuB = "PRZEDMIOTY";
        String B1 = "B1";
        String B2 = "B2";
        String B3 = "B3";
        String B4 = "B4";
        String menuC = "NAUCZYCIELE";
        String C1 = "C1";
        String C2 = "C2";
        String C3 = "C3";
        String C4 = "C4";
        String menuD = "UCZNIOWIE";
        String D1 = "D1";
        String D2 = "D2";
        String D3 = "D3";
        String D4 = "D4";
        String menuE = "ZESTAWIENIE INFORMACJI O SZKOLE";
        String E1 = "E1";
        String E2 = "E2";
        String E3 = "E3";
        String E4 = "E4";
        String menuF = "F";
        String F1 = "F1";
        String F2 = "F2";
        String F3 = "F3";
        String F4 = "F4";
        //rowsMain.put(menuA, new String[]{A1, A2, A3, A4});
        String[] menuANazwyTab = new String[klasy.size() + 1];
        for (Klasa klasa : klasy) {
            menuANazwyTab[klasy.indexOf(klasa)] = "Klasa nr " + klasa.getNazwaKlasy();
        }
        menuANazwyTab[menuANazwyTab.length - 1] = "Spis ";
        ArrayList<String> nazwyPrzedmiotow = new ArrayList<String>();
        String[] menuBNazwyTab = new String[przedmiotyNauczania.size() + 1];
        for (PrzedmiotNauczania przedmiotNauczania : przedmiotyNauczania) {
            nazwyPrzedmiotow.add(przedmiotNauczania.getPrzedmiotNauczania());
        }
        nazwyPrzedmiotow.sort(null);
        for (int i = 0; i < nazwyPrzedmiotow.size(); i++) {
            menuBNazwyTab[i] = nazwyPrzedmiotow.get(i);
        }
        menuBNazwyTab[menuBNazwyTab.length - 1] = "Spis ";
        ArrayList<String> nazwyNauczycieli = new ArrayList<String>();
        String[] menuCNazwyTab = new String[nauczyciele.size() + 1];
        for (Nauczyciel nauczyciel : nauczyciele) {
            nazwyNauczycieli.add(nauczyciel.getImie() + " " + nauczyciel.getNazwisko());
        }
        nazwyNauczycieli.sort(null);
        for (int i = 0; i < nazwyNauczycieli.size(); i++) {
            menuCNazwyTab[i] = nazwyNauczycieli.get(i);
        }
        menuCNazwyTab[menuCNazwyTab.length - 1] = "Spis ";
        String[] menuDNazwyTab = new String[1];
        menuDNazwyTab[0] = "Alfabetyczny spis uczniów";
        rowsMain.put(menuA, menuANazwyTab);
        rowsMain.put(menuB, menuBNazwyTab);
        rowsMain.put(menuC, menuCNazwyTab);
        rowsMain.put(menuD, menuDNazwyTab);
        rowsMain.put(menuE, new String[]{E1, E2, E3, E4});
        rowsMain.put(menuF, new String[]{F1, F2, F3, F4});
        //rows1.put(A1, new String[]{"A1_1", "A1_2", "A1_3"});
        //rows1.put(A2, new String[]{"A2_1", "A2_2", "A2_3"});
        //rows1.put(A3, new String[]{"A3_1", "A3_2", "A3_3"});
        //rows1.put(B1, new String[]{"B1_1", "B1_2", "B1_3"});
        //rows1.put(B2, new String[]{"B2_1", "B2_2", "B2_3"});
        menuBar = new JMenuBar();
        for (String m : rowsMain.keySet()) {
            JMenu menu = new JMenu(" " + m + " ");
            menuBar.add(menu);
            for (String m1 : rowsMain.get(m)) {
                if (rows1.containsKey(m1)) {
                    JMenu subMenu1 = new JMenu(m1);
                    menu.add(subMenu1);
                    for (String m2 : rows1.get(m1)) {
                        JMenuItem subMenu2 = new JMenuItem(m2);
                        subMenu1.add(subMenu2);
                        subMenu1.addSeparator();
                        subMenu2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            }
                        });
                    }
                } else {
                    JMenuItem subMenu11 = new JMenuItem(m1);
                    menu.add(subMenu11);
                    subMenu11.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelMain.removeAll();
                            box2 = new Box(BoxLayout.X_AXIS);
                            box3 = new Box(BoxLayout.Y_AXIS);
                            selectedColumn = -1;
                            selectedRow = -1;
                            //headTable2 = new JLabel("");
                            if (m.equals(menuA)) {
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box2.add(box3);
                                scrolPanelMain.setViewportView(createPanelKlasy(m1.replaceAll("Klasa nr ", "")));
                            }
                            if (m.equals(menuB)) {
                                scrolPanelMain.setViewportView(createPanelPrzedmioty(m1));
                            }
                            if (m.equals(menuC)) {
                                scrolPanelMain.setViewportView(createPanelNauczyciele(m1));
                            }
                            if (m.equals(menuD)) {
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box3.add(new JLabel(" "));
                                box2.add(box3);
                                scrolPanelMain.setViewportView(createPanelUczniowie(m1));
                            }
                            if (!m.equals(menuA) && !m.equals(menuB) && !m.equals(menuC) && !m.equals(menuD)) {
                                panelMain.add(ButtonActPerfor("RED " + m + " / " + m1));
                                panelMain.setBackground(Color.BLACK);
                                panelMain.setBackground(Color.BLUE);
                                scrolPanelMain.setViewportView(panelMain);
                            }
                        }
                    });
                }
                menu.addSeparator();
            }
        }
        mainFrame.getContentPane().add(scrolPanelMain, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        return menuBar;
    }

    private static JPanel createPanelKlasy(String nazwaKlasy) {
        panelKlasy.removeAll();
        if (!nazwaKlasy.equals("Spis ")) {
            String[] columnNames = new String[3 + listaNazwZajecKlasy(nazwaKlasy).size()];
            columnNames[0] = "ID ucznia";
            columnNames[1] = "Imię ucznia";
            columnNames[2] = "Nazwisko ucznia";
            for (int j = 0; j < listaNazwZajecKlasy(nazwaKlasy).size(); j++) {
                columnNames[3 + j] = listaNazwZajecKlasy(nazwaKlasy).get(j) + " " + nauczyciele.get(listaZajecKlasy(nazwaKlasy).get(j).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(listaZajecKlasy(nazwaKlasy).get(j).getIdNauczyciela() - 1).getNazwisko();
            }

            JTable tableSpisOcenKlasy = new JTable(new TableModel() {
                @Override
                public int getRowCount() {
                    return listaUczniKlasy(nazwaKlasy).size() + 1;
                }

                @Override
                public int getColumnCount() {
                    return 3 + listaNazwZajecKlasy(nazwaKlasy).size();
                }

                @Override
                public String getColumnName(int columnIndex) {
                    return columnNames[columnIndex];
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object newValueCell = null;
                    if (rowIndex < getRowCount() - 1) {
                        if (columnIndex == 0) {
                            newValueCell = listaUczniKlasy(nazwaKlasy).get(rowIndex).getidUcznia();
                        }
                        if (columnIndex == 1) {
                            newValueCell = listaUczniKlasy(nazwaKlasy).get(rowIndex).getImie();
                        }
                        if (columnIndex == 2) {
                            newValueCell = listaUczniKlasy(nazwaKlasy).get(rowIndex).getNazwisko();
                        }
                        if (columnIndex > 2) {
                            newValueCell = listaOcenUczniaZZajec(listaUczniKlasy(nazwaKlasy).get(rowIndex).getidUcznia(), listaZajecKlasy(nazwaKlasy).get(columnIndex - 3).getIdZajecie());
                        }
                    }
                    if (rowIndex == getRowCount() - 1 && columnIndex == 2) {
                        newValueCell = "średnia ocen:";
                    }
                    if (rowIndex == getRowCount() - 1 && columnIndex > 2) {
                        if (sredniaOcenKlasyZZajec(listaZajecKlasy(nazwaKlasy).get(columnIndex - 3).getIdZajecie()) > 0) {
                            newValueCell = sredniaOcenKlasyZZajec(listaZajecKlasy(nazwaKlasy).get(columnIndex - 3).getIdZajecie());
                        }
                    }
                    return newValueCell;
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                }

                @Override
                public void addTableModelListener(TableModelListener l) {

                }

                @Override
                public void removeTableModelListener(TableModelListener l) {

                }
            });
            for (int i = 0; i < tableSpisOcenKlasy.getColumnCount(); i++) {
                tableSpisOcenKlasy.getColumn(columnNames[i]).setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JLabel newL = new JLabel();
                        String rowTemp = "";
                        if (tableSpisOcenKlasy.getValueAt(row, column) != null) {
                            rowTemp = tableSpisOcenKlasy.getValueAt(row, column).toString();
                            if (row == selectedRow && column == selectedColumn) {
                                newL.setText(rowTemp);
                                newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                newL.setForeground(Color.RED);
                                newL.setBackground(Color.yellow);
                                newL.setOpaque(true);
                            } else {
                                newL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                                if (row == tableSpisOcenKlasy.getRowCount() - 1) {
                                    newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                    newL.setBackground(Color.LIGHT_GRAY);
                                    newL.setOpaque(true);
                                }
                                if (column >= 0 && column <= 2) {
                                    newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                                }
                                newL.setText(rowTemp);
                            }
                        }
                        return newL;
                    }
                });
            }
            int totalHeaderTableWidth = 0;
            for (int i = 0; i < columnNames.length; i++) {
                JLabel label = new JLabel(columnNames[i]);
                tableSpisOcenKlasy.getColumn(columnNames[i]).setPreferredWidth(label.getPreferredSize().width + 10);
                totalHeaderTableWidth = totalHeaderTableWidth + label.getPreferredSize().width + 10;
            }
            tableSpisOcenKlasy.setRowHeight(20);

            JLabel headTable1 = new JLabel("KLASA nr " + nazwaKlasy + " - spis ocen");
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(totalHeaderTableWidth, (headTable1.getPreferredSize().height + tableSpisOcenKlasy.getTableHeader().getPreferredSize().height + tableSpisOcenKlasy.getPreferredSize().height) + box2.getPreferredSize().height));
            boxMain.add(headTable1);
            boxMain.add(tableSpisOcenKlasy.getTableHeader());
            boxMain.add(tableSpisOcenKlasy);
            boxMain.add(Box.createGlue());
            boxMain.add(box2);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelKlasy.add(scrollBox);
            panelKlasy.setSize(widthScreen, heightScreen);
            panelKlasy.setBackground(Color.BLUE);

            tableSpisOcenKlasy.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    box2 = new Box(BoxLayout.X_AXIS);
                    box3 = new Box(BoxLayout.Y_AXIS);
                    if (tableSpisOcenKlasy.getSelectedRow() >= 0 && tableSpisOcenKlasy.getSelectedRow() < listaUczniKlasy(nazwaKlasy).size() && tableSpisOcenKlasy.getSelectedColumn() > 2 && tableSpisOcenKlasy.getSelectedColumn() < listaZajecKlasy(nazwaKlasy).size() + 3) {
                        if (tableSpisOcenKlasy.getSelectedColumn() > -1 && tableSpisOcenKlasy.getSelectedRow() > -1) {
                            selectedColumn = tableSpisOcenKlasy.getSelectedColumn();
                            selectedRow = tableSpisOcenKlasy.getSelectedRow();
                        }
                        int idWybranyUczen = listaUczniKlasy(nazwaKlasy).get(tableSpisOcenKlasy.getSelectedRow()).getidUcznia();
                        int idWybraneZajecie = listaZajecKlasy(nazwaKlasy).get(tableSpisOcenKlasy.getSelectedColumn() - 3).getIdZajecie();
                        String imieWybranyUczen = uczniowieSzkoly.get(idWybranyUczen - 1).getImie();
                        String nazwiskoWybranyUczen = uczniowieSzkoly.get(idWybranyUczen - 1).getNazwisko();
                        String nazwaWybranyPrzedmiot = przedmiotyNauczania.get(zajecia.get(idWybraneZajecie - 1).getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania();
                        String namePupilSubject = idWybranyUczen + ") " + imieWybranyUczen + " " + nazwiskoWybranyUczen + " - " + nazwaWybranyPrzedmiot + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getNazwisko();

                        box3.add(new JLabel(idWybranyUczen + ") " + imieWybranyUczen + " " + nazwiskoWybranyUczen)).setFont(new Font(null, Font.BOLD, 16));
                        box3.add(new JLabel(nazwaWybranyPrzedmiot + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getNazwisko()));
                        box3.add(new JLabel("Oceny: " + listaOcenUczniaZZajec(idWybranyUczen, idWybraneZajecie)));
                        box2.add(box3);
                        box2.add(Box.createGlue());
                        JButton buttonNewGrade = new JButton("Dodaj ocenę");
                        box2.add(buttonNewGrade);
                        buttonNewGrade.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int newGrade = newGradeFor(namePupilSubject);
                                if (newGrade != -1) {
                                    ocenyUczniow.add(new OcenaUcznia(idWybranyUczen, idWybraneZajecie, newGrade, LocalDateTime.now()));
                                    zapiszDoPlikuOcenyUczniow();
                                    box2 = new Box(BoxLayout.X_AXIS);
                                    box3 = new Box(BoxLayout.Y_AXIS);
                                    box3.add(new JLabel(idWybranyUczen + ") " + imieWybranyUczen + " " + nazwiskoWybranyUczen)).setFont(new Font(null, Font.BOLD, 16));
                                    box3.add(new JLabel(nazwaWybranyPrzedmiot + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getNazwisko()));
                                    box3.add(new JLabel("Oceny: " + listaOcenUczniaZZajec(idWybranyUczen, idWybraneZajecie)));
                                    box2.add(box3);
                                    box2.add(Box.createGlue());
                                    box2.add(buttonNewGrade);
                                    createPanelKlasy(nazwaKlasy);
                                }
                            }
                        });
                    } else {
                        box3.add(new JLabel(" "));
                        box3.add(new JLabel(" "));
                        box3.add(new JLabel(" "));
                        box2.add(box3);
                        selectedColumn = -1;
                        selectedRow = -1;
                    }
                    createPanelKlasy(nazwaKlasy);
                }
            });
        } else {
            String[] columnNames = new String[3];
            columnNames[0] = "Klasa Nr";
            columnNames[1] = "Przedmioty";
            columnNames[2] = "Śrenia ocen";

            JTable tableSpisOcen = new JTable(new Object[klasy.size()][3], columnNames);
            for (int lo = 0; lo <= klasy.size() - 1; lo++) {
                tableSpisOcen.setValueAt(klasy.get(lo).getNazwaKlasy(), lo, 0);
                tableSpisOcen.setValueAt(listaNazwZajecKlasy(klasy.get(lo).getNazwaKlasy()), lo, 1);
                tableSpisOcen.setValueAt(sredniaOcenKlasy(klasy.get(lo).getNazwaKlasy()), lo, 2);
            }
            tableSpisOcen.setAutoCreateRowSorter(false);
            //tableSpisOcen.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableSpisOcen.getColumn(columnNames[0]).setPreferredWidth(2);
            tableSpisOcen.getColumn(columnNames[1]).setPreferredWidth(800);
            tableSpisOcen.getColumn(columnNames[2]).setPreferredWidth(10);
            JLabel headTable1 = new JLabel("Spis klas " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(1000, (tableSpisOcen.getRowCount() + 6) * tableSpisOcen.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(tableSpisOcen.getTableHeader());
            boxMain.add(tableSpisOcen);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelKlasy.add(scrollBox);
            panelKlasy.setSize(widthScreen, heightScreen);
            panelKlasy.setBackground(Color.BLUE);
        }
        return panelKlasy;
    }

    private static JPanel createPanelPrzedmioty(String nazwaPrzedmiotu) {
        panelPrzedmioty.removeAll();
        if (!nazwaPrzedmiotu.equals("Spis ")) {
            String[] columnNames = new String[6];
            columnNames[0] = "ID nauczyciela";
            columnNames[1] = "Imię";
            columnNames[2] = "Nazwisko";
            columnNames[3] = "Id zajęcia";
            columnNames[4] = "Zajęcie w klasie";
            columnNames[5] = "Średnia ocen";
            JTable table1 = new JTable(new Object[listaZajecPrzedmiotu(nazwaPrzedmiotu).size()][6], columnNames);
            for (int lo = 0; lo <= listaZajecPrzedmiotu(nazwaPrzedmiotu).size() - 1; lo++) {
                table1.setValueAt(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdNauczyciela(), lo, 0);
                table1.setValueAt(nauczyciele.get(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdNauczyciela() - 1).getImie(), lo, 1);
                table1.setValueAt(nauczyciele.get(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdNauczyciela() - 1).getNazwisko(), lo, 2);
                table1.setValueAt(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdZajecie(), lo, 3);
                table1.setValueAt(klasy.get(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdKlasa() - 1).getNazwaKlasy(), lo, 4);
                table1.setValueAt(sredniaOcenKlasyZZajec(listaZajecPrzedmiotu(nazwaPrzedmiotu).get(lo).getIdZajecie()), lo, 5);
            }
            table1.setSelectionForeground(Color.BLUE);
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Lista zajęć przedmiotu: " + nazwaPrzedmiotu);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(6 * 100, (listaZajecPrzedmiotu(nazwaPrzedmiotu).size() + 5) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewAactivity = new JButton("Dodaj nowe zajęcie przedmiotu " + nazwaPrzedmiotu);
            box2.add(Box.createGlue());
            box2.add(buttonNewAactivity);
            boxMain.add(box2);
            buttonNewAactivity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Nauczyciel nowyNauczyciel = wybierzNowyNauczycielZajeciaPrzedmiotu(nazwaPrzedmiotu);
                    if (nowyNauczyciel.getIdNauczyciela() > 0) {
                        String nowaKlasa = wybierzNowaKlasaZajeciaNauczycielaId(nowyNauczyciel.getIdNauczyciela());
                        if (!nowaKlasa.equals("")) {
                            zajecia.add(new Zajecie(zajecia.size() + 1, podajIdPrzedmiotu(nazwaPrzedmiotu), nowyNauczyciel.getIdNauczyciela(), podajIdKlasy(nowaKlasa), LocalDateTime.now()));
                            zapiszDoPlikuZajecia();
                            createPanelPrzedmioty(nazwaPrzedmiotu);
                        }
                    }
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelPrzedmioty.add(scrollBox);
            panelPrzedmioty.setSize(widthScreen, heightScreen);
            panelPrzedmioty.setBackground(Color.BLUE);
        } else {
            String[] columnNames = new String[4];
            columnNames[0] = "Przedmiot";
            columnNames[1] = "Nauczyciel";
            columnNames[2] = "Klasa";
            columnNames[3] = "Średnia ocen";
            ArrayList<String> nazwyPrzemiotowNauczania = new ArrayList<String>();
            ArrayList<String> nazwyPrzedmiotowWszystkichNauczycieli = new ArrayList<String>();
            ArrayList<String> nauczycieleWszystkichPrzedmiotow = new ArrayList<String>();
            ArrayList<ArrayList<String>> nazwyKlasWszystkichNauczycieli = new ArrayList<ArrayList<String>>();
            for (PrzedmiotNauczania pn : przedmiotyNauczania) {
                nazwyPrzemiotowNauczania.add(pn.getPrzedmiotNauczania());
            }
            nazwyPrzemiotowNauczania.sort(null);
            for (String npn : nazwyPrzemiotowNauczania) {
                if (listaNauczycieliPrzedmiotuId(podajIdPrzedmiotu(npn)).size() > 0) {
                    for (Nauczyciel n : listaNauczycieliPrzedmiotuId(podajIdPrzedmiotu(npn))) {
                        nazwyPrzedmiotowWszystkichNauczycieli.add(npn);
                        nauczycieleWszystkichPrzedmiotow.add(n.getImie() + " " + n.getNazwisko());
                        nazwyKlasWszystkichNauczycieli.add(listaNazwKlasIdPrzedmiotuNauczyciela(podajIdPrzedmiotu(npn), n.getIdNauczyciela()));
                    }
                } else {
                    nazwyPrzedmiotowWszystkichNauczycieli.add(npn);
                    nauczycieleWszystkichPrzedmiotow.add("");
                    nazwyKlasWszystkichNauczycieli.add(new ArrayList<String>());
                }
            }
            JTable table1 = new JTable(new Object[nazwyPrzedmiotowWszystkichNauczycieli.size()][4], columnNames);
            for (int lo = 0; lo <= nazwyPrzedmiotowWszystkichNauczycieli.size() - 1; lo++) {
                table1.setValueAt(nazwyPrzedmiotowWszystkichNauczycieli.get(lo), lo, 0);
                table1.setValueAt(nauczycieleWszystkichPrzedmiotow.get(lo), lo, 1);
                table1.setValueAt(nazwyKlasWszystkichNauczycieli.get(lo), lo, 2);
                if (!nauczycieleWszystkichPrzedmiotow.get(lo).equals("")) {
                    table1.setValueAt(sredniaOcenNauczycielazPrzedmiotu(podajIdNauczyciela(nauczycieleWszystkichPrzedmiotow.get(lo)), podajIdPrzedmiotu(nazwyPrzedmiotowWszystkichNauczycieli.get(lo))), lo, 3);
                }
            }
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Spis przedmiotów nauczania " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(500, (table1.getRowCount() + 6) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewSubjetTeaching = new JButton("Dodaj nowy przedmiot");
            box2.add(Box.createGlue());
            box2.add(buttonNewSubjetTeaching);
            boxMain.add(box2);
            buttonNewSubjetTeaching.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PrzedmiotNauczania nowyPrzedmiot = wybierzNowyPrzedmiot();
                    if (nowyPrzedmiot.getIdPrzedmiotNauczania() > 0) {
                        przedmiotyNauczania.add(nowyPrzedmiot);
                        zapiszDoPlikuPrzedmiotyNauczania();
                        mainFrame.remove(menuBar);
                        mainFrame.setJMenuBar(createMenuGlowne());
                        mainFrame.setVisible(true);
                        createPanelPrzedmioty(nazwaPrzedmiotu);
                    }
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelPrzedmioty.add(scrollBox);
            panelPrzedmioty.setSize(widthScreen, heightScreen);
            panelPrzedmioty.setBackground(Color.BLUE);
        }
        return panelPrzedmioty;
    }

    private static JPanel createPanelNauczyciele(String imieINazwiskoNauczyciela) {
        panelNauczyciele.removeAll();
        if (!imieINazwiskoNauczyciela.equals("Spis ")) {
            String[] columnNames = new String[4];
            columnNames[0] = "ID zajęcia";
            columnNames[1] = "Nazwa przedmiotu";
            columnNames[2] = "Klasa";
            columnNames[3] = "Średnia ocen";
            JTable table1 = new JTable(new Object[listaZajecNauczyciela(imieINazwiskoNauczyciela).size()][4], columnNames);
            for (int lo = 0; lo <= listaZajecNauczyciela(imieINazwiskoNauczyciela).size() - 1; lo++) {
                table1.setValueAt(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdZajecie(), lo, 0);
                table1.setValueAt(przedmiotyNauczania.get(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania(), lo, 1);
                table1.setValueAt(klasy.get(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdKlasa() - 1).getNazwaKlasy(), lo, 2);
                table1.setValueAt(sredniaOcenKlasyZZajec(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdZajecie()), lo, 3);
            }
            table1.setSelectionForeground(Color.BLUE);
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Lista zajęć nauczyciela: " + imieINazwiskoNauczyciela);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(6 * 100, (listaZajecNauczyciela(imieINazwiskoNauczyciela).size() + 5) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);
            JButton buttonNewAactivity = new JButton("Dodaj nowe zajęcie nauczyciela " + imieINazwiskoNauczyciela);
            box2.add(Box.createGlue());
            box2.add(buttonNewAactivity);
            boxMain.add(box2);
            buttonNewAactivity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PrzedmiotNauczania nowyPrzedmiot = wybierzNowyPrzedmiotZajeciaNauczycielaId(podajIdNauczyciela(imieINazwiskoNauczyciela));
                    if (nowyPrzedmiot.getIdPrzedmiotNauczania() > 0) {
                        String nowaKlasa = wybierzNowaKlasaZajeciaNauczycielaId(podajIdNauczyciela(imieINazwiskoNauczyciela));
                        if (!nowaKlasa.equals("")) {
                            zajecia.add(new Zajecie(zajecia.size() + 1, nowyPrzedmiot.getIdPrzedmiotNauczania(), podajIdNauczyciela(imieINazwiskoNauczyciela), podajIdKlasy(nowaKlasa), LocalDateTime.now()));
                            zapiszDoPlikuZajecia();
                            createPanelNauczyciele(imieINazwiskoNauczyciela);
                        }
                    }
                }
            });
            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelNauczyciele.add(scrollBox);
            panelNauczyciele.setSize(widthScreen, heightScreen);
            panelNauczyciele.setBackground(Color.BLUE);
        } else {
            String[] columnNames = new String[4];
            columnNames[0] = "Nauczyciel";
            columnNames[1] = "Przedmiot";
            columnNames[2] = "Klasa";
            columnNames[3] = "Średnia ocen";
            ArrayList<String> nauczycieleSzkoly = new ArrayList<String>();
            ArrayList<String> nauczycieleWszystkichPrzedmiotow = new ArrayList<String>();
            ArrayList<String> nazwyPrzemiotowNauczania = new ArrayList<String>();
            ArrayList<ArrayList<String>> nazwyKlasWszystkichNauczycieli = new ArrayList<ArrayList<String>>();
            for (Nauczyciel n : nauczyciele) {
                nauczycieleSzkoly.add(n.getImie() + " " + n.getNazwisko());
            }
            nauczycieleSzkoly.sort(null);
            for (String ns : nauczycieleSzkoly) {
                if (listaNazwPrzedmiotowNauczycielaId(podajIdNauczyciela(ns)).size() > 0) {
                    for (String lp : listaNazwPrzedmiotowNauczycielaId(podajIdNauczyciela(ns))) {
                        nauczycieleWszystkichPrzedmiotow.add(ns);
                        nazwyPrzemiotowNauczania.add(lp);
                        nazwyKlasWszystkichNauczycieli.add(listaNazwKlasIdPrzedmiotuNauczyciela(podajIdPrzedmiotu(lp), podajIdNauczyciela(ns)));
                    }
                } else {
                    nauczycieleWszystkichPrzedmiotow.add(ns);
                    nazwyPrzemiotowNauczania.add("");
                    nazwyKlasWszystkichNauczycieli.add(new ArrayList<String>());
                }
            }
            JTable table1 = new JTable(new Object[nauczycieleWszystkichPrzedmiotow.size()][4], columnNames);
            for (int lo = 0; lo <= nauczycieleWszystkichPrzedmiotow.size() - 1; lo++) {
                table1.setValueAt(nauczycieleWszystkichPrzedmiotow.get(lo), lo, 0);
                table1.setValueAt(nazwyPrzemiotowNauczania.get(lo), lo, 1);
                table1.setValueAt(nazwyKlasWszystkichNauczycieli.get(lo), lo, 2);
                if (!nazwyPrzemiotowNauczania.get(lo).equals("")) {
                    table1.setValueAt(sredniaOcenNauczycielazPrzedmiotu(podajIdNauczyciela(nauczycieleWszystkichPrzedmiotow.get(lo)), podajIdPrzedmiotu(nazwyPrzemiotowNauczania.get(lo))), lo, 3);
                }
            }
            table1.setAutoCreateRowSorter(false);
            JLabel headTable1 = new JLabel("Spis nauczycieli " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            boxMain.setPreferredSize(new Dimension(500, (table1.getRowCount() + 6) * table1.getRowHeight()));
            boxMain.add(headTable1);
            boxMain.add(table1.getTableHeader());
            boxMain.add(table1);

            JButton buttonNewTeacher = new JButton("Dodaj nowego nauczyciela");
            box2.add(Box.createGlue());
            box2.add(buttonNewTeacher);
            boxMain.add(box2);
            buttonNewTeacher.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Nauczyciel nowyNauczyciel = dodajNowegoNauczyciela();
                    if (nowyNauczyciel.getIdNauczyciela() > 0) {
                        nauczyciele.add(nowyNauczyciel);
                        zapiszDoPlikuNauczyciele();
                    }
                    mainFrame.remove(menuBar);
                    mainFrame.setJMenuBar(createMenuGlowne());
                    mainFrame.setVisible(true);
                    createPanelNauczyciele(imieINazwiskoNauczyciela);
                }
            });

            JScrollPane scrollBox = new JScrollPane(boxMain);
            panelNauczyciele.add(scrollBox);
            panelNauczyciele.setSize(widthScreen, heightScreen);
            panelNauczyciele.setBackground(Color.BLUE);
        }
        return panelNauczyciele;
    }

    private static JPanel createPanelUczniowie(String spisUczniow) {
        panelUczniowie.removeAll();
        panelUczniowie.setAlignmentY(JPanel.TOP_ALIGNMENT);
        if (spisUczniow.equals("Alfabetyczny spis uczniów")) {
            String[] columnNames = new String[5];
            columnNames[0] = "Imię ucznia";
            columnNames[1] = "Nazwisko ucznia";
            columnNames[2] = "ID ucznia";
            columnNames[3] = "Klasa";
            columnNames[4] = "Średnia ocen";
            JTable tableSpisUcziow = new JTable(new TableModel() {
                @Override
                public int getRowCount() {
                    return uczniowieSzkoly.size();
                }

                @Override
                public int getColumnCount() {
                    return columnNames.length;
                }

                @Override
                public String getColumnName(int columnIndex) {
                    return columnNames[columnIndex];
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object newValueCell = null;
                    if (rowIndex > -1) {
                        if (columnIndex == 0) {
                            newValueCell = uczniowieSzkolySpisAlfabet.get(rowIndex).getImie();
                        }
                        if (columnIndex == 1) {
                            newValueCell = uczniowieSzkolySpisAlfabet.get(rowIndex).getNazwisko();
                        }
                        if (columnIndex == 2) {
                            newValueCell = uczniowieSzkolySpisAlfabet.get(rowIndex).getidUcznia();
                        }
                        if (columnIndex == 3) {
                            newValueCell = podajNazweKlasyUczniaId(uczniowieSzkolySpisAlfabet.get(rowIndex).getidUcznia());
                        }
                        if (columnIndex == 4) {
                            if (sredniaWszystkichOcenUczniaId(uczniowieSzkolySpisAlfabet.get(rowIndex).getidUcznia()) > -1) {
                                newValueCell = sredniaWszystkichOcenUczniaId(uczniowieSzkolySpisAlfabet.get(rowIndex).getidUcznia());
                            }
                        }
                    }
                    return newValueCell;
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                }

                @Override
                public void addTableModelListener(TableModelListener l) {

                }

                @Override
                public void removeTableModelListener(TableModelListener l) {

                }
            });

            int totalHeaderTableWidth = 0;
            for (int i = 0; i < columnNames.length; i++) {
                JLabel label = new JLabel(columnNames[i]);
                tableSpisUcziow.getColumn(columnNames[i]).setPreferredWidth(label.getPreferredSize().width + 10);
                totalHeaderTableWidth = totalHeaderTableWidth + label.getPreferredSize().width + 10;
            }
            tableSpisUcziow.setRowHeight(20);

            JLabel headTable1 = new JLabel("Alfabetyczny spis uczniów " + NAZWA_SZKOLY);
            headTable1.setFont(new Font(null, Font.BOLD, 14));
            headTable1.setAlignmentX(CENTER_ALIGNMENT);
            Box boxMain = new Box(BoxLayout.Y_AXIS);
            //boxMain.setPreferredSize(new Dimension(totalHeaderTableWidth, (Toolkit.getDefaultToolkit().getScreenSize().height) - 100));
            boxMain.add(headTable1);
            boxMain.add(tableSpisUcziow.getTableHeader());
            boxMain.add(tableSpisUcziow);
            boxMain.add(Box.createGlue());
            boxMain.add(box2);
            JScrollPane scrollBox = new JScrollPane(boxMain);
            scrollBox.setPreferredSize(new Dimension(totalHeaderTableWidth + 20, (Toolkit.getDefaultToolkit().getScreenSize().height) - 100));
            panelUczniowie.add(scrollBox);
            panelUczniowie.setSize(widthScreen, heightScreen);
            panelUczniowie.setBackground(Color.BLUE);
            JPanel paneSub = new JPanel();
            JFrame frameSub = new JFrame();
            frameSub.setVisible(false);
            frameSub.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameSub.setFocusableWindowState(false);
            for (int i = 0; i < tableSpisUcziow.getColumnCount(); i++) {
                tableSpisUcziow.getColumn(columnNames[i]).setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                        JLabel newL = new JLabel();
                        String rowTemp = "";
                        if (tableSpisUcziow.getValueAt(row, column) != null) {
                            rowTemp = tableSpisUcziow.getValueAt(row, column).toString();
                        }

                        if (isSelected) {
                            newL.setText(rowTemp);
                            newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                            newL.setForeground(Color.RED);
                            newL.setBackground(Color.yellow);
                            newL.setOpaque(true);
                        } else {
                            newL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                            if (column >= 0 && column <= 1) {
                                newL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                            }
                            newL.setText(rowTemp);
                        }
                        frameSub.setBounds(scrollBox.getLocationOnScreen().x + scrollBox.getWidth() + 2, scrollBox.getLocationOnScreen().y, 600, 300);
                        if (isSelected && selectedRow != tableSpisUcziow.getSelectedRow()) {
                            frameSub.getContentPane().removeAll();
                            paneSub.removeAll();
                            frameSub.setTitle(tableSpisUcziow.getValueAt(tableSpisUcziow.getSelectedRow(), 0).toString() + " " + tableSpisUcziow.getValueAt(tableSpisUcziow.getSelectedRow(), 1).toString());
                            JScrollPane scrolDziennikUcznia = dzienniczekUczniaId(uczniowieSzkolySpisAlfabet.get(tableSpisUcziow.getSelectedRow()).getidUcznia());
                            paneSub.add(scrolDziennikUcznia);
                            paneSub.setVisible(true);
                            frameSub.getContentPane().add(scrolDziennikUcznia);
                            selectedRow = tableSpisUcziow.getSelectedRow();
                        }
                        frameSub.revalidate();
                        frameSub.setVisible(true);
                        if (!frameSub.isDisplayable() && tableSpisUcziow.getSelectedRow() > -1) {
                            selectedColumn = -1;
                            selectedRow = -1;
                            System.out.println("111");
                            panelUczniowie.removeAll();
                            frameSub.revalidate();
                            createPanelUczniowie(spisUczniow);
                        }
                        return newL;
                    }
                });
            }
        }
        return panelUczniowie;
    }

    private static JScrollPane dzienniczekUczniaId(int idUcznia) {
        String[] columnNames = new String[6];
        columnNames[0] = "Nazwa przedmiotu";
        columnNames[1] = "Imię i mazwisko nauczyciela";
        columnNames[2] = "ID zajędia";
        columnNames[3] = "Klasa nr";
        columnNames[4] = "Oceny z przedmiotu";
        columnNames[5] = "Średnia ocen z przedmiotu";

        JTable dzienniczekUcznia = new JTable(new TableModel() {
            @Override
            public int getRowCount() {
                return listaAlfabetZajecUczniaId(idUcznia).size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (getValueAt(0, columnIndex) != null) {
                    return getValueAt(0, columnIndex).getClass();
                } else {
                    return Object.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object newValueCell = null;
                if (columnIndex == 0) {
                    newValueCell = przedmiotyNauczania.get(listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania();
                }
                if (columnIndex == 1) {
                    newValueCell = nauczyciele.get(listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdNauczyciela() - 1).getNazwisko();
                }
                if (columnIndex == 2) {
                    newValueCell = listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdZajecie();
                }
                if (columnIndex == 3) {
                    newValueCell = podajNazweKlasyUczniaId(idUcznia);
                }
                if (columnIndex == 4) {
                    newValueCell = listaOcenUczniaZZajec(idUcznia, listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdZajecie());
                }
                if (columnIndex == 5) {
                    if (sredniaOcenUczniaZZajecId(idUcznia, listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdZajecie()) > -1) {
                        newValueCell = sredniaOcenUczniaZZajecId(idUcznia, listaAlfabetZajecUczniaId(idUcznia).get(rowIndex).getIdZajecie());
                    }
                }
                return newValueCell;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        });
        return new JScrollPane(dzienniczekUcznia);
    }
}