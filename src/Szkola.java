import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
    private static ArrayList<Uczen> uczniowieSzkoly = new ArrayList<>();
    private static ArrayList<Klasa> klasy = new ArrayList<>();
    private static ArrayList<UczniowieKlasy> uczniowieSzkolyWgKlas = new ArrayList<>();
    private static ArrayList<Nauczyciel> nauczyciele = new ArrayList<>();
    private static ArrayList<PrzedmiotNauczania> przedmiotyNauczania = new ArrayList<>();
    private static ArrayList<Zajecie> zajecia = new ArrayList<>();
    private static ArrayList<OcenaUcznia> ocenyUczniow = new ArrayList<>();
    private static boolean tak = false;
    private static Scanner sc = new Scanner(System.in);
    private static String imie = "";
    private static String nazwisko = "";
    private static String przedmiot = "";
    private static String nazwaWprowadzona = "";
    private static String tabbed0 = "TabbedPanel0";
    private static String tabbed1 = "TabbedPanel1";
    private static String tabbed2 = "TabbedPanel2";
    private static String tabbed3 = "TabbedPanel3";
    private static String tabbed4 = "TabbedPanel4";
    private static JButton button;
    private static JScrollPane scrolPanelMain;
    private static JPanel panelMain;
    private static JPanel panel0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelMenuA = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelMenuB = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panelMenuC = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panel1 = new JPanel();
    private static JPanel panel2 = new JPanel();
    private static JPanel panel3 = new JPanel();
    private static JPanel panel4 = new JPanel();
    private static JFrame mainFrame;
    private static JMenuBar menuBar;
    private static JTabbedPane tabbedPanel = new JTabbedPane();
    private static JMenuBar menuBar1;
    private static JLabel headTable2 = new JLabel(" ");
    private static JTable table1 = new JTable();
    private static Box box1 = new Box(BoxLayout.X_AXIS);
    private static Box box2 = new Box(BoxLayout.X_AXIS);
    private static Box box3 = new Box(BoxLayout.Y_AXIS);

    public static void main(String[] args) {
        System.out.println("Nazwa Szkoły: " + NAZWA_SZKOLY);
        uczniowieSzkoly = odczytZPlikuUczniowieSzkoly();
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
        for (int l = 0; l < zajecia.size(); l++) {
            if (podajIdKlasy(nazwaKlasy) == zajecia.get(l).getIdKlasa()) {
                listaZajecKlasy.add(zajecia.get(l));
            }
        }
        return listaZajecKlasy;
    }

    private static ArrayList<Integer> listaOcenUczniaZZajec(Integer idUcznia, Integer idZajecia) {
        ArrayList<Integer> listaOcenUczniaZZajec = new ArrayList<Integer>();
        for (int l = 0; l < ocenyUczniow.size(); l++) {
            if (ocenyUczniow.get(l).getIdUcznia() == idUcznia && ocenyUczniow.get(l).getIdZajecia() == idZajecia) {
                listaOcenUczniaZZajec.add(ocenyUczniow.get(l).getOcenaZZajeciaUcznia());
            }
        }
        return listaOcenUczniaZZajec;
    }

    private static ArrayList<Zajecie> listaZajecPrzedmiotu(String nazwaPrzedmiotu) {
        ArrayList<Zajecie> listaZajecPrzedmiotu = new ArrayList<Zajecie>();
        for (int l = 0; l < nauczyciele.size(); l++) {
            for (Zajecie z : zajecia) {
                if (podajIdPrzedmiotu(nazwaPrzedmiotu) == z.getIdPrzedmiotNauczania() && nauczyciele.get(l).getIdNauczyciela() == z.getIdNauczyciela()) {
                    listaZajecPrzedmiotu.add(z);
                }
            }
        }
        return listaZajecPrzedmiotu;
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
            if (o.getIdZajecia() == idZajecia && o.getOcenaZZajeciaUcznia() > 0) {
                sumaOcen = sumaOcen + o.getOcenaZZajeciaUcznia();
                liczbaOcen++;
                sredniaOcenKlasyZZajec = sumaOcen / liczbaOcen;
            }
        }
        return sredniaOcenKlasyZZajec;
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
        //mainFrame.setUndecorated(true);
        //mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        mainFrame.setBounds(0, 0, widthScreen, heightScreen);
        panelMain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scrolPanelMain = new JScrollPane();
        scrolPanelMain.setViewportView(panelMain);
        panelMain.setBackground(Color.BLUE);
        //panelMain.setSize(10000, 10000);
        //panelMain.add(ButtonActPerfor("Nr1"));
        //panelMain.add(ComboBoxActPerfor());
        //mainFrame.getContentPane().add(panelMain);
        mainFrame.setJMenuBar(createMenuBar());
        //tabbedPanel.add(tabbed1, createPanel1());
        //tabbedPanel.add(tabbed2, createPanel2());
        //tabbedPanel.add(tabbed3, createPanel3());
        //tabbedPanel.add(tabbed4, createPanel4());
        //mainFrame.add(tabbedPanel);
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
        PrzedmiotNauczania nowyPrzedmiotZajecia = new PrzedmiotNauczania(0,"");
        ArrayList<String> nazwyPrzedmiotów = new ArrayList<String>();
        for (PrzedmiotNauczania p : przedmiotyNauczania) {
            nazwyPrzedmiotów.add(p.getPrzedmiotNauczania());
        }
        nazwyPrzedmiotów.sort(null);
        JOptionPane inputnowyPrzedmiotZajecia = new JOptionPane();
        inputnowyPrzedmiotZajecia.setWantsInput(true);
        String[] possibleValues = new String[nazwyPrzedmiotów.size()];
        for (int i = 0; i < nazwyPrzedmiotów.size(); i++) {
            possibleValues[i] = nazwyPrzedmiotów.get(i);
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

    private static JMenuBar createMenuBar() {
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
        String menuD = "D";
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
        String menuANazwyTab[] = new String[klasy.size()];
        String manuBNazwyTab[] = new String[przedmiotyNauczania.size()];
        String manuCNazwyTab[] = new String[nauczyciele.size()];
        for (Klasa klasa : klasy) {
            menuANazwyTab[klasy.indexOf(klasa)] = "Klasa nr " + klasa.getNazwaKlasy();
        }
        ArrayList<String> nazwyPrzedmiotow = new ArrayList<String>();
        for (PrzedmiotNauczania przedmiotNauczania : przedmiotyNauczania) {
            nazwyPrzedmiotow.add(przedmiotNauczania.getPrzedmiotNauczania());
        }
        nazwyPrzedmiotow.sort(null);
        for (int i = 0; i < nazwyPrzedmiotow.size(); i++) {
            manuBNazwyTab[i] = nazwyPrzedmiotow.get(i);
        }
        ArrayList<String> nazwyNauczycieli = new ArrayList<String>();
        for (Nauczyciel nauczyciel : nauczyciele) {
            nazwyNauczycieli.add(nauczyciel.getImie() + " " + nauczyciel.getNazwisko());
        }
        nazwyNauczycieli.sort(null);
        for (int i = 0; i < nazwyNauczycieli.size(); i++) {
            manuCNazwyTab[i] = nazwyNauczycieli.get(i);
        }

        rowsMain.put(menuA, menuANazwyTab);
        rowsMain.put(menuB, manuBNazwyTab);
        rowsMain.put(menuC, manuCNazwyTab);
        rowsMain.put(menuD, new String[]{D1, D2, D3, D4});
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
                            headTable2 = new JLabel("");
                            if (m.equals(menuA)) {
                                scrolPanelMain.setViewportView(createPanelMenuA(m1.replaceAll("Klasa nr ", "")));
                            }
                            if (m.equals(menuB)) {
                                scrolPanelMain.setViewportView(createPanelMenuB(m1));
                            }
                            if (m.equals(menuC)) {
                                scrolPanelMain.setViewportView(createPanelMenuC(m1));
                            }

                            if (!m.equals(menuA) && !m.equals(menuB) && !m.equals(menuC)) {
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

    private static JPanel createPanelMenuA(String nazwaKlasy) {
        panelMenuA.removeAll();
        String[] columnNames = new String[3 + listaNazwZajecKlasy(nazwaKlasy).size()];
        columnNames[0] = "ID ucznia";
        columnNames[1] = "Imię ucznia";
        columnNames[2] = "Nazwisko ucznia";
        for (int j = 0; j < listaNazwZajecKlasy(nazwaKlasy).size(); j++) {
            columnNames[3 + j] = listaNazwZajecKlasy(nazwaKlasy).get(j) + " " + nauczyciele.get(listaZajecKlasy(nazwaKlasy).get(j).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(listaZajecKlasy(nazwaKlasy).get(j).getIdNauczyciela() - 1).getNazwisko();
        }
        table1 = new JTable(new Object[listaUczniKlasy(nazwaKlasy).size() + 1][3 + listaZajecKlasy(nazwaKlasy).size()], columnNames);
        for (int lo = 0; lo <= listaUczniKlasy(nazwaKlasy).size() - 1; lo++) {
            table1.setValueAt(listaUczniKlasy(nazwaKlasy).get(lo).getidUcznia(), lo, 0);
            table1.setValueAt(listaUczniKlasy(nazwaKlasy).get(lo).getImie(), lo, 1);
            table1.setValueAt(listaUczniKlasy(nazwaKlasy).get(lo).getNazwisko(), lo, 2);
            for (int k = 0; k < listaZajecKlasy(nazwaKlasy).size(); k++) {
                table1.setValueAt(listaOcenUczniaZZajec(listaUczniKlasy(nazwaKlasy).get(lo).getidUcznia(), listaZajecKlasy(nazwaKlasy).get(k).getIdZajecie()), lo, 3 + k);
                if (sredniaOcenKlasyZZajec(listaZajecKlasy(nazwaKlasy).get(k).getIdZajecie()) > 0) {
                    table1.setValueAt(sredniaOcenKlasyZZajec(listaZajecKlasy(nazwaKlasy).get(k).getIdZajecie()), listaUczniKlasy(nazwaKlasy).size(), 3 + k);
                }
            }
            table1.setValueAt("średnia ocen:", uczniowieSzkolyWgKlas.get(podajIdKlasy(nazwaKlasy) - 1).getIdUczniowKlasy().size(), 2);
        }
        table1.setAutoCreateRowSorter(false);
        JLabel headTable1 = new JLabel("KLASA nr " + nazwaKlasy + " - spis ocen");
        headTable1.setFont(new Font(null, Font.BOLD, 14));
        headTable1.setAlignmentX(CENTER_ALIGNMENT);
        Box boxMain = new Box(BoxLayout.Y_AXIS);
        boxMain.setPreferredSize(new Dimension((3 + listaZajecKlasy(nazwaKlasy).size()) * 180, (table1.getRowCount() + 6) * table1.getRowHeight()));
        boxMain.add(headTable1);
        boxMain.add(table1.getTableHeader());
        boxMain.add(table1);
        boxMain.add(Box.createGlue());
        boxMain.add(box2);
        JScrollPane scrollBox = new JScrollPane(boxMain);
        panelMenuA.add(scrollBox);
        panelMenuA.setSize(widthScreen, heightScreen);
        panelMenuA.setBackground(Color.BLUE);
        box2 = new Box(BoxLayout.X_AXIS);
        box3 = new Box(BoxLayout.Y_AXIS);
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (table1.getSelectedRow() >= 0 && table1.getSelectedRow() < listaUczniKlasy(nazwaKlasy).size() && table1.getSelectedColumn() > 2 && table1.getSelectedColumn() < listaZajecKlasy(nazwaKlasy).size() + 3) {
                    int idWybranyUczen = listaUczniKlasy(nazwaKlasy).get(table1.getSelectedRow()).getidUcznia();
                    int idWybraneZajecie = listaZajecKlasy(nazwaKlasy).get(table1.getSelectedColumn() - 3).getIdZajecie();
                    String imieWybranyUczen = listaUczniKlasy(nazwaKlasy).get(table1.getSelectedRow()).getImie();
                    String nazwiskoWybranyUczen = listaUczniKlasy(nazwaKlasy).get(table1.getSelectedRow()).getNazwisko();
                    String nazwaWybraneZajecie = listaNazwZajecKlasy(nazwaKlasy).get(table1.getSelectedColumn() - 3);
                    headTable2 = new JLabel(idWybranyUczen + ") " + imieWybranyUczen + " " + nazwiskoWybranyUczen);
                    box2 = new Box(BoxLayout.X_AXIS);
                    box3 = new Box(BoxLayout.Y_AXIS);
                    box3.add(headTable2);
                    String namePupilSubject = idWybranyUczen + ") " + imieWybranyUczen + " " + nazwiskoWybranyUczen + " - " + nazwaWybraneZajecie + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getNazwisko();
                    headTable2 = new JLabel(nazwaWybraneZajecie + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getImie() + " " + nauczyciele.get(zajecia.get(idWybraneZajecie - 1).getIdNauczyciela() - 1).getNazwisko());
                    box3.add(headTable2);
                    headTable2 = new JLabel("Oceny: " + listaOcenUczniaZZajec(idWybranyUczen, idWybraneZajecie));
                    box3.add(headTable2);
                    //box1.add(new JTextField(1));
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
                                createPanelMenuA(nazwaKlasy);
                            }
                        }
                    });
                } else {
                    box2 = new Box(BoxLayout.X_AXIS);
                    box3 = new Box(BoxLayout.Y_AXIS);
                    headTable2 = new JLabel(" ");
                    box2.add(headTable2);
                }
                panelMenuA.removeAll();
                createPanelMenuA(nazwaKlasy);
            }
        });
        return panelMenuA;
    }

    private static JPanel createPanelMenuB(String nazwaPrzedmiotu) {
        panelMenuB.removeAll();
        String[] columnNames = new String[6];
        columnNames[0] = "ID nauczyciela";
        columnNames[1] = "Imię";
        columnNames[2] = "Nazwisko";
        columnNames[3] = "Id zajęcia";
        columnNames[4] = "Zajęcie w klasie";
        columnNames[5] = "Średnia ocen";

        table1 = new JTable(new Object[listaZajecPrzedmiotu(nazwaPrzedmiotu).size()][6], columnNames);
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
        box2 = new Box(BoxLayout.X_AXIS);
        box3 = new Box(BoxLayout.Y_AXIS);
        JLabel headTable1 = new JLabel("Lista zajęć przedmiotu: " + nazwaPrzedmiotu);
        headTable1.setFont(new Font(null, Font.BOLD, 14));
        headTable1.setAlignmentX(CENTER_ALIGNMENT);
        Box boxMain = new Box(BoxLayout.Y_AXIS);
        boxMain.setPreferredSize(new Dimension(6 * 100, (listaZajecPrzedmiotu(nazwaPrzedmiotu).size() + 5) * table1.getRowHeight()));
        boxMain.add(headTable1);
        boxMain.add(table1.getTableHeader());
        boxMain.add(table1);
        JButton buttonNewAactivity = new JButton("Dodaj nowe zajęcie");
        box2.add(Box.createGlue());
        box2.add(buttonNewAactivity);


        boxMain.add(box2);
        //boxMain.setAlignmentX(LEFT_ALIGNMENT);
        buttonNewAactivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nauczyciel nowyNauczyciel = wybierzNowyNauczycielZajeciaPrzedmiotu(nazwaPrzedmiotu);
                if (nowyNauczyciel.getIdNauczyciela() > 0) {
                    String nowaKlasa = wybierzNowaKlasaZajeciaNauczycielaId(nowyNauczyciel.getIdNauczyciela());
                    if (!nowaKlasa.equals("")) {
                        zajecia.add(new Zajecie(zajecia.size() + 1, podajIdPrzedmiotu(nazwaPrzedmiotu), nowyNauczyciel.getIdNauczyciela(), podajIdKlasy(nowaKlasa), LocalDateTime.now()));
                        zapiszDoPlikuZajecia();
                        createPanelMenuB(nazwaPrzedmiotu);
                    }
                }
            }
        });
        //boxMain.add(box2);
        JScrollPane scrollBox = new JScrollPane(boxMain);
        //scrollBox.setAlignmentX(RIGHT_ALIGNMENT);
        //JScrollPane scrollBox = new JScrollPane(table1);
        //scrollBox.setPreferredSize(new Dimension(6 * 100, (listaZajecPrzedmiotu(nazwaPrzedmiotu).size() + 3) * table1.getRowHeight()));
        panelMenuB.add(scrollBox);
        panelMenuB.setSize(widthScreen, heightScreen);
        panelMenuB.setBackground(Color.BLUE);
        box2 = new Box(BoxLayout.X_AXIS);
        box3 = new Box(BoxLayout.Y_AXIS);
        return panelMenuB;
    }

    private static JPanel createPanelMenuC(String imieINazwiskoNauczyciela) {
        panelMenuC.removeAll();
        String[] columnNames = new String[4];
        columnNames[0] = "ID zajęcia";
        columnNames[1] = "Nazwa przedmiotu";
        columnNames[2] = "Klasa";
        columnNames[3] = "Średnia ocen";


        table1 = new JTable(new Object[listaZajecNauczyciela(imieINazwiskoNauczyciela).size()][4], columnNames);
        for (int lo = 0; lo <= listaZajecNauczyciela(imieINazwiskoNauczyciela).size() - 1; lo++) {
            table1.setValueAt(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdZajecie(), lo, 0);
            table1.setValueAt(przedmiotyNauczania.get(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdPrzedmiotNauczania() - 1).getPrzedmiotNauczania(), lo, 1);
            table1.setValueAt(klasy.get(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdKlasa() - 1).getNazwaKlasy(), lo, 2);
            table1.setValueAt(sredniaOcenKlasyZZajec(listaZajecNauczyciela(imieINazwiskoNauczyciela).get(lo).getIdZajecie()), lo, 3);
        }
        table1.setSelectionForeground(Color.BLUE);
        table1.setAutoCreateRowSorter(false);
        box2 = new Box(BoxLayout.X_AXIS);
        box3 = new Box(BoxLayout.Y_AXIS);
        JLabel headTable1 = new JLabel("Lista zajęć nauczyciela: " + imieINazwiskoNauczyciela);
        headTable1.setFont(new Font(null, Font.BOLD, 14));
        headTable1.setAlignmentX(CENTER_ALIGNMENT);
        Box boxMain = new Box(BoxLayout.Y_AXIS);
        boxMain.setPreferredSize(new Dimension(6 * 100, (listaZajecNauczyciela(imieINazwiskoNauczyciela).size() + 5) * table1.getRowHeight()));
        boxMain.add(headTable1);
        boxMain.add(table1.getTableHeader());
        boxMain.add(table1);
        JButton buttonNewAactivity = new JButton("Dodaj nowe zajęcie");
        box2.add(Box.createGlue());
        box2.add(buttonNewAactivity);


        boxMain.add(box2);
        //boxMain.setAlignmentX(LEFT_ALIGNMENT);
        buttonNewAactivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrzedmiotNauczania nowyPrzedmiot = wybierzNowyPrzedmiotZajeciaNauczycielaId(podajIdNauczyciela(imieINazwiskoNauczyciela));
                if (nowyPrzedmiot.getIdPrzedmiotNauczania() > 0) {
                    String nowaKlasa = wybierzNowaKlasaZajeciaNauczycielaId(podajIdNauczyciela(imieINazwiskoNauczyciela));
                    if (!nowaKlasa.equals("")) {
                        zajecia.add(new Zajecie(zajecia.size() + 1, nowyPrzedmiot.getIdPrzedmiotNauczania(), podajIdNauczyciela(imieINazwiskoNauczyciela), podajIdKlasy(nowaKlasa), LocalDateTime.now()));
                        zapiszDoPlikuZajecia();
                        createPanelMenuC(imieINazwiskoNauczyciela);
                    }
                }
            }
        });
        //boxMain.add(box2);
        JScrollPane scrollBox = new JScrollPane(boxMain);
        //scrollBox.setAlignmentX(RIGHT_ALIGNMENT);
        //JScrollPane scrollBox = new JScrollPane(table1);
        //scrollBox.setPreferredSize(new Dimension(6 * 100, (listaZajecPrzedmiotu(nazwaPrzedmiotu).size() + 3) * table1.getRowHeight()));
        panelMenuC.add(scrollBox);
        panelMenuC.setSize(widthScreen, heightScreen);
        panelMenuC.setBackground(Color.BLUE);
        box2 = new Box(BoxLayout.X_AXIS);
        box3 = new Box(BoxLayout.Y_AXIS);
        return panelMenuC;
    }

    private static JPanel createPanel1() {
        panel1.add(ButtonActPerfor("Start"));
        return panel1;
    }

    private static JPanel createPanel2() {
        panel2.add(ButtonActPerfor("Panel2"));
        return panel2;
    }

    private static JPanel createPanel3() {

        return panel3;
    }

    private static JPanel createPanel4() {

        return panel4;
    }
}