package bean;

/**
 * Created by Hunter6 on 12/13/17.
 */

public class PlaceInfo {
    private double latitude;
    private double longitude;
    private String markName;
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
