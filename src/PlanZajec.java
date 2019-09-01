import java.io.Serializable;

public class PlanZajec implements Serializable {
    private int idPlanZajec;
    private int idZajecie;
    private String dzienTygodniaZajec;
    private int godzRozpZajec;

    public PlanZajec(int idPlanZajec, int idZajecie, String dzienTygodniaZajec, int godzRozpZajec) {
        this.idPlanZajec = idPlanZajec;
        this.idZajecie = idZajecie;
        this.dzienTygodniaZajec = dzienTygodniaZajec;
        this.godzRozpZajec = godzRozpZajec;
    }

    public PlanZajec(){
    }

    public PlanZajec(int idPlanZajec){
        this.idPlanZajec = idPlanZajec;
    }

    public int getIdPlanZajec() {
        return idPlanZajec;
    }

    public int getIdZajecie() {
        return idZajecie;
    }

    public String getDzienTygodniaZajec() {
        return dzienTygodniaZajec;
    }

    public int getGodzRozpZajec() {
        return godzRozpZajec;
    }
}