package request;

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=museum&key=AIzaSyAA5FDptklMXePKTEmd6m7xUu4_xg8QKqM
/**
 * Created by Hunter6 on 12/13/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import bean.PlaceInfo;

public class GetGooglePlaceTask extends GetUrlContentTask {

    private static final String LOG_TAG = "CrimeEvader";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch";
    private static final String RADIUS = "500";
    private static final String TYPE_SEARCH = "museum";
    private static final String API_KEY = "AIzaSyAA5FDptklMXePKTEmd6m7xUu4_xg8QKqM";
    private static final String OUT_JSON = "/json";

    protected ArrayList<PlaceInfo> getAroundPlace(double latitude, double longtitude){
        ArrayList<PlaceInfo> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(OUT_JSON);
            sb.append("?location="+latitude+","+longtitude);
            sb.append("&radius="+RADIUS);
            sb.append("&type="+TYPE_SEARCH);
            sb.append("&key=" + API_KEY);

            Log.i(LOG_TAG, sb.toString());
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            Log.i(LOG_TAG,jsonResults.toString());
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray resultJsonArray = jsonObj.getJSONArray("results");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<PlaceInfo>(resultJsonArray.length());
            for (int i = 0; i < resultJsonArray.length(); i++) {
                PlaceInfo place = new PlaceInfo(
                        resultJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                        resultJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                        resultJsonArray.getJSONObject(i).getString("name")
                );
                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }
        return resultList;
    }

}
