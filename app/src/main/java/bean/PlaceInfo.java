package bean;

/**
 * Created by Hunter6 on 12/13/17.
 */

public class PlaceInfo {
    private double latitude;
    private double longtitude;
    private String markName;
    public PlaceInfo(double latitude, double longtitude, String markName){
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.markName = markName;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongtitude(){
        return longtitude;
    }

    public String getMarkName(){
        return markName;
    }
}
