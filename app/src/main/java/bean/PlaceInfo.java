package bean;

/**
 * Created by Hunter6 on 12/13/17.
 */

public class PlaceInfo {
    private double latitude;
    private double longitude;
    private String markName;
    private int cc100;
    private int cc200;
    private int cc300;
    private double safetyScore;

    public int getCc100() {
        return cc100;
    }

    public void setCc100(int cc100) {
        this.cc100 = cc100;
    }

    public int getCc200() {
        return cc200;
    }

    public void setCc200(int cc200) {
        this.cc200 = cc200;
    }

    public int getCc300() {
        return cc300;
    }

    public void setCc300(int cc300) {
        this.cc300 = cc300;
    }

    public double getSafetyScore() {
        return safetyScore;
    }

    public void setSafetyScore(double safetyScore) {
        this.safetyScore = safetyScore;
    }

    public PlaceInfo(double latitude, double longitude, String markName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.markName = markName;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getMarkName(){
        return markName;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", markName='" + markName + '\'' +
                '}';
    }
}
