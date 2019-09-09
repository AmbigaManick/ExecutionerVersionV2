package anulom.executioner.com.anulom;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
    public String readUrl(String mapsApiDirectionsUrl) throws IOException {
        String data = "";
        HttpURLConnection urlConnection = null;
        URL url = new URL(mapsApiDirectionsUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        try (InputStream iStream = urlConnection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {

            Log.d("Exceptionwhilereadurl", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return data;
    }

}
