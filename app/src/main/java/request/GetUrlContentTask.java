package request;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chaoqunhuang on 12/13/17.
 */

public abstract class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    protected String doInBackground(String... body) {
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
            os.write(body[0].getBytes("UTF-8"));
            os.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        } catch (Exception e) {
            return "Error";
        }
    }
}

