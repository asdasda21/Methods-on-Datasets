public class Dato {
    private final String station;
    private final String name;
    private final String date;
    private final Object prcp;
    private final Object tavg;
    private final Object tmax;
    private final Object tmin;

    Dato (String station, String name, String date, Object prcp, Object tavg, Object tmax, Object tmin) {
        this.station = station;
        this.name = name;
        this.date = date;
        this.prcp = prcp;
        this.tavg = tavg;
        this.tmax = tmax;
        this.tmin = tmin;
    }

    public String getStation() {
        return this.station;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public Object getPrcp() {
        return this.prcp;
    }

    public Object getTavg() {
        return this.tavg;
    }

    public Object getTmax() {
        return this.tmax;
    }

    public Object getTmin() {
        return this.tmin;
    }

    public void imprimirDato() {
        System.out.printf("%-15s", this.getStation());
        System.out.printf("%-22s", this.getName());
        System.out.printf("%-15s", this.getDate());
        System.out.printf("%-10s", this.getPrcp());
        System.out.printf("%-10s", this.getTavg());
        System.out.printf("%-10s", this.getTmax());
        System.out.printf("%-10s", this.getTmin());
        System.out.println();
    }
}