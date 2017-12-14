package bean;

/**
 * Created by chaoqunhuang on 12/13/17.
 */

public class Location {
    private double latitude;
    private double longitude;
    private int crimeCases;


    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude, int crimeCases) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.crimeCases = crimeCases;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCrimeCases() {
        return crimeCases;
    }

    public void setCrimeCases(int crimeCases) {
        this.crimeCases = crimeCases;
    }
}
