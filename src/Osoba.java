import java.io.Serializable;
import java.util.Scanner;

public class Osoba implements Serializable {
    private String imie;
    private String nazwisko;

    public Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Osoba() {
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String dodajImie() {
        Scanner sc = new Scanner(System.in);
        System.out.print("imię: ");
        return sc.nextLine();
    }

    public String dodajNazwisko() {
        Scanner sc = new Scanner(System.in);
        System.out.print("nazwisko: ");
        return sc.nextLine();
    }
}