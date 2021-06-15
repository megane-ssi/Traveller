package um5.ssi.traininggps;

public class WayPoint {
    private int id;
    private String title;
    private String date;
    private int time;
    private double lon;
    private double lat;

    // Constructors
    public WayPoint(int id, String title, String date, int time, double lat, double lon) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.lon = lon;
        this.lat = lat;
    }

    public WayPoint(String title, String date, int time, double latitude, double longtitude) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.lon = longtitude;
        this.lat = latitude;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
