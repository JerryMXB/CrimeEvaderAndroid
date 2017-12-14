package request;

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=museum&key=AIzaSyAA5FDptklMXePKTEmd6m7xUu4_xg8QKqM
/**
 * Created by Hunter6 on 12/13/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


import com.example.chaoqunhuang.crimeevader.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import bean.Location;
import bean.PlaceInfo;

public class GetGooglePlaceTask extends AsyncTask<String, Integer, ArrayList<PlaceInfo>> {
    private static final String LOG_TAG = "CrimeEvader";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch";
    private static final String RADIUS = "500";
    private static final String TYPE_SEARCH = "museum,art_gallery,park";
    private static final String API_KEY = "AIzaSyAA5FDptklMXePKTEmd6m7xUu4_xg8QKqM";
    private static final String OUT_JSON = "/json";

    protected ArrayList<PlaceInfo> doInBackground(String... locations){
        String[] latLon = locations[0].split("#");
        Log.i(MapsActivity.class.getSimpleName(), latLon[0]);
        Log.i(MapsActivity.class.getSimpleName(), latLon[1]);

        ArrayList<PlaceInfo> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(OUT_JSON);
            sb.append("?location=" + latLon[0] + "," + latLon[1]);
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

                try {
                    URL url = new URL("http://34.201.113.162:8080");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Accept-Encoding", "UTF-8");
                    connection.setRequestProperty("Content-Type", "application/json");
                    OutputStream os = connection.getOutputStream();
                    String body = "{\"type\":\"safety\",\"latitude\":\"" + place.getLatitude() + "\",\"longitude\":\"" + place.getLongitude() + "\"}";
                    os.write((body.getBytes("UTF-8")));
                    os.close();

                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String content = "", line;
                    while ((line = rd.readLine()) != null) {
                        content += line + "\n";
                    }
                    Log.i(LOG_TAG, content);
                    JSONObject jsonObject = new JSONObject(content);
                    place.setCc100(jsonObject.getInt("100"));
                    place.setCc200(jsonObject.getInt("200"));
                    place.setCc300(jsonObject.getInt("300"));
                    place.setSafetyScore(jsonObject.getDouble("score"));
                    rd.close();
                    connection.disconnect();
                } catch (Exception e) {
                    Log.i(LOG_TAG, e.getMessage());
                }
                resultList.add(place);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }
        return resultList;
    }

}
